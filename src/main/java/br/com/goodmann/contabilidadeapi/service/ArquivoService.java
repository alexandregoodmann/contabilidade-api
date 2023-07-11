package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.UtilConfiguration;
import br.com.goodmann.contabilidadeapi.dto.AnaliseDTO;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.model.SubLancamento;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.repository.SubLancamentoRepository;
import javassist.NotFoundException;

@Service
public class ArquivoService {

	@Value("${file.download}")
	private String filesPath;

	@Autowired
	protected LancamentoRepository lancamentoRepository;

	@Autowired
	protected SubLancamentoRepository subLancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private BradescoService bradescoService;

	@Autowired
	protected UtilConfiguration util;

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

		if (!conta.get().getBanco().getCarga())
			throw new NotFoundException("Este banco não possui carga de arquivo");

		// bradesco
		if ("237".equals(conta.get().getBanco().getCodigo())) {
			mapa = this.bradescoService.cargaArquivoBradesco(conta.get(), planilha.get(), multipartFile);
			this.lancamentoService.atualizaSaldo(planilha.get(), conta.get());
		}

		// itau
		if ("479".equals(conta.get().getBanco().getCodigo())) {
			mapa = this.cargaArquivoItau(conta.get(), planilha.get(), multipartFile);
		}
		
		return mapa;
	}

	private void deleteAllLancamentos(Conta conta, Planilha planilha) {
		Lancamento l = new Lancamento();
		l.setConta(conta);
		l.setPlanilha(planilha);
		Example<Lancamento> example = Example.of(l);
		List<Lancamento> list = this.lancamentoRepository.findAll(example);
		this.lancamentoRepository.deleteAll(list);
	}

	/**
	 * Le arquivo CSV do C6, faz o lancamento principal na conta a qual pagou a
	 * fatura e insere os sublancamentos (itens da fatura)
	 * 
	 * @param conta         - Conta do Lancamento principal
	 * @param planilha      - Planilha do lancamento principal
	 * @param multipartFile - Arquivo onde cada linha se transformara em
	 *                      sublancamento
	 * @return Mapa com resumo dos lancamentos
	 * @throws ParseException
	 * @throws IOException
	 */
	@Transactional
	public Map<String, Object> cargaArquivoC6(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<String> lines = this.readLines(multipartFile);

		Lancamento lancamento = new Lancamento();
		lancamento.setConta(conta);
		lancamento.setPlanilha(planilha);
		lancamento.setDescricao("Fatura C6");
		lancamento.setValor(BigDecimal.ZERO);
		lancamento.setData(new Date());

		this.lancamentoRepository.save(lancamento);
		BigDecimal total = BigDecimal.ZERO;

		for (int i = 1; i < lines.size(); i++) {

			String[] vet = lines.get(i).split(";");

			SubLancamento sub = new SubLancamento();
			sub.setLancamento(lancamento);
			sub.setData(this.util.dateUtil().parseToDate(vet[0]));
			sub.setDescricao(vet[4]);
			sub.setValor(new BigDecimal(vet[8]));
			this.subLancamentoRepository.save(sub);

			total = total.add(sub.getValor());
		}

		lancamento.setValor(total);
		this.lancamentoRepository.save(lancamento);

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

	private Map<String, Object> cargaArquivoItau(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws IOException {

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
				try {
					lancamento.setData(this.util.dateUtil().parseToDate(vet[0]));
				} catch (ParseException e) {
					throw new RuntimeException("ERROR parsing the value: " + valor);
				}
				lancamento.setDescricao(vet[1]);
				lancamento.setPlanilha(planilha);
				lancamento.setValor(BigDecimal.valueOf(Double.valueOf(valor)));

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

	public Resource downloadExtrato(Integer ano, Integer mes) throws IOException {
		String fileName = String.format(this.filesPath + "/extrato-%s-%s.csv", ano, mes);
		this.arquivoExtrato(ano, mes, fileName);
		try {
			Path file = Paths.get(filesPath).resolve(fileName);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	private void arquivoExtrato(Integer ano, Integer mes, String fileName) throws IOException {
		File file = new File(fileName);
		FileWriter fw = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fw);
		this.planilhaRepository.getAnaliseAnoMes(ano, mes).stream().map(this::parse2String).forEach(t -> {
			try {
				writer.append(t);
				writer.newLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		writer.close();
	}

	private String parse2String(AnaliseDTO dto) {

		StringBuilder sb = new StringBuilder();
		String dt = dto.getData().toString().substring(0, 10);
		sb.append(dt).append(";");
		sb.append(dto.getBanco()).append(";");
		String categoria = (dto.getCategoria() == null ? "" : dto.getCategoria());
		sb.append(categoria).append(";");
		sb.append(dto.getDescricao()).append(";");
		sb.append(dto.getValor());

		return sb.toString();
	}

}
