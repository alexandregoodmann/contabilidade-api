package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.application.banco.Banco;
import br.com.goodmann.contabilidadeapi.core.BaseController;

@RestController
@RequestMapping(value = "v1/bancos")
public class BancoController extends BaseController<Banco, String> {

}
