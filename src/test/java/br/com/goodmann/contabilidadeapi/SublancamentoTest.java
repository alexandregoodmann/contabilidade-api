package br.com.goodmann.contabilidadeapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.repository.SublancamentoRepository;

@SpringBootTest
public class SublancamentoTest {

	@Autowired
	private SublancamentoRepository sublancamentoRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Test
	public void testExtrato() {
		Planilha p = this.planilhaRepository.findByAnoAndMes(Short.valueOf("2023"), Short.valueOf("7"));
		this.sublancamentoRepository.findAllByIdPlanilha(p.getId());
	}

}
