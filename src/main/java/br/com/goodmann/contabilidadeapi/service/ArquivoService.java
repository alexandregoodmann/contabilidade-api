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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.MesesAbreviadosEnum;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class ArquivoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");

	private List<String> readLines(MultipartFile multipartFile) throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		List<String> lista = new ArrayList<String>(reader.lines().collect(Collectors.toList()));
		reader.close();
		return lista;
	}

	public Map<String, Object> cargaArquivoC6(Integer idConta, Integer idPlanilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<String> lines = this.readLines(multipartFile);
		Planilha planilha = this.planilhaRepository.findById(idPlanilha).get();
		Conta conta = new Conta(idConta);

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
		mapa.put("idConta", idConta);
		mapa.put("idPlanilha", idPlanilha);
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

	public Map<String, Object> cargaArquivoBradesco(Integer idConta, Integer idPlanilha, MultipartFile multipartFile)
			throws ParseException, IOException {

		List<String> lines = this.readLines(multipartFile);
		Planilha planilha = this.planilhaRepository.findById(idPlanilha).get();
		Conta conta = new Conta(idConta);
		int count = 0;
		for (int i = 3; i < lines.size(); i += 2) {

			if (lines.get(i).contains(";Total;"))
				break;

			String line = lines.get(i) + lines.get(i + 1);
			String[] vet = line.split(";");

			Lancamento lancamento = new Lancamento();
			lancamento.setConta(conta);
			lancamento.setPlanilha(planilha);

			String descricao = vet.length == 7 ? vet[6] : vet[7];
			lancamento.setDescricao(descricao);

			String sValor = vet[3].isEmpty() ? vet[4] : vet[3];
			Double valor = Double.valueOf(sValor.replaceAll("\\.", "").replaceAll("\\,", "\\.").replaceAll("\\\"", ""));
			lancamento.setValor(BigDecimal.valueOf(valor));

			lancamento.setData(this.sdf2.parse(vet[0]));
			this.lancamentoRepository.save(lancamento);

			count++;
		}

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", idConta);
		mapa.put("idPlanilha", idPlanilha);
		mapa.put("qtdLancamentos", count);

		return mapa;

	}

}
