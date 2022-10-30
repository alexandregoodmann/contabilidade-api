package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Banco;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.repository.BancoRepository;

@RestController
@RequestMapping(value = "v1/contas")
public class ContaController extends BaseController<Conta, Integer> {

	@Autowired
	private BancoRepository bancoRepository;

	@GetMapping("/bancos")
	public List<Banco> findAllBancos() {
		return this.bancoRepository.findAll();
	}

}
