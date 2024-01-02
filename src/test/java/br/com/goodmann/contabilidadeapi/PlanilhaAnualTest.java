package br.com.goodmann.contabilidadeapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.service.PlanilhaAnualService;

@SpringBootTest
public class PlanilhaAnualTest {

	@Autowired
	private PlanilhaAnualService service;

	@Test
	void criarPlanilhaTest() {
		this.service.criarPlanilhaAnual(9455, "Teste");
	}

}
