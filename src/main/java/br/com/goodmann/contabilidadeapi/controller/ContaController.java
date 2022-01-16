package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Conta;

@RestController
@RequestMapping(value = "v1/contas")
public class ContaController extends BaseController<Conta, String> {

}
