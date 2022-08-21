package br.com.goodmann.contabilidadeapi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class PlanilhaService {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public List<Planilha> findAll() {
		return this.planilhaRepository.findAll();
	}

	public List<Lancamento> getLancamentos(Integer idPlanilha) {
		return this.lancamentoRepository.getLancamentos(idPlanilha);
	}

	public Planilha findByAnoAndMes(Short ano, Short mes) {
		Planilha planilha = this.planilhaRepository.findByAnoAndMes(ano, mes);
		if (planilha != null) {
			List<Lancamento> lancamentos = this.lancamentoRepository.findAllByPlanilha(planilha);
			lancamentos.forEach(l -> {
				l.setPlanilha(null);
			});
			planilha.setLancamentos(lancamentos);
		}
		return planilha;
	}
}
