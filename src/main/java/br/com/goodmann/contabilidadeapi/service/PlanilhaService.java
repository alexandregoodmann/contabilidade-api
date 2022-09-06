package br.com.goodmann.contabilidadeapi.service;

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

import br.com.goodmann.contabilidadeapi.dto.ContaDTO;
import br.com.goodmann.contabilidadeapi.dto.LancamentoDTO;
import br.com.goodmann.contabilidadeapi.dto.PlanilhasAnoDTO;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class PlanilhaService {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

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

	public List<ContaDTO> getExtrato(Integer idPlanilha) {

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

		List<ContaDTO> contas = new ArrayList<ContaDTO>();

		mapa.forEach((conta, lancamentos) -> {

			ContaDTO contaDTO = new ContaDTO();
			BeanUtils.copyProperties(conta, contaDTO);

			lancamentos.forEach(l -> {
				String categoria = l.getCategoria() == null ? null : l.getCategoria().getDescricao();
				LancamentoDTO lancamentoDTO = new LancamentoDTO(l.getId(), categoria, l.getData(), l.getDescricao(),
						l.getValor(), l.getConcluido());
				contaDTO.getLancamentos().add(lancamentoDTO);
				contaDTO.setTotal(contaDTO.getTotal().add(l.getValor()));
			});

			contas.add(contaDTO);
		});

		return contas;
	}
}
