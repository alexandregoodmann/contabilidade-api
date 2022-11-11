package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.rmi.NoSuchObjectException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.enums.TipoConta;
import br.com.goodmann.contabilidadeapi.enums.TipoLancamento;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.CategoriaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Transactional
	public Lancamento save(Lancamento model) {
		this.lancamentoRepository.save(model);
		if (TipoConta.CC.equals(model.getConta().getTipo())) {
			this.atualizarSaldoCC(model.getPlanilha(), model.getConta());
		} else if (TipoConta.CARTAO.equals(model.getConta().getTipo())) {
			this.atualizaValorFatura(model.getPlanilha(), model.getConta(), model.getContaPagadora());
		}
		return model;
	}

	@Transactional
	public void delete(Integer id) {
		Lancamento lancamento = this.lancamentoRepository.findById(id).get();
		this.lancamentoRepository.deleteById(id);
		if (TipoConta.CC.equals(lancamento.getConta().getTipo())) {
			this.atualizarSaldoCC(lancamento.getPlanilha(), lancamento.getConta());
		} else if (TipoConta.CARTAO.equals(lancamento.getConta().getTipo())) {
			this.atualizaValorFatura(lancamento.getPlanilha(), lancamento.getConta(), lancamento.getContaPagadora());
		}
	}

	public List<String> lerArquivo(MultipartFile file) throws IOException {
		InputStreamReader reader = new InputStreamReader(file.getInputStream());
		BufferedReader br = new BufferedReader(reader);
		String linha = br.readLine();
		List<String> lista = new ArrayList<String>();
		while (linha != null) {
			lista.add(linha);
			linha = br.readLine();
		}
		return lista;
	}

	public void deleteAllById(List<Integer> ids) {
		List<Lancamento> lancamentos = this.lancamentoRepository.findAllById(ids);
		this.lancamentoRepository.deleteAll(lancamentos);
	}

	public void concluir(List<Integer> ids) {
		this.lancamentoRepository.findAllById(ids).forEach(lancamento -> {
			lancamento.setConcluido(true);
			this.lancamentoRepository.save(lancamento);
		});
	}

	public void fixo(List<Integer> ids) {
		this.lancamentoRepository.findAllById(ids).forEach(lancamento -> {
			lancamento.setFixo(true);
			this.lancamentoRepository.save(lancamento);
		});
	}

	public void categorizar(@NotEmpty List<Integer> ids, @NotNull Integer idCategoria) throws NoSuchObjectException {

		var opt = this.categoriaRepository.findById(idCategoria);
		if (!opt.isPresent())
			throw new NoSuchObjectException("The idCategoria was not found: " + idCategoria);

		this.lancamentoRepository.findAllById(ids).forEach(lancamento -> {
			lancamento.setCategoria(opt.get());
			this.lancamentoRepository.save(lancamento);
		});
	}

	private List<Planilha> getPlanilhaAtualAndFuturas(Planilha planilha) {
		List<Planilha> planilhas = this.planilhaRepository.getPlanilhasFuturas(planilha.getAno());
		planilhas.forEach(p -> {
			LocalDate criacao = LocalDate.of(p.getAno(), p.getMes(), 1);
			p.setCriacao(criacao);
		});
		LocalDate mesAnterior = LocalDate.of(planilha.getAno(), planilha.getMes(), 1).minusMonths(1);
		return planilhas.stream().filter(o -> o.getCriacao().isAfter(mesAnterior)).collect(Collectors.toList());
	}

	public void atualizaValorFatura(Planilha inicial, Conta cartao, Conta pagadora) {

		if (!TipoConta.CARTAO.equals(cartao.getTipo()) || !TipoConta.CC.equals(pagadora.getTipo())) {
			throw new RuntimeException("ERRO - A conta deverá ser do tipo CARTÃO e pagadora do tipo CC");
		}

		this.getPlanilhaAtualAndFuturas(inicial).forEach(planilha -> {
			BigDecimal total = this.lancamentoRepository.findAllByPlanilhaAndConta(planilha, cartao).stream()
					.map(e -> e.getValor()).reduce((a, b) -> a.add(b)).get();

			this.lancamentoRepository.getByPlanilhaContaTipo(planilha, pagadora, TipoLancamento.FATURA)
					.ifPresentOrElse(e -> {
						e.setValor(total);
						this.lancamentoRepository.save(e);
					}, () -> {
						Lancamento model = new Lancamento();
						model.setConta(cartao);
						model.setContaPagadora(pagadora);
						model.setData(Date.valueOf(LocalDate.of(planilha.getAno(), planilha.getMes(), 1)));
						model.setDescricao("Fatura " + cartao.getDescricao());
						model.setPlanilha(planilha);
						model.setValor(total);
						model.setTipo(TipoLancamento.FATURA);
						this.lancamentoRepository.save(model);
					});
		});
	}

	public void atualizarSaldoCC(Planilha planilha, Conta conta) {

		if (!TipoConta.CC.equals(conta.getTipo())) {
			throw new RuntimeException("ERRO - Esta operação é somente para conta do tipo CC");
		}

		List<Planilha> planilhas = this.getPlanilhaAtualAndFuturas(planilha);
		for (int i = 0; i < planilhas.size(); i++) {
			if (i + 1 < planilhas.size()) {
				Planilha atual = planilhas.get(i);
				Planilha proxima = planilhas.get(i + 1);
				BigDecimal saldoAtual = this.lancamentoRepository.findAllByPlanilhaAndConta(atual, conta).stream()
						.map(e -> e.getValor()).reduce((a, b) -> a.add(b)).get();
				this.lancamentoRepository.getByPlanilhaContaTipo(proxima, conta, TipoLancamento.SALDO)
						.ifPresentOrElse(e -> {
							e.setValor(saldoAtual);
							this.lancamentoRepository.save(e);
						}, () -> {
							Lancamento model = new Lancamento();
							model.setConta(conta);
							model.setData(Date.valueOf(LocalDate.of(proxima.getAno(), proxima.getMes(), 1)));
							model.setDescricao("Saldo Anterior");
							model.setPlanilha(proxima);
							model.setValor(saldoAtual);
							model.setTipo(TipoLancamento.SALDO);
							this.lancamentoRepository.save(model);
						});
			}
		}
	}

}
