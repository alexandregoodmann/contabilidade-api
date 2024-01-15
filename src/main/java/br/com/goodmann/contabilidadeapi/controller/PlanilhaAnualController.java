package br.com.goodmann.contabilidadeapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.dto.PlanilhaAnualDTO;
import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaAnualRepository;
import br.com.goodmann.contabilidadeapi.service.PlanilhaAnualService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/planilhaanual")
public class PlanilhaAnualController extends BaseController<PlanilhaAnual, Integer> {

	@Autowired
	private PlanilhaAnualService planilhaAnualService;

	@Autowired
	private PlanilhaAnualRepository planilhaAnualRepository;

	@GetMapping("/planilhas")
	public List<String> getPlanilhas() {
		return this.planilhaAnualRepository.getPlanilhas();
	}

	@GetMapping("/planilhaanual/{titulo}")
	public List<PlanilhaAnual> findByTitulo(@PathVariable(name = "titulo") String titulo) {
		return this.planilhaAnualService.findByTitulo(titulo);
	}

	@PostMapping("/criar")
	public void criarPlanilhaAnual(@RequestBody PlanilhaAnualDTO dto) {
		this.planilhaAnualService.criarPlanilhaAnual(dto.getIdPlanilha(), dto.getTitulo());
	}

	@Override
	@PostMapping
	public ResponseEntity<PlanilhaAnual> create(@RequestBody PlanilhaAnual model) {
		return new ResponseEntity<PlanilhaAnual>(this.planilhaAnualService.criarLancamento(model), HttpStatus.CREATED);
	}

	@PostMapping("/rename")
	public void rename(@RequestBody PlanilhaAnualDTO dto) {
		this.planilhaAnualService.rename(dto.getNovoTitulo(), dto.getTitulo());
	}

	@PostMapping("/{planilha}/duplicar")
	public void duplicar(@PathVariable(name = "planilha") String planilha) {
		this.planilhaAnualService.duplicar(planilha);
	}

	@DeleteMapping("/planilhaanual/{planilha}")
	public void delete(@PathVariable(name = "planilha") String planilha) {
		this.planilhaAnualService.delete(planilha);
	}

	@PostMapping("/uploadFile")
	public void uploadFile(@RequestParam("titulo") String titulo, @RequestParam("mes") Integer mes,
			@RequestParam("file") MultipartFile file) throws IOException, ParseException {
		this.planilhaAnualService.cargaXPCartao(titulo, mes, file);
	}
}
