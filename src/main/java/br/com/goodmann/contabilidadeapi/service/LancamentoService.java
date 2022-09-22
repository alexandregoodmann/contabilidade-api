package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

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
}
