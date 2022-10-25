package br.com.goodmann.contabilidadeapi.service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.goodmann.contabilidadeapi.dto.ExtratoDTO;
import br.com.goodmann.contabilidadeapi.dto.LancamentoDTO;
import br.com.goodmann.contabilidadeapi.dto.PlanilhasAnoDTO;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.MesesEnum;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class PlanilhaService {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@org.springframework.data.annotation.Transient
	public void delete(Integer idPlanilha) {
		this.lancamentoRepository.deleteAll(this.lancamentoRepository.getLancamentos(idPlanilha));
		this.planilhaRepository.deleteById(idPlanilha);
	}

	public List<Planilha> findAll() {
		return this.planilhaRepository.findAll();
	}

	public List<Lancamento> getLancamentos(Integer idPlanilha) {
		return this.lancamentoRepository.getLancamentos(idPlanilha);
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

		this.lancamentoRepository.getLancamentos(idPlanilha).forEach(lancamento -> {
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

	private Planilha proximaPlanilha(Integer idPlanilha) {
		Planilha planilha = this.planilhaRepository.findById(idPlanilha).get();
		Planilha model = new Planilha();
		if (planilha.getMes() == 12) {
			model.setMes((short) 1);
			model.setAno((short) (planilha.getAno() + 1));
		} else {
			model.setMes((short) (planilha.getMes() + 1));
			model.setAno(planilha.getAno());
		}
		model.setDescricao(StringUtils.capitalize(MesesEnum.values()[model.getMes() - 1].toString().toLowerCase()));
		return this.planilhaRepository.save(model);
	}

	@Transient
	public void duplicarPlanilha(Integer idPlanilha) {
		Planilha proxima = this.proximaPlanilha(idPlanilha);
		this.lancamentoRepository.getLancamentos(idPlanilha).forEach(lancamento -> {
			Lancamento model = new Lancamento();
			BeanUtils.copyProperties(lancamento, model, "id");
			model.setPlanilha(proxima);
			model.setConcluido(false);
			model.getData().setMonth(proxima.getMes());
			this.lancamentoRepository.save(model);
		});
	}

}
