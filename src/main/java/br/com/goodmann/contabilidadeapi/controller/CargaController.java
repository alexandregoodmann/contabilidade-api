package br.com.goodmann.contabilidadeapi.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	LancamentoRepository lancamentoRepository;

	@PostMapping("/c6")
	public void create(@RequestBody CargaJson model) {

		Optional<Conta> c6 = this.contaRepository.findById("600f098e662c464963eedd5b");
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();

		model.getLinhas().forEach(linha -> {
			try {
				Lancamento lanc = this.parseLancamento(c6.get(), linha);
				lancamentos.add(lanc);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		this.lancamentoRepository.saveAll(lancamentos);
	}

	private Lancamento parseLancamento(Conta conta, String linha) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

		int index = linha.indexOf("R$");
		String sData = linha.substring(0, 10);
		String descricao = linha.substring(11, index);
		String valor = linha.substring(index + 3).replaceAll("/.", "").replaceAll(",", ".");

		Lancamento lanc = new Lancamento();
		lanc.setConta(conta);
		lanc.setData(sdf.parse(sData));
		lanc.setDescricao(descricao);
		lanc.setValor(BigDecimal.valueOf(Double.valueOf(valor)));

		return lanc;
	}

}
