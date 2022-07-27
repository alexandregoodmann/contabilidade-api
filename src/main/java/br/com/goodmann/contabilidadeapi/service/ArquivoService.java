package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.dto.ArquivoDTO;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;

@Service
public class ArquivoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private ContaRepository contaRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public void cargaArquivoC6(Integer idConta, MultipartFile multipartFile) throws IOException, ParseException {
		this.lerArquivoC6(multipartFile).forEach(dto -> {
			Lancamento lanc = new Lancamento();
			Conta conta = new Conta();
			conta.setId(idConta);
			lanc.setConta(conta);
			lanc.setData(dto.getDataLancamento());
			lanc.setDescricao(dto.getItem());
			lanc.setValor(dto.getValor());
		});
	}

	private List<ArquivoDTO> lerArquivoC6(MultipartFile multipartFile) throws IOException, ParseException {

		List<ArquivoDTO> lista = new ArrayList<ArquivoDTO>();
		InputStream inputStream = multipartFile.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

		List<String> lines = reader.lines().toList();
		for (String line : lines) {
			String[] vet = line.split(";");
			ArquivoDTO dto = new ArquivoDTO(this.sdf.parse(vet[0]), vet[1], new BigDecimal(vet[2]));
			lista.add(dto);
		}
		reader.close();
		return lista;
	}
}
