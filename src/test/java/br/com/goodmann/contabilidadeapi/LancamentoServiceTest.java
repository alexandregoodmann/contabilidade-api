package br.com.goodmann.contabilidadeapi;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@SpringBootTest
public class LancamentoServiceTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Test
	public void deleteAllByIdTest() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(8);
		ids.add(10);
		ids.add(11);

		List<Lancamento> lancamentos = lancamentoRepository.findAllById(ids);
		this.lancamentoRepository.deleteAll(lancamentos);
	}
}
