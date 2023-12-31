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
import br.com.goodmann.contabilidadeapi.repository.PlanilhaAnualRepository;
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

	@Autowired
	private PlanilhaAnualRepository planilhaAnualRepository;

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

	@PostMapping("/planilhaanual/processar/{idPlanilha}/{titulo}")
	public void processarPlanilhaAnual(@PathVariable(name = "idPlanilha") Integer idPlanilha,
			@PathVariable(name = "titulo") String titulo) {
		this.planilhaService.criarPlanilhaAnual(idPlanilha, titulo);
	}

	@GetMapping("/planilhaanual")
	public List<String> processarPlanilhaAnual() {
		return this.planilhaAnualRepository.getPlanilhas();
	}

	@GetMapping("/planilhaanual/{titulo}")
	public List<PlanilhaAnual> processarPlanilhaAnual(@PathVariable(name = "titulo") String titulo) {
		return this.planilhaService.findPlanilhaAnualByTitulo(titulo);
	}
}
