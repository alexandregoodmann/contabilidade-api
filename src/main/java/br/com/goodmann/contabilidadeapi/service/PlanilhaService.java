package br.com.goodmann.contabilidadeapi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.model.PlanilhaConta;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaContaRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class PlanilhaService {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private PlanilhaContaRepository planilhaContaRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Transactional
	public Planilha create(Planilha model) {

		Planilha planilha = this.planilhaRepository.save(model);
		this.contaRepository.findAll().forEach(conta -> {
			PlanilhaConta pc = new PlanilhaConta();
			pc.setIdConta(conta.getId());
			pc.setIdPlanilha(planilha.getId());
			planilhaContaRepository.save(pc);
		});

		return planilha;
	}

	public List<Planilha> findAll() {
		return this.planilhaRepository.findAll();
	}

}
