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

import br.com.goodmann.contabilidadeapi.application.banco.Banco;
import br.com.goodmann.contabilidadeapi.application.banco.BancoRepository;

@RestController
@RequestMapping(value = "v1/bancos")
@CrossOrigin(origins = "http://localhost:4200")
public class BancoController {

	@Autowired
	private BancoRepository bancoRepository;

	@GetMapping("/{desc}")
	public ResponseEntity<List<Banco>> findByDescricao(@PathVariable(required = true, name = "desc") String desc) {
		return new ResponseEntity<List<Banco>>(this.bancoRepository.findByBancoContainingIgnoreCase(desc),
				HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Banco>> findAll() {
		return new ResponseEntity<List<Banco>>(this.bancoRepository.findAll(), HttpStatus.OK);
	}

}
