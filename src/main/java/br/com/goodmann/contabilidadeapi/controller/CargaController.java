package br.com.goodmann.contabilidadeapi.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.json.CargaJson;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@CrossOrigin
@RestController
@RequestMapping(value = "v1/carga")
public class CargaController {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	LancamentoRepository lancamentoRepository;

	@PostMapping
	public void create(@RequestBody CargaJson model) throws ParseException {

		Optional<Conta> conta = this.contaRepository.findById(model.getIdConta());
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();

		for (String linha : model.getLinhas()) {
			Lancamento lanc = this.parseLancamento(linha);
			lanc.setConta(conta.get());
			lancamentos.add(lanc);
		}
		this.lancamentoRepository.saveAll(lancamentos);
	}

	private Lancamento parseLancamento(String linha) throws ParseException {
		String[] vet = linha.split(";");
		Lancamento lanc = new Lancamento();
		Date date = sdf.parse(vet[0]);
		lanc.setData(LocalDate.of(date.getYear(), date.getMonth(), date.getDay()));
		lanc.setDescricao(vet[1]);
		lanc.setValor(BigDecimal.valueOf(Double.valueOf(vet[2])));
		return lanc;
	}

}
