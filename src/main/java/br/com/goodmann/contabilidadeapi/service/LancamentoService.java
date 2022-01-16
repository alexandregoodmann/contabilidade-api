package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.controller.CargaJson;
import br.com.goodmann.contabilidadeapi.model.Categoria;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.repository.CategoriaRepository;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@Service
public class LancamentoService {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	private Map<String, Categoria> categorias;

	public void cargaArquivo(CargaJson carga) {

		if (carga.getIdConta() == null || "".equals(carga.getIdConta()))
			throw new RuntimeException("Você precisa informar o ID da conta");

		Optional<Conta> conta = this.contaRepository.findById(carga.getIdConta());
		if (!conta.isPresent())
			throw new RuntimeException("Não foi encontrada a conta para o ID informado");

		categorias = new HashMap<String, Categoria>();
		this.categoriaRepository.findAll().forEach(cat -> {
			categorias.put(cat.getDescricao().toUpperCase(), cat);
		});

		List<Lancamento> lancamentos = new ArrayList<Lancamento>();

		int index = 1;
		try {
			for (String linha : carga.getLinhas()) {
				Lancamento lanc = this.parseLancamento(index, linha);
				lanc.setConta(conta.get());
				lancamentos.add(lanc);
				index++;
			}
		} catch (ParseException e) {
			throw new RuntimeException("Ocorreu um erro ao ler o arquivo. Linha: " + index, e);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Ocorreu um erro ao ler o arquivo. Linha: " + index, e);
		}

		this.lancamentoRepository.saveAll(lancamentos);

	}

	private Lancamento parseLancamento(int index, String linha) throws ParseException {
		String[] vet = linha.split(";");
		Lancamento lanc = new Lancamento();
		lanc.setData(LocalDate.parse(vet[0], this.formatter));
		lanc.setCategoria(this.setCategoria(index, vet[1]));
		lanc.setDescricao(vet[2]);
		String valor = vet[3].replaceAll("/.", "");
		valor = valor.replaceAll(",", ".");
		lanc.setValor(new BigDecimal(valor));
		return lanc;
	}

	private Categoria setCategoria(int index, String descricao) {
		Categoria cat = this.categorias.get(descricao.toUpperCase());
		if (cat == null)
			throw new RuntimeException("A categoria " + descricao + " não foi encontrada. Linha: " + index);
		return cat;
	}

	public List<String> lerArquivo(MultipartFile file) throws IOException {
		InputStreamReader reader = new InputStreamReader(file.getInputStream());
		BufferedReader br = new BufferedReader(reader);
		String linha = br.readLine();
		List<String> lista = new ArrayList<String>();
		while (linha != null) {
			lista.add(linha);
			linha = br.readLine();
		}
		return lista;
	}
}
