package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.dto.AnaliseSaldoDTO;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/analises")
public class AnaliseController {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@GetMapping("/saldo/{ano}")
	public List<AnaliseSaldoDTO> getSaldosPorAno(int ano) {
		return this.planilhaRepository.getAnaliseSaldoAno(ano);
	}
}
