package br.com.goodmann.contabilidadeapi.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.goodmann.contabilidadeapi.dto.ExtratoDTO;
import br.com.goodmann.contabilidadeapi.dto.LancamentoDTO;
import br.com.goodmann.contabilidadeapi.dto.PlanilhasAnoDTO;
import br.com.goodmann.contabilidadeapi.enums.MesesEnum;
import br.com.goodmann.contabilidadeapi.enums.TipoConta;
import br.com.goodmann.contabilidadeapi.enums.TipoLancamento;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.LimiteGastos;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.LimiteGastosRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class PlanilhaService {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LimiteGastosRepository gastosRepository;

	@Transactional
	public void delete(Integer idPlanilha) {
		this.gastosRepository.deleteAll(
				this.gastosRepository.findAllByPlanilha(this.planilhaRepository.findById(idPlanilha).orElseThrow()));
		this.lancamentoRepository.deleteAll(this.lancamentoRepository.findAllByIdPlanilha(idPlanilha));
		this.planilhaRepository.deleteById(idPlanilha);
	}

	public List<Planilha> findAll() {
		return this.planilhaRepository.findAll();
	}

	public List<Lancamento> getLancamentos(Integer idPlanilha) {
		return this.lancamentoRepository.findAllByIdPlanilha(idPlanilha);
	}

	public Planilha findByAnoAndMes(Short ano, Short mes) {
		Planilha planilha = this.planilhaRepository.findByAnoAndMes(ano, mes);
		if (planilha != null) {
			List<Lancamento> lancamentos = this.lancamentoRepository.findAllByPlanilha(planilha);
			lancamentos.forEach(l -> {
				l.setPlanilha(null);
			});
			planilha.setLancamentos(lancamentos);
		}
		return planilha;
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

	public List<ExtratoDTO> getExtrato(Integer idPlanilha) {
		Map<Conta, List<Lancamento>> mapa = new HashMap<Conta, List<Lancamento>>();
		this.lancamentoRepository.findAllByIdPlanilha(idPlanilha).forEach(lancamento -> {
			List<Lancamento> temp = new ArrayList<Lancamento>();
			if (!mapa.containsKey(lancamento.getConta())) {
				temp.add(lancamento);
				mapa.put(lancamento.getConta(), temp);
			} else {
				temp = mapa.get(lancamento.getConta());
				temp.add(lancamento);
				mapa.put(lancamento.getConta(), temp);
			}
		});

		List<ExtratoDTO> contas = new ArrayList<ExtratoDTO>();
		mapa.forEach((conta, lancamentos) -> {
			ExtratoDTO contaDTO = new ExtratoDTO();
			BeanUtils.copyProperties(conta, contaDTO);
			lancamentos.forEach(lancamento -> {
				String categoria = lancamento.getCategoria() == null ? null : lancamento.getCategoria().getDescricao();
				LancamentoDTO lancamentoDTO = new LancamentoDTO();
				lancamentoDTO.setCategoria(categoria);
				lancamentoDTO.setConcluido(lancamento.getConcluido());
				lancamentoDTO.setData(lancamento.getData());
				lancamentoDTO.setDescricao(lancamento.getDescricao());
				lancamentoDTO.setFixo(lancamento.getFixo());
				lancamentoDTO.setId(lancamento.getId());
				lancamentoDTO.setValor(lancamento.getValor());
				contaDTO.getLancamentos().add(lancamentoDTO);
				contaDTO.setSaldoPrevisto(contaDTO.getSaldoPrevisto().add(lancamento.getValor()));
				if (lancamento.getConcluido() != null && lancamento.getConcluido() == true)
					contaDTO.setSaldoEfetivado(contaDTO.getSaldoEfetivado().add(lancamento.getValor()));
			});
			contas.add(contaDTO);
		});
		return contas;
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
	public void duplicarPlanilha(Integer idPlanilha) throws ParseException {

		Planilha atual = this.planilhaRepository.findById(idPlanilha).get();
		Planilha proxima = this.criarProximaPlanilha(atual);
		Set<Conta> contas = new HashSet<Conta>();

		// duplica os lançamentos
		this.lancamentoRepository.findAllByIdPlanilha(idPlanilha).forEach(lancamento -> {
			if (Boolean.TRUE.equals(lancamento.getFixo()) || TipoLancamento.SALDO.equals(lancamento.getTipo())
					|| TipoLancamento.FATURA.equals(lancamento.getTipo())) {
				contas.add(lancamento.getConta());
				Lancamento model = new Lancamento();
				BeanUtils.copyProperties(lancamento, model, "id");
				model.setPlanilha(proxima);
				model.setConcluido(false);
				Date d = DateUtils.addMonths(model.getData(), 1);
				model.setData(d);
				this.lancamentoRepository.save(model);
			}
		});

		// duplica os limites de gastos para categorias
		this.duplicarLimites(atual, proxima);

		// atualiza o lançamento saldoAnterior
		contas.stream().filter(o -> TipoConta.CC.equals(o.getTipo())).forEach(conta -> {
			this.lancamentoService.atualizaSaldo(atual, conta);
		});

	}

	private void duplicarLimites(Planilha atual, Planilha proxima) {
		this.gastosRepository.findAllByPlanilha(atual).forEach(limite -> {
			LimiteGastos model = new LimiteGastos();
			model.setCategoria(limite.getCategoria());
			model.setLimite(limite.getLimite());
			model.setPlanilha(proxima);
			this.gastosRepository.save(model);
		});
	}

}
