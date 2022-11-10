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

import br.com.goodmann.contabilidadeapi.model.Categoria;
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
		this.atualizarSaldo(model.getPlanilha(), model.getConta());
		return model;
	}

	@Transactional
	public void delete(Integer id) {
		Lancamento lancamento = this.lancamentoRepository.findById(id).get();
		this.lancamentoRepository.deleteById(id);
		this.atualizarSaldo(lancamento.getPlanilha(), lancamento.getConta());
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

	public void atualizarSaldo(Planilha planilha, Conta conta) {
		if (!"C6 Cartão".equalsIgnoreCase(conta.getDescricao())) {
			List<Planilha> planilhas = this.getPlanilhaAtualAndFuturas(planilha);
			for (int i = 0; i < planilhas.size(); i++) {
				if (i + 1 < planilhas.size()) {
					Planilha atual = planilhas.get(i);
					Planilha proxima = planilhas.get(i + 1);
					BigDecimal saldoAtual = this.lancamentoRepository.findAllByContaAndPlanilha(conta, atual).stream()
							.map(e -> e.getValor()).reduce((a, b) -> a.add(b)).get();
					this.lancamentoRepository.getLancamentoSaldo(proxima, conta).ifPresentOrElse(e -> {
						e.setValor(saldoAtual);
						this.lancamentoRepository.save(e);
					}, () -> {
						Categoria categoria = this.categoriaRepository.findByDescricao("Saldo Anterior");
						Lancamento model = new Lancamento();
						model.setCategoria(categoria);
						model.setConta(conta);
						model.setData(Date.valueOf(LocalDate.of(proxima.getAno(), proxima.getMes(), 1)));
						model.setDescricao("Saldo Anterior");
						model.setPlanilha(proxima);
						model.setValor(saldoAtual);
						this.lancamentoRepository.save(model);
					});
				}
			}
		}
	}

}
