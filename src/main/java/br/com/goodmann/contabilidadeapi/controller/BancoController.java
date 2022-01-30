package br.com.goodmann.contabilidadeapi.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Banco;
import br.com.goodmann.contabilidadeapi.repository.BancoRepository;

@RestController
@RequestMapping(value = "v1/bancos")
public class BancoController extends BaseController<Banco, String> {

	@Autowired
	private BancoRepository bancoRepository;

	@PostConstruct
	public void init() {
		super.repo = bancoRepository;
	}
}
