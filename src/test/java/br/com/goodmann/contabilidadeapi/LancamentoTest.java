package br.com.goodmann.contabilidadeapi;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.enums.TipoLancamento;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@SpringBootTest
public class LancamentoTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public void criarSaldoAnterior() {

		Planilha planilha = new Planilha();
		planilha.setId(910);

		Conta conta = new Conta();
		conta.setId(338);

		Lancamento model = new Lancamento();
		model.setConta(conta);
		model.setData(new Date());
		model.setDescricao("Saldo Anterior");
		model.setPlanilha(planilha);
		model.setTipo(TipoLancamento.SALDO);
		model.setValor(BigDecimal.ZERO);

		this.lancamentoRepository.save(model);
	}

	//@Test
	public void criarFatura() {

		Planilha planilha = new Planilha();
		planilha.setId(910);

		Conta conta = new Conta();
		conta.setId(338);

		Conta cartao = new Conta();
		cartao.setId(340);

		Lancamento model = new Lancamento();
		model.setConta(conta);
		model.setCartao(cartao);
		model.setData(new Date());
		model.setDescricao("Fatura C6");
		model.setPlanilha(planilha);
		model.setTipo(TipoLancamento.FATURA);
		model.setValor(BigDecimal.ZERO);

		this.lancamentoRepository.save(model);
	}

}
