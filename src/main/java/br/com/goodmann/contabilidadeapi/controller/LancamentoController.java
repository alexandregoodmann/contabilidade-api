package br.com.goodmann.contabilidadeapi.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.service.LancamentoService;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/lancamentos")
public class LancamentoController extends BaseController<Lancamento, String> {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@PostMapping("/carga")
	public void cargaArquivo(@RequestBody CargaJson carga) throws ParseException {
		this.lancamentoService.cargaArquivo(carga);
	}

	@Override
	@PostMapping
	public ResponseEntity<Lancamento> create(@RequestBody Lancamento model) {
		if (model.getRepetir() > 1) {
			for (int i = 0; i < model.getRepetir(); i++) {
				Lancamento entity = new Lancamento();
				BeanUtils.copyProperties(model, entity);
				entity.setData(model.getData().plusMonths(i));
				super.create(entity);
			}
			return new ResponseEntity<Lancamento>(model, HttpStatus.CREATED);
		}
		return super.create(model);
	}

	@GetMapping("/findByPeriod")
	public List<Lancamento> findByPeriod() {
		// return this.lancamentoRepository.findByPeriod(LocalDate.of(2021, 10, 1),
		// LocalDate.of(2021, 10, 30));
		throw new NullPointerException();
	}
}
