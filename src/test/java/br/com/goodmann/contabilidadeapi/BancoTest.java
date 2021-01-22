package br.com.goodmann.contabilidadeapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.application.banco.Banco;
import br.com.goodmann.contabilidadeapi.application.banco.BancoRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class BancoTest {

	@Autowired
	private BancoRepository repo;

	@Test
	@Order(1)
	void find() {
		List<Banco> l = this.repo.findByBancoContainingIgnoreCase("bradesco");
		l.forEach(e -> {
			System.out.println(e);
		});
		assertTrue(l.size() > 0);
	}

}
