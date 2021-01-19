package br.com.goodmann.contabilidadeapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.application.banco.Banco;
import br.com.goodmann.contabilidadeapi.application.banco.BancoService;
import br.com.goodmann.contabilidadeapi.application.conta.Conta;
import br.com.goodmann.contabilidadeapi.application.conta.ContaService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ContaTest {

	@Autowired
	private BancoService bancoService;

	@Autowired
	private ContaService contaService;

	@Test
	@Order(1)
	void create() {

		Banco banco = this.bancoService.findAll().get(0);

		Conta conta = new Conta();
		conta.setBanco(banco);
		conta.setConta("Conta Teste 1");

		Conta contaRetorno = this.contaService.save(conta);
		assertEquals(conta.getConta(), contaRetorno.getConta());
	}

	@Test
	@Order(2)
	void findAll() {
		assertTrue(this.contaService.findAll().size() > 0);
	}

}
