package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.core.BaseController;
import br.com.goodmann.contabilidadeapi.model.Lancamento;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, String> {

}
