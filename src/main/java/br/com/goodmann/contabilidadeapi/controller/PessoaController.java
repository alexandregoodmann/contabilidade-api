package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Pessoa;

@RestController
@RequestMapping(value = "v1/pessoas")
public class PessoaController extends BaseController<Pessoa, Integer> {

}
