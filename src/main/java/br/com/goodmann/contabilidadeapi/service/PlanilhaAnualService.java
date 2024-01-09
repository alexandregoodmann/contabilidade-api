package br.com.goodmann.contabilidadeapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaAnualRepository;

@Service
public class PlanilhaAnualService {

	@Autowired
	private PlanilhaAnualRepository planilhaAnualRepository;

	@Autowired
	private ArquivoService arquivoService;

	@Transactional
	public void rename(String novo, String velho) {
		this.planilhaAnualRepository.rename(novo, velho);
	}

	@Transactional
	public void duplicar(String planilha) {
		this.planilhaAnualRepository.duplicar(planilha.concat(" - Cópia"), planilha);
	}

	@Transactional
	public void delete(String planilha) {
		this.planilhaAnualRepository.delete(planilha);
	}

	public List<PlanilhaAnual> findByTitulo(String titulo) {
		List<PlanilhaAnual> list = this.planilhaAnualRepository.findAllByTitulo(titulo);
		list.forEach(item -> {
			this.parseVetString2ListBigDecimal(item);
		});
		return list;
	}

	@Transactional
	public void criarPlanilhaAnual(Integer idPlanilha, String titulo) {
		this.planilhaAnualRepository.criarPlanilhaAnual(idPlanilha, titulo);
		List<PlanilhaAnual> list = this.planilhaAnualRepository.findAllByTitulo(titulo);
		list.forEach(item -> {
			BigDecimal[] vetValores = this.criaVetorValores(item.getParcelas(), item);
			item.setValores(this.parseVetBigDecimal2String(vetValores));
			this.planilhaAnualRepository.save(item);
		});
	}

	public void cargaXPCartao(String titulo, MultipartFile multipartFile) throws IOException, ParseException {

		List<String> lines = this.arquivoService.readLines(multipartFile);

		for (int i = 1; i < lines.size(); i++) {

			String[] vet = lines.get(i).split(";");

			PlanilhaAnual item = new PlanilhaAnual();
			item.setTitulo(titulo);
			item.setConta("XP Cartão");
			item.setData(DateUtils.parseDate(vet[0], "dd/MM/yyyy"));
			item.setDescricao(vet[1]);

			String sValor = vet[3];
			sValor = sValor.replaceAll("R\\$ ", "").replaceAll("\\.", "").replaceAll("\\,", ".").trim();
			item.setValor(BigDecimal.valueOf(Double.valueOf(sValor) * (-1)));

			String parcela = ("-".equals(vet[4])) ? null : vet[4].replaceAll("\\ de ", "/");
			item.setParcelas(parcela);

			BigDecimal[] vetValores = this.criaVetorValores(parcela, item);
			item.setValores(this.parseVetBigDecimal2String(vetValores));

			this.planilhaAnualRepository.save(item);
		}
	}

	/**
	 * Verificar se é lançamento único, fixo ou parcelado e cria o vetor[12] de
	 * valores
	 * 
	 * @param item
	 * @return
	 */
	private BigDecimal[] criaVetorValores(String parcelas, PlanilhaAnual item) {

		BigDecimal[] vet = new BigDecimal[12];

		// fixo
		if (item.getFixo() != null) {
			for (int i = 0; i < 12; i++) {
				if (item.getFixo() != null)
					vet[i] = item.getValor();
			}
			return vet;
		}

		// parcelado
		if (parcelas != null) {
			String[] parc = item.getParcelas().split("/");
			int x = Integer.parseInt(parc[1]) - Integer.parseInt(parc[0]);
			for (int i = 0; i <= x; i++) {
				vet[i] = item.getValor();
			}
			return vet;
		}

		// unico
		vet[0] = item.getValor();

		return vet;
	}

	/**
	 * Pega cada item de planilhaAnual e converte a string de valores para uma lista
	 * de BigDecimal
	 * 
	 * @param item
	 */
	private void parseVetString2ListBigDecimal(PlanilhaAnual item) {
		if (item.getValores() != null) {
			String[] vet = item.getValores().split(";");
			BigDecimal[] vetValor = new BigDecimal[vet.length];
			for (int i = 0; i < vet.length; i++) {
				vetValor[i] = new BigDecimal(vet[i]);
			}
			item.setListValores(Arrays.asList(vetValor));
		}
	}

	private String parseVetBigDecimal2String(BigDecimal[] vet) {
		StringJoiner join = new StringJoiner(";");
		for (int i = 0; i < vet.length; i++) {
			join.add((vet[i] == null ? "" : vet[i].toString()));
		}
		return join.toString();
	}
}
