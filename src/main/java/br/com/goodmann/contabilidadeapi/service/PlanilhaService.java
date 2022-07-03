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

	@Transactional
	public Planilha create(Planilha model) {

		Planilha planilha = this.planilhaRepository.save(model);
		this.lancamentoRepository.findAllFixos().forEach(fixo -> {
			Lancamento lanc = new Lancamento();
			BeanUtils.copyProperties(fixo, lanc);
			lanc.setId(null);
			lanc.setFixo(null);
			lanc.setPlanilha(planilha);
			this.lancamentoRepository.save(lanc);
		});

		return planilha;
	}

	public List<Planilha> findAll() {
		return this.planilhaRepository.findAll();
	}

	public List<Lancamento> getLancamentos(Integer idPlanilha) {
		return this.lancamentoRepository.getLancamentos(idPlanilha);
	}
}
