package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.LimiteGastos;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/limitegastos")
public class LimiteGastosController extends BaseController<LimiteGastos, Integer> {

}
