package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.dto.AnaliseDTO;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/analise")
public class AnaliseController {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@GetMapping("/{ano}")
	public List<AnaliseDTO> getAnalise(@PathVariable(required = true, name = "ano") Integer ano) {
		return this.planilhaRepository.getAnaliseAno(ano);
	}

	@GetMapping("/{ano}/{mes}")
	public List<AnaliseDTO> getAnalise(@PathVariable(name = "ano") Integer ano,
			@PathVariable(name = "mes") Integer mes) {
		return this.planilhaRepository.getAnaliseAnoMes(ano, mes);
	}

}
