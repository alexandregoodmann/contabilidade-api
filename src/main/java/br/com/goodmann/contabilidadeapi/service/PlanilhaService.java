package br.com.goodmann.contabilidadeapi.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.goodmann.contabilidadeapi.dto.PlanilhasAnoDTO;
import br.com.goodmann.contabilidadeapi.enums.MesesEnum;
import br.com.goodmann.contabilidadeapi.enums.TipoConta;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.LancamentoLabel;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;
import br.com.goodmann.contabilidadeapi.repository.LancamentoLabelRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaAnualRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class PlanilhaService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoLabelRepository lancamentoLabelRepository;

	@Autowired
	private PlanilhaAnualRepository planilhaAnualRepository;

	@Transactional
	public void delete(Integer idPlanilha) {
		List<Lancamento> lancamentos = this.lancamentoRepository.findAllByPlanilhaOrderByData(new Planilha(idPlanilha));
		lancamentos.forEach(p -> {
			this.lancamentoLabelRepository.deleteAll(this.lancamentoLabelRepository.findAllByLancamento(p));
		});
		this.lancamentoRepository.deleteAll(lancamentos);
		this.planilhaRepository.deleteById(idPlanilha);
	}

	public List<Planilha> findAll() {
		return this.planilhaRepository.findAll();
	}

	public List<PlanilhasAnoDTO> getMapaPlanilhas() {

		List<PlanilhasAnoDTO> mapa = new ArrayList<PlanilhasAnoDTO>();
		Set<Short> anos = new HashSet<Short>();
		List<Planilha> planilhas = this.planilhaRepository.findAll();

		planilhas.forEach(p -> {
			anos.add(p.getAno());
		});

		anos.forEach(a -> {
			List<Planilha> plans = planilhas.stream().filter(o -> a.equals(o.getAno())).collect(Collectors.toList());
			mapa.add(new PlanilhasAnoDTO(a, plans));
		});

		return mapa;
	}

	private Planilha criarProximaPlanilha(Planilha atual) {
		LocalDate data = LocalDate.of(atual.getAno(), atual.getMes(), 1).plusMonths(1);
		Planilha model = new Planilha();
		model.setAno((short) data.getYear());
		model.setMes((short) data.getMonth().getValue());
		model.setDescricao(StringUtils.capitalize(MesesEnum.values()[model.getMes() - 1].toString().toLowerCase()));
		return this.planilhaRepository.save(model);
	}

	@Transactional
	public Planilha duplicarPlanilha(Integer idPlanilha) throws ParseException {

		Planilha atual = this.planilhaRepository.findById(idPlanilha).get();
		Planilha proxima = this.criarProximaPlanilha(atual);
		Set<Conta> contas = new HashSet<Conta>();

		// duplica os lançamentos
		this.lancamentoRepository.findAllFixos(new Planilha(idPlanilha)).forEach(lancamento -> {
			contas.add(lancamento.getConta());
			Lancamento model = new Lancamento();
			BeanUtils.copyProperties(lancamento, model, "id");
			model.setPlanilha(proxima);
			model.setConcluido(false);
			Date d = DateUtils.addMonths(model.getData(), 1);
			model.setData(d);
			Lancamento novoLancamento = this.lancamentoRepository.save(model);

			this.lancamentoLabelRepository.findAllByLancamento(lancamento).forEach(lancamentoLabel -> {
				LancamentoLabel novolancamentoLabel = new LancamentoLabel();
				novolancamentoLabel.setLabel(lancamentoLabel.getLabel());
				novolancamentoLabel.setLancamento(novoLancamento);
				this.lancamentoLabelRepository.save(novolancamentoLabel);
			});
		});

		// atualiza o lançamento saldoAnterior
		contas.stream().filter(o -> TipoConta.CC.equals(o.getTipo())).forEach(conta -> {
			this.lancamentoService.atualizaSaldo(atual, conta);
		});

		return proxima;
	}

	@Transactional
	public void processarPlanilhas() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date hoje = new Date();
		String data = sdf.format(hoje);

		this.lancamentoRepository.deleteLancamentosByCriacaoPlanilha(data);
		this.planilhaRepository.deletePlanilhaByCriacaoPlanilha(data);

		String ano = data.substring(0, 4);
		String mes = data.substring(5, 7);

		Planilha atual = this.planilhaRepository.findByAnoAndMes(Short.valueOf(ano), Short.valueOf(mes));

		Integer idAtual = atual.getId();
		for (int i = 0; i < 12; i++) {
			Planilha proxima = duplicarPlanilha(idAtual);
			idAtual = proxima.getId();
		}
	}

	@Transactional
	public List<PlanilhaAnual> processarPlanilhaAnual() {

		this.planilhaAnualRepository.deleteAll();
		this.planilhaAnualRepository.insertBase();

		List<PlanilhaAnual> planilha = this.planilhaAnualRepository.findAll();
		planilha.forEach(item -> {

			BigDecimal[] vet = this.criarListaValores(item);

			// lancamento unico
			if (item.getFixo() == null && item.getParcelas() == null) {
				vet[0] = item.getValor();
			}

			// parcelado
			if (item.getParcelas() != null && item.getFixo() == null) {
				String[] parc = item.getParcelas().split("/");
				int x = Integer.parseInt(parc[1]) - Integer.parseInt(parc[0]);
				for (int i = 0; i <= x; i++) {
					vet[i] = item.getValor();
				}
			}

			item.setListValores(Arrays.asList(vet));
			this.planilhaAnualRepository.save(item);
		});

		return planilha;
	}

	private BigDecimal[] criarListaValores(PlanilhaAnual item) {
		BigDecimal[] vet = new BigDecimal[12];
		for (int i = 0; i < 12; i++) {
			if (item.getFixo() != null)
				vet[i] = item.getValor();
		}
		return vet;
	}

}
