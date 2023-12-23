package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.dto.AnaliseCategoriaDTO;
import br.com.goodmann.contabilidadeapi.dto.ResumoExtratoDTO;
import br.com.goodmann.contabilidadeapi.dto.SaldoContas;
import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.service.PlanilhaService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/analise")
public class AnaliseController {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private PlanilhaService planilhaService;

	@GetMapping("/categoria/{ano}/{mes}")
	public List<AnaliseCategoriaDTO> getAnalise(@PathVariable(name = "ano") Integer ano,
			@PathVariable(name = "mes") Integer mes) {
		return this.planilhaRepository.analiseCategoria(ano, mes);
	}

	@GetMapping("/resumoextrato/{ano}/{mes}")
	public List<ResumoExtratoDTO> getResumoExtrato(@PathVariable(name = "ano") Integer ano,
			@PathVariable(name = "mes") Integer mes) {
		return this.planilhaRepository.resumoExtrato(ano, mes);
	}

	@GetMapping("/saldocontas/{idPlanilha}")
	public List<SaldoContas> getSaldoContas(@PathVariable(name = "idPlanilha") Integer idPlanilha) {
		return this.planilhaRepository.getSaldoContas(idPlanilha);
	}

	@PostMapping("/processarPlanilhaAnual")
	public List<PlanilhaAnual> processarPlanilhaAnual() {
		return this.planilhaService.processarPlanilhaAnual();
	}
}
