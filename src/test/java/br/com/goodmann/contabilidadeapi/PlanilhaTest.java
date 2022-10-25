package br.com.goodmann.contabilidadeapi;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.service.PlanilhaService;

@SpringBootTest
public class PlanilhaTest {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private PlanilhaService planilhaService;

	// @Test
	public void getAnaliseAno() {
		this.planilhaRepository.getAnaliseAno(2022).forEach(e -> {
			System.out.println(e);
		});
		assertTrue(this.planilhaRepository.getAnaliseAno(2022).size() > 0);
	}

	// @Test
	public void test2() {
		this.planilhaRepository.getAnaliseAnoMes(2022, 10).forEach(e -> {
			System.out.println(e);
		});
		assertTrue(this.planilhaRepository.getAnaliseAnoMes(2022, 10).size() > 0);

	}

	@Test
	public void duplicar() {
		this.planilhaService.duplicarPlanilha(132);
		this.planilhaRepository.findAll().forEach(e -> {
			System.out.println(e.toString());
		});
	}
}
