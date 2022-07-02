package br.com.goodmann.contabilidadeapi.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.service.LancamentoService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, Integer> {

	@Autowired
	private LancamentoService lancamentoService;

	@PostMapping("/carga")
	public void cargaArquivo(@RequestBody CargaJson carga) throws ParseException {
		this.lancamentoService.cargaArquivo(carga);
	}

}
