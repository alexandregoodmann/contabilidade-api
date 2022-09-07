package br.com.goodmann.contabilidadeapi;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@SpringBootTest
public class LancamentoServiceTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Test
	public void testFindById() {
		var opt = this.lancamentoRepository.findById(196);
		assertTrue(opt.isPresent());
	}
}
