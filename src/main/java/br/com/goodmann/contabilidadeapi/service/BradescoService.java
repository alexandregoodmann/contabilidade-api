package br.com.goodmann.contabilidadeapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;

@Service
public class BradescoService extends ArquivoService {

	public List<String> montarLinhas(MultipartFile multipartFile) throws IOException {

		List<String> ret = new ArrayList<String>();
		List<String> lines = this.readLines(multipartFile);

		Date data;

		for (int i = 0; i < lines.size(); i++) {

			if (lines.get(i).contains("SALDO ANTERIOR"))
				continue;

			String[] vet = lines.get(i).split(";");
			try {
				data = DateUtils.parseDate(vet[0], "dd/MM/yy");
				ret.add(lines.get(i).concat(lines.get(i + 1)));
			} catch (ParseException e) {
				continue;
			}
		}
		return ret;
	}

	public Map<String, Object> cargaArquivoBradesco(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws IOException {

		List<String> bradesco = super.lancamentoRepository.findAllNumeroBradesco(planilha.getId()).stream()
				.map(n -> n.getNumeroBradesco()).collect(Collectors.toList());

		List<String> lines = this.montarLinhas(multipartFile);

		for (String line : lines) {
			System.out.println(line);
			String[] vet = line.split(";");
			if (!bradesco.contains(vet[2])) {

				Date data;
				try {
					data = DateUtils.parseDate(vet[0], "dd/MM/yy");
				} catch (Exception e) {
					continue;
				}

				Lancamento lancamento = new Lancamento();
				lancamento.setConta(conta);
				lancamento.setPlanilha(planilha);
				String descricao = vet.length == 7 ? vet[6] : vet[7];
				lancamento.setDescricao(descricao);

				String sValor = vet[3].isEmpty() ? vet[4] : vet[3];
				Double valor = Double
						.valueOf(sValor.replaceAll("\\.", "").replaceAll("\\,", "\\.").replaceAll("\\\"", ""));
				lancamento.setValor(BigDecimal.valueOf(valor));

				lancamento.setData(data);
				lancamento.setNumeroBradesco(vet[2]);

				this.lancamentoRepository.save(lancamento);
			}
		}

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("idConta", conta.getId());
		mapa.put("idPlanilha", planilha.getId());
		mapa.put("qtdLancamentos", lines.size());

		return mapa;

	}

}
