package br.com.goodmann.contabilidadeapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.service.PlanilhaService;

@SpringBootTest
public class PlanilhaTest {

	@Autowired
	private PlanilhaService planilhaService;

	@Test
	void criarPlanilhaAnual() {
		this.planilhaService.criarPlanilhaAnual(9455, "Planilha_2024");
	}

}
