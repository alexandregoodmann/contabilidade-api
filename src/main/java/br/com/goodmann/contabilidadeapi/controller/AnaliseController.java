package br.com.goodmann.contabilidadeapi.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.dto.AnaliseDTO;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/analise")
public class AnaliseController {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private ArquivoService arquivoService;

	@GetMapping("/{ano}")
	public List<AnaliseDTO> getAnalise(@PathVariable(required = true, name = "ano") Integer ano) {
		return this.planilhaRepository.getAnaliseAno(ano);
	}

	@GetMapping("/{ano}/{mes}")
	public List<AnaliseDTO> getAnalise(@PathVariable(name = "ano") Integer ano,
			@PathVariable(name = "mes") Integer mes) {
		return this.planilhaRepository.getAnaliseAnoMes(ano, mes);
	}

	@GetMapping("/downloadextrato/{ano}/{mes}")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable(name = "ano") Integer ano,
			@PathVariable(name = "mes") Integer mes) throws IOException {
		Resource file = this.arquivoService.downloadExtrato(ano, mes);
		Path path = file.getFile().toPath();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
