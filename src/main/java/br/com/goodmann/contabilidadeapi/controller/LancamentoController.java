package br.com.goodmann.contabilidadeapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.dto.ListDTO;
import br.com.goodmann.contabilidadeapi.dto.ProcessaLabelDTO;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;
import br.com.goodmann.contabilidadeapi.service.LancamentoService;
import javassist.NotFoundException;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, Integer> {

	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private LancamentoService lancamentoService;

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> findById(@PathVariable("id") Integer id) {
		return new ResponseEntity<Lancamento>(this.lancamentoService.findById(id), HttpStatus.OK);
	}

	@Override
	@PostMapping
	public ResponseEntity<Lancamento> create(@RequestBody Lancamento model) {
		return new ResponseEntity<Lancamento>(this.lancamentoService.create(model), HttpStatus.CREATED);
	}

	@Override
	@PutMapping
	public ResponseEntity<Lancamento> update(@RequestBody Lancamento model) {
		return new ResponseEntity<Lancamento>(this.lancamentoService.update(model), HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true, name = "id") Integer id) {
		this.lancamentoService.delete(id);
	}

	@PostMapping("/uploadFile")
	public Map<String, Object> uploadFile(@RequestParam("limpar") Boolean limpar,
			@RequestParam("idConta") Integer idConta, @RequestParam("idPlanilha") Integer idPlanilha,
			@RequestParam("file") MultipartFile file) throws IOException, ParseException, NotFoundException {
		return this.arquivoService.cargaArquivo(limpar, idConta, idPlanilha, file);
	}

	@PostMapping("/deleteall")
	public void deleteAll(@RequestBody ListDTO<Integer> dto) {
		this.lancamentoService.deleteAllById(dto.getList());
	}

	@PostMapping("/processarLabels")
	public void processarLabels(@RequestBody ProcessaLabelDTO dto) {
		this.lancamentoService.processarLabels(dto.getIdPlanilha(), dto.getIdConta());
	}
}
