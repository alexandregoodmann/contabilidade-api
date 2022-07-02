package br.com.goodmann.contabilidadeapi.service;

import java.util.ArrayList;
import java.util.List;

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

	public Planilha create(Planilha model) {
		Planilha planilha = this.planilhaRepository.save(model);
		List<Lancamento> fixos = new ArrayList<Lancamento>(this.lancamentoRepository.findAllFixos());
		fixos.forEach(fixo -> {
			Lancamento novo = new Lancamento();
			BeanUtils.copyProperties(fixo, novo);
			novo.setId(null);
			novo.setPlanilha(planilha);
			novo.setFixo(false);
			this.lancamentoRepository.save(novo);
		});
		return planilha;
	}

}
