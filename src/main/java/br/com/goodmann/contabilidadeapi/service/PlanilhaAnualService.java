package br.com.goodmann.contabilidadeapi.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaAnualRepository;

@Service
public class PlanilhaAnualService {

	@Autowired
	private PlanilhaAnualRepository planilhaAnualRepository;

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
			BigDecimal[] vet = this.criarListaValores(item);
			// lancamento unico
			if (item.getFixo() == null && item.getParcelas() == null) {
				vet[0] = item.getValor();
			}
			// parcelado
			if (item.getParcelas() != null && item.getFixo() == null) {
				String[] parc = item.getParcelas().split("/");
				int x = Integer.parseInt(parc[1]) - Integer.parseInt(parc[0]);
				for (int i = 0; i <= x; i++) {
					vet[i] = item.getValor();
				}
			}

			StringJoiner join = new StringJoiner(";");
			for (int i = 0; i < vet.length; i++) {
				join.add((vet[i] == null ? "" : vet[i].toString()));
			}
			item.setValores(join.toString());

			this.planilhaAnualRepository.save(item);
		});
	}

	private BigDecimal[] criarListaValores(PlanilhaAnual item) {
		BigDecimal[] vet = new BigDecimal[12];
		for (int i = 0; i < 12; i++) {
			if (item.getFixo() != null)
				vet[i] = item.getValor();
		}
		return vet;
	}

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
}
