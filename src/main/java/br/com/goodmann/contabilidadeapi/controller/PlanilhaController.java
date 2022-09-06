package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.dto.ContaDTO;
import br.com.goodmann.contabilidadeapi.dto.PlanilhasAnoDTO;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.service.PlanilhaService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/planilhas")
public class PlanilhaController extends BaseController<Planilha, Integer> {

	@Autowired
	private PlanilhaService planilhaService;

	@Override
	@GetMapping
	public ResponseEntity<List<Planilha>> findAll() {
		return new ResponseEntity<List<Planilha>>(this.planilhaService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}/lancamentos")
	public ResponseEntity<List<Lancamento>> getLancamentos(@PathVariable(required = true, name = "id") Integer id) {
		return new ResponseEntity<List<Lancamento>>(this.planilhaService.getLancamentos(id), HttpStatus.OK);
	}

	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<Planilha> findByAnoAndMes(@PathVariable(required = true, name = "ano") Short ano,
			@PathVariable(required = true, name = "mes") Short mes) {

		Planilha planilha = this.planilhaService.findByAnoAndMes(ano, mes);

		if (planilha == null)
			return new ResponseEntity<Planilha>(new Planilha(), HttpStatus.OK);
		else
			return new ResponseEntity<Planilha>(planilha, HttpStatus.OK);
	}

	@GetMapping("/{id}/extrato")
	public List<ContaDTO> getExtrato(@PathVariable(required = true, name = "id") Integer id) {
		return this.planilhaService.getExtrato(id);
	}

	@GetMapping("/mapa")
	public List<PlanilhasAnoDTO> getMapaPlanilhas() {
		return this.planilhaService.getMapaPlanilhas();
	}

}
