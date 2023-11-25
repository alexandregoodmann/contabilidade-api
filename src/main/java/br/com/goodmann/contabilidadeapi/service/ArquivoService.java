package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoLabelRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import javassist.NotFoundException;

@Service
public class ArquivoService {

	@Value("${file.download}")
	private String filesPath;

	@Autowired
	protected LancamentoRepository lancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private BradescoService bradescoService;

	@Autowired
	private LabelService labelService;

	@Autowired
	private LancamentoLabelRepository lancamentoLabelRepository;

	@Autowired
	private SodexoService sodexoService;

	public List<String> readLines(MultipartFile multipartFile) throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		List<String> lista = new ArrayList<String>(reader.lines().collect(Collectors.toList()));
		reader.close();
		return lista;
	}

	public Map<String, Object> cargaArquivo(Integer idConta, Integer idPlanilha, MultipartFile multipartFile)
			throws NotFoundException, ParseException, IOException {

		Map<String, Object> mapa = null;

		Optional<Conta> conta = this.contaRepository.findById(idConta);
		if (!conta.isPresent())
			throw new NotFoundException("O idconta não foi encontrado: " + idConta);

		Optional<Planilha> planilha = this.planilhaRepository.findById(idPlanilha);

		if (!planilha.isPresent())
			throw new NotFoundException("The id planilha was not found: " + idPlanilha);

		// carga c6
		if ("336".equals(conta.get().getBanco().getCodigo())) {
			this.deleteAllLancamentos(conta.get(), planilha.get());
			mapa = this.cargaArquivoC6(conta.get(), planilha.get(), multipartFile);
		}

		// bradesco
		if ("237".equals(conta.get().getBanco().getCodigo())) {
			mapa = this.bradescoService.cargaArquivoBradesco(conta.get(), planilha.get(), multipartFile);
			this.lancamentoService.atualizaSaldo(planilha.get(), conta.get());
		}

		// itau
		if ("479".equals(conta.get().getBanco().getCodigo())) {
			mapa = this.cargaArquivoItau(conta.get(), planilha.get(), multipartFile);
		}

		// sodexo
		if (conta.get().getBanco().getId() == 4) {
			mapa = this.sodexoService.cargaArquivoSodexo(conta.get(), planilha.get(), multipartFile);
		}

		// xp
		if (conta.get().getId() == 8710) {
			mapa = this.sodexoService.cargaXP(conta.get(), planilha.get(), multipartFile);
		}

		// xp cartao
		if (conta.get().getId() == 8711) {
			this.deleteAllLancamentos(conta.get(), planilha.get());
			mapa = this.sodexoService.cargaXPCartao(conta.get(), planilha.get(), multipartFile);
		}

		return mapa;
	}

	protected void deleteAllLancamentos(Conta conta, Planilha planilha) {
		Lancamento l = new Lancamento();
		l.setConta(conta);
		l.setPlanilha(planilha);
		Example<Lancamento> example = Example.of(l);
		List<Lancamento> lancamentos = this.lancamentoRepository.findAll(example);
		lancamentos.forEach(lancamento -> {
			this.lancamentoLabelRepository.deleteAll(this.lancamentoLabelRepository.findAllByLancamento(lancamento));
		});
		this.lancamentoRepository.deleteAll(lancamentos);
	}

	private Map<String, Object> cargaArquivoC6(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<String> lines = this.readLines(multipartFile);
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();

		for (int i = 1; i < lines.size(); i++) {
			String[] vet = lines.get(i).split(";");
			Lancamento lancamento = new Lancamento();
			lancamento.setConta(conta);
			lancamento.setPlanilha(planilha);
			lancamento.setValor(BigDecimal.valueOf(Double.valueOf(vet[8]) * (-1)));
			lancamento.setData(DateUtils.parseDate(vet[0], "dd/MM/yyyy"));
			lancamento.setConcluido(true);

			if (!"Única".equalsIgnoreCase(vet[5])) {
				lancamento.setDescricao(vet[4] + " " + vet[5]);
				lancamento.setParcelas(vet[5]);
			} else {
				lancamento.setDescricao(vet[4]);
			}

			this.lancamentoRepository.save(lancamento);
			lancamentos.add(lancamento);
		}

		this.labelService.processLabel(lancamentos);

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

	private Map<String, Object> cargaArquivoItau(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws IOException, ParseException {

		List<Lancamento> lancamentos = this.lancamentoRepository.findAllByPlanilhaAndConta(planilha, conta);

		List<String> lines = this.readLines(multipartFile);

		String[] vet = new String[3];
		int count = 0;

		for (String line : lines) {

			if (!line.contains("SALDO DO DIA")) {
				vet[0] = line.substring(0, 10);
				vet[1] = line.substring(11);
				String[] vetValor = line.substring(11).split(" ");
				String valor = vetValor[vetValor.length - 1];
				vet[1] = vet[1].replace(valor, "");
				valor = valor.replaceAll("\\.", "").replaceAll("\\,", ".");

				Lancamento lancamento = new Lancamento();
				lancamento.setConta(conta);
				lancamento.setData(DateUtils.parseDate(vet[0], "dd/MM/yyyy"));
				lancamento.setDescricao(vet[1]);
				lancamento.setPlanilha(planilha);
				lancamento.setValor(BigDecimal.valueOf(Double.valueOf(valor)));
				lancamento.setConcluido(true);

				if (lancamentos.stream().filter(o -> o.getData().compareTo(lancamento.getData()) == 0
						&& o.getValor().compareTo(lancamento.getValor()) == 0).count() == 0) {
					this.lancamentoRepository.save(lancamento);
					count++;
				}
			}
		}
		;

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

	public Map<String, Object> cargaXP(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<Lancamento> list = this.lancamentoRepository.findAllByPlanilhaAndConta(planilha, conta);

		List<String> lines = this.readLines(multipartFile);
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();

		for (int i = 1; i < lines.size(); i++) {

			String[] vet = lines.get(i).split(";");

			Lancamento lancamento = new Lancamento();
			lancamento.setData(DateUtils.parseDate(vet[0], "dd/MM/yyyy HH:mm"));
			lancamento.setDescricao(vet[1]);

			String sValor = vet[2];
			sValor = sValor.replaceAll("R\\$ ", "").replaceAll("\\.", "").replaceAll("\\,", ".").trim();
			lancamento.setValor(BigDecimal.valueOf(Double.valueOf(sValor)));

			lancamento.setConta(conta);
			lancamento.setPlanilha(planilha);
			lancamento.setConcluido(true);

			if (list.stream().filter(p -> p.getData().compareTo(lancamento.getData()) == 0
					&& p.getValor().compareTo(lancamento.getValor()) == 0).toArray().length == 0) {
				this.lancamentoRepository.save(lancamento);
				lancamentos.add(lancamento);
			}
		}

		this.labelService.processLabel(lancamentos);

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

	public Map<String, Object> cargaXPCartao(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<String> lines = this.readLines(multipartFile);
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();

		for (int i = 1; i < lines.size(); i++) {

			String[] vet = lines.get(i).split(";");

			Lancamento lancamento = new Lancamento();
			lancamento.setData(DateUtils.parseDate(vet[0], "dd/MM/yyyy"));
			lancamento.setDescricao(vet[1]);

			String sValor = vet[3];
			sValor = sValor.replaceAll("R\\$ ", "").replaceAll("\\.", "").replaceAll("\\,", ".").trim();
			lancamento.setValor(BigDecimal.valueOf(Double.valueOf(sValor) * (-1)));
			lancamento.setConta(conta);
			lancamento.setPlanilha(planilha);
			lancamento.setConcluido(true);
			String parcela = ("-".equals(vet[4])) ? null : vet[4].replaceAll("\\ de ", "/");
			lancamento.setParcelas(parcela);

			this.lancamentoRepository.save(lancamento);
			lancamentos.add(lancamento);

		}

		this.labelService.processLabel(lancamentos);

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

}
