package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.enums.MesesAbreviadosEnum;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import javassist.NotFoundException;

@Service
public class ArquivoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");

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
		/*
		 * switch (conta.get().getCarga()) { case BRADESCO: mapa =
		 * this.cargaArquivoBradesco(conta.get(), planilha.get(), multipartFile); break;
		 * 
		 * case C6: this.deleteAllLancamentos(conta.get(), planilha.get()); mapa =
		 * this.cargaArquivoC6(conta.get(), planilha.get(), multipartFile); break; }
		 */

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

	private Map<String, Object> cargaArquivoC6(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<String> lines = this.readLines(multipartFile);

		for (String line : lines) {

			line = line.replaceAll("\\.", "").replaceAll("\\,", ".");
			String dia = line.substring(0, 2);
			String mes = "";

			int i = MesesAbreviadosEnum.valueOf(line.substring(3, 6).toUpperCase()).ordinal() + 1;
			if (i < 10)
				mes = "0" + i;
			else
				mes = String.valueOf(i);
			String data = dia + "/" + mes + "/" + planilha.getAno();

			String[] vet = line.split(" ");
			String valor = (vet[vet.length - 1]);

			String descricao = line.substring(7).replace(valor, "");

			Lancamento lancamento = new Lancamento();
			lancamento.setConta(conta);
			lancamento.setPlanilha(planilha);
			lancamento.setDescricao(descricao);
			lancamento.setValor(BigDecimal.valueOf(Double.valueOf(valor) * (-1)));
			lancamento.setData(this.sdf.parse(data));

			this.lancamentoRepository.save(lancamento);

		}

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

	private Map<String, Object> cargaArquivoBradesco(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<String> bradesco = this.lancamentoRepository.getNumerosBradesco(planilha.getId()).stream()
				.map(n -> n.getNumeroBradesco()).collect(Collectors.toList());

		List<String> lines = this.readLines(multipartFile);
		int count = 0;

		for (int i = 3; i < lines.size(); i += 2) {

			if (lines.get(i).contains(";Total;"))
				break;

			String line = lines.get(i) + lines.get(i + 1);
			String[] vet = line.split(";");

			if (vet.length >= 3 && bradesco.contains(vet[2]))
				continue;

			Lancamento lancamento = new Lancamento();
			lancamento.setConta(conta);
			lancamento.setPlanilha(planilha);
			String descricao = vet.length == 7 ? vet[6] : vet[7];
			lancamento.setDescricao(descricao);

			String sValor = vet[3].isEmpty() ? vet[4] : vet[3];
			Double valor = Double.valueOf(sValor.replaceAll("\\.", "").replaceAll("\\,", "\\.").replaceAll("\\\"", ""));
			lancamento.setValor(BigDecimal.valueOf(valor));

			lancamento.setData(this.sdf2.parse(vet[0]));
			lancamento.setNumeroBradesco(vet[2]);

			this.lancamentoRepository.save(lancamento);

			count++;
		}

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", count);

		return mapa;

	}

}
