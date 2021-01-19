package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.application.lancamento.Lancamento;
import br.com.goodmann.contabilidadeapi.core.BaseController;

@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, String> {
}
