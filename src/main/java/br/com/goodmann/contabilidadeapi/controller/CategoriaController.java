package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.core.BaseController;
import br.com.goodmann.contabilidadeapi.model.Categoria;

@RestController
@RequestMapping(value = "v1/categorias")
public class CategoriaController extends BaseController<Categoria, String> {

}
