package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.enums.TipoConta;
import br.com.goodmann.contabilidadeapi.enums.TipoLancamento;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.LancamentoLabel;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoLabelRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private LancamentoLabelRepository lancamentoLabelRepository;

	@Autowired
	private LabelService labelService;

	public Lancamento findById(Integer id) {
		Lancamento lancamento = this.lancamentoRepository.findById(id).get();
		List<String> labels = this.lancamentoLabelRepository.findAllByLancamento(lancamento).stream()
				.map(o -> o.getLabel().getDescricao()).collect(Collectors.toList());
		lancamento.setLabels(labels);
		return lancamento;
	}

	@Transactional
	public Lancamento create(Lancamento lancamento) {

		if ("true".equalsIgnoreCase(lancamento.getFixo())) {
			lancamento.setFixo(UUID.randomUUID().toString());
		} else if ("false".equalsIgnoreCase(lancamento.getFixo())) {
			lancamento.setFixo(null);
		}

		this.lancamentoRepository.save(lancamento);
		this.labelService.createLabels(lancamento);
		this.atualizaSaldo(lancamento.getPlanilha(), lancamento.getConta());
		return lancamento;
	}

	@Transactional
	public Lancamento update(Lancamento novo) {

		if ("true".equalsIgnoreCase(novo.getFixo())) {
			novo.setFixo(UUID.randomUUID().toString());
		} else if ("false".equalsIgnoreCase(novo.getFixo())) {
			novo.setFixo(null);
		}

		// atualiza saldo se houve mudanca de valor
		Lancamento original = this.lancamentoRepository.findById(novo.getId()).get();

		if (original.getValor().compareTo(novo.getValor()) != 0) {
			this.atualizaSaldo(novo.getPlanilha(), novo.getConta());
		}

		this.labelService.updateLabels(novo, original);
		return this.lancamentoRepository.save(novo);
	}

	@Transactional
	public void delete(Integer id) {
		Lancamento lancamento = this.lancamentoRepository.findById(id).get();
		if (TipoLancamento.SALDO.equals(lancamento.getTipo()))
			throw new RuntimeException("Não é possível excluir um lançamento do tipo SALDO");

		Lancamento lan = new Lancamento();
		lan.setId(id);
		List<LancamentoLabel> apagar = this.lancamentoLabelRepository.findAllByLancamento(lan);
		this.lancamentoLabelRepository.deleteAll(apagar);

		this.lancamentoRepository.deleteById(id);
		this.atualizaSaldo(lancamento.getPlanilha(), lancamento.getConta());
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

		List<Lancamento> lancamentos = this.lancamentoRepository.findAllById(ids).stream()
				.filter(o -> !TipoLancamento.SALDO.equals(o.getTipo()) && !TipoLancamento.FATURA.equals(o.getTipo()))
				.collect(Collectors.toList());

		lancamentos.forEach(lancamento -> {
			List<LancamentoLabel> list = this.lancamentoLabelRepository.findAllByLancamento(lancamento);
			this.lancamentoLabelRepository.deleteAll(list);
		});

		this.lancamentoRepository.deleteAll(lancamentos);
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

	public void atualizaSaldo(Planilha planilha, Conta conta) {
		if (TipoConta.CC.equals(conta.getTipo())) {
			List<Planilha> planilhas = this.getPlanilhaAtualAndFuturas(planilha);
			for (int i = 0; i < planilhas.size(); i++) {
				if (i + 1 < planilhas.size()) {

					Planilha atual = planilhas.get(i);
					Planilha proxima = planilhas.get(i + 1);
					BigDecimal saldoAtual = this.lancamentoRepository.findAllByPlanilhaAndConta(atual, conta).stream()
							.map(e -> e.getValor()).reduce((a, b) -> a.add(b)).get();

					this.lancamentoRepository.findByPlanilhaContaTipo(proxima, conta, TipoLancamento.SALDO).stream()
							.findFirst().ifPresentOrElse(e -> {
								e.setValor(saldoAtual);
								this.lancamentoRepository.save(e);
							}, () -> {
								Lancamento model = new Lancamento();
								model.setConta(conta);
								model.setData(
										java.sql.Date.valueOf(LocalDate.of(proxima.getAno(), proxima.getMes(), 1)));
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

	public void processarLabels(Integer idPlanilha, Integer idConta) {

		Planilha p = new Planilha();
		p.setId(idPlanilha);
		Conta c = new Conta();
		c.setId(idConta);

		List<Lancamento> lancamentos = this.lancamentoRepository.findAllByPlanilhaAndConta(p, c);
		lancamentos.forEach(lancamento -> {
			lancamento.setListLabels(this.lancamentoLabelRepository.findAllByLancamento(lancamento).stream()
					.map(o -> o.getLabel()).collect(Collectors.toList()));
		});

		this.labelService.processLabel(lancamentos);
	}

	public List<Lancamento> findAllByPlanilha(Planilha planilha) {
		List<Lancamento> lancamentos = this.lancamentoRepository.findAllByPlanilhaOrderByData(planilha);
		lancamentos.forEach(lancamento -> {
			this.lancamentoLabelRepository.findAllByLancamento(lancamento).forEach(labelLancamento -> {
				lancamento.getLabels().add(labelLancamento.getLabel().getDescricao());
			});
		});
		return lancamentos;
	}
}
