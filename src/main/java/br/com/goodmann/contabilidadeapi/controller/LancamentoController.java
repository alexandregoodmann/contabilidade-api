package br.com.goodmann.contabilidadeapi.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, Integer> {

	@Autowired
	private ArquivoService arquivoService;

	@PostMapping("/uploadFile")
	public void uploadFile(@RequestParam("idConta") Integer idConta, @RequestParam("file") MultipartFile file) throws IOException, ParseException {
		this.arquivoService.cargaArquivoC6(idConta, file);
	}
}
