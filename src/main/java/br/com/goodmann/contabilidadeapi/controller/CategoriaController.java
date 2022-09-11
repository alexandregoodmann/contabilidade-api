package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Categoria;
import br.com.goodmann.contabilidadeapi.service.CategoriaService;

@RestController
@RequestMapping(value = "v1/categorias")
public class CategoriaController extends BaseController<Categoria, Integer> {

	@Autowired
	private CategoriaService categoriaService;

	@Override
	public ResponseEntity<List<Categoria>> findAll() {
		return new ResponseEntity<List<Categoria>>(this.categoriaService.findByOrderByDescricaoAsc(), HttpStatus.OK);
	}
}
