package br.com.goodmann.contabilidadeapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@Service
public class SodexoService extends ArquivoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private LabelService labelService;

	private Date parse2Date(String line, String mes, String ano) throws ParseException {
		String[] dias = { "segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo" };
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (String dia : dias) {
			if (line.contains(dia)) {
				String sDate = line.substring(line.length() - 18, line.length() - 16).concat("/" + mes + "/" + ano);
				return sdf.parse(sDate);
			}
		}
		return null;
	}

	private boolean isDate(String line) throws ParseException {
		String[] dias = { "segunda", "terça", "quarta", "quinta", "sexta", "sábado", "domingo" };
		for (String dia : dias) {
			if (line.contains(dia))
				return true;
		}
		return false;
	}

	public Map<String, Object> cargaArquivoSodexo(Conta conta, Planilha planilha, MultipartFile multipartFile)
			throws IOException, ParseException {

		List<String> lines = super.readLines(multipartFile);
		super.deleteAllLancamentos(conta, planilha);

		Date date = null;
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		for (String line : lines) {

			if (this.isDate(line)) {
				date = this.parse2Date(line, planilha.getMes().toString(), planilha.getAno().toString());
				continue;
			} else {

				String descricao = line.split("-")[0];
				String sValor = line.split("-")[1];
				sValor = sValor.replaceAll("R\\$", "").replaceAll("\\.", "").replaceAll("\\,", ".").trim();
				BigDecimal valor = BigDecimal.valueOf(Double.valueOf(sValor));

				Lancamento lancamento = new Lancamento();
				lancamento.setData(date);
				lancamento.setConta(conta);
				lancamento.setPlanilha(planilha);
				lancamento.setDescricao(descricao);

				if (descricao.contains("VALOR"))
					lancamento.setValor(valor);
				else
					lancamento.setValor(valor.multiply(BigDecimal.valueOf(-1)));

				lancamento.setConcluido(true);
				
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
}
