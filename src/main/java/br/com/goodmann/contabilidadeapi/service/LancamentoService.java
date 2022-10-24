package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.repository.CategoriaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

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

	public void deleteAllById(List<Integer> ids) {
		List<Lancamento> lancamentos = this.lancamentoRepository.findAllById(ids);
		this.lancamentoRepository.deleteAll(lancamentos);
	}

	public void concluir(List<Integer> ids) {
		this.lancamentoRepository.findAllById(ids).forEach(lancamento -> {
			lancamento.setConcluido(true);
			this.lancamentoRepository.save(lancamento);
		});
	}
	
	public void fixo(List<Integer> ids) {
		this.lancamentoRepository.findAllById(ids).forEach(lancamento -> {
			lancamento.setFixo(true);
			this.lancamentoRepository.save(lancamento);
		});
	}

	public void categorizar(@NotEmpty List<Integer> ids, @NotNull Integer idCategoria) throws NoSuchObjectException {

		var opt = this.categoriaRepository.findById(idCategoria);
		if (!opt.isPresent())
			throw new NoSuchObjectException("The idCategoria was not found: " + idCategoria);

		this.lancamentoRepository.findAllById(ids).forEach(lancamento -> {
			lancamento.setCategoria(opt.get());
			this.lancamentoRepository.save(lancamento);
		});
	}

}
