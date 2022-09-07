package br.com.goodmann.contabilidadeapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.dto.BancoEnum;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, Integer> {

	@Autowired
	private ArquivoService arquivoService;

	@PostMapping("/uploadFile")
	public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("banco") BancoEnum banco,
			@RequestParam("idConta") Integer idConta, @RequestParam("idPlanilha") Integer idPlanilha,
			@RequestParam("file") MultipartFile file) throws IOException, ParseException {

		Map<String, Object> mapa = null;

		switch (banco) {
		case BRADESCO:
			mapa = this.arquivoService.cargaArquivoBradesco(idConta, idPlanilha, file);
			break;
		case C6:
			mapa = this.arquivoService.cargaArquivoC6(idConta, idPlanilha, file);
			break;

		default:
			return new ResponseEntity<Map<String, Object>>(mapa, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Map<String, Object>>(mapa, HttpStatus.OK);
	}

}
