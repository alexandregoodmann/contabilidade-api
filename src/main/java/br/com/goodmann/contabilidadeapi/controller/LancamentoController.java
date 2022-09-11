package br.com.goodmann.contabilidadeapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;
import javassist.NotFoundException;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, Integer> {

	@Autowired
	private ArquivoService arquivoService;

	@PostMapping("/uploadFile")
	public Map<String, Object> uploadFile(@RequestParam("idConta") Integer idConta,
			@RequestParam("idPlanilha") Integer idPlanilha, @RequestParam("file") MultipartFile file)
			throws IOException, ParseException, NotFoundException {
		return this.arquivoService.cargaArquivo(idConta, idPlanilha, file);
	}

}
