package br.com.goodmann.contabilidadeapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@SpringBootTest
public class LancamentoTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Test
	public void testPlanilhaFutura() {
	}
}
