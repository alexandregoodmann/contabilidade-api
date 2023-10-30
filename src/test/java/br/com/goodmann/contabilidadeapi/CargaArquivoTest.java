package br.com.goodmann.contabilidadeapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;
import br.com.goodmann.contabilidadeapi.service.SodexoService;
import javassist.NotFoundException;

@SpringBootTest
public class CargaArquivoTest {

	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private SodexoService sodexoService;

	// @Test
	public void cargaArquivoC6Test() throws IOException, ParseException, NotFoundException {

		// arquivo para upload
		File file = new File(
				"/home/alexandre/projetos/contabilidade/contabilidade-api/arquivos/Bradesco_20072022_145023.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		// Exemplo de conta
		Conta conta = new Conta();
		Example<Conta> example = Example.of(conta);
		conta = this.contaRepository.findOne(example).get();

		// Example de Planilha
		Planilha planilha = new Planilha();
		planilha.setAno((short) 2022);
		planilha.setMes((short) 7);
		Example<Planilha> pExample = Example.of(planilha);
		planilha = this.planilhaRepository.findOne(pExample).get();

		/*
		 * Map<String, Object> mapa = this.arquivoService.cargaArquivo(conta.getId(),
		 * planilha.getId(), mFile);
		 * 
		 * int qtd = (int) mapa.get("qtdLancamentos"); assertTrue(qtd == 47);
		 */
	}

	// @Test
	public void cargaArquivoBradescoTest() throws IOException, ParseException, NotFoundException {

		File file = new File(
				"/home/alexandre/projetos/contabilidade/contabilidade-api/arquivos/Bradesco_15052023_223228.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		Conta conta = this.contaRepository.findById(338).get();

		Planilha planilha = new Planilha();
		planilha.setAno((short) 2023);
		planilha.setMes((short) 5);
		Example<Planilha> pExample = Example.of(planilha);
		planilha = this.planilhaRepository.findOne(pExample).get();

		this.arquivoService.cargaArquivo(conta.getId(), planilha.getId(), mFile);
	}

	// @Test
	public void cargaArquivoItauTest() throws IOException, ParseException, NotFoundException {

		File file = new File("/home/alexandre/projetos/contabilidade/contabilidade-api/arquivos/extrato_itau.txt");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		this.arquivoService.cargaArquivo(343, 4747, mFile);
	}

	// @Test
	public void cargaSodexoTest() throws IOException, ParseException {

		File file = new File("/home/alexandre/projetos/contabilidade-api/arquivos/sodexo.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		Conta conta = new Conta();
		conta.setId(5081);

		Planilha planilha = new Planilha(8171);
		planilha.setAno(Short.valueOf("2023"));
		planilha.setMes(Short.valueOf("10"));

		this.sodexoService.cargaArquivoSodexo(conta, planilha, mFile);
	}

	//@Test
	public void cargaXPTest() throws IOException, ParseException {

		File file = new File(
				"/home/alexandre/projetos/contabilidade-api/arquivos/extrato_de_27-09-2023_ate_27-10-2023.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		Conta conta = new Conta();
		conta.setId(8710);

		Planilha planilha = new Planilha(8171);
		planilha.setAno(Short.valueOf("2023"));
		planilha.setMes(Short.valueOf("10"));

		this.sodexoService.cargaXP(conta, planilha, mFile);
	}
	
	@Test
	public void cargaXPCredito() throws IOException, ParseException {

		File file = new File(
				"/home/alexandre/projetos/contabilidade-api/arquivos/Fatura XP - novembro 2023.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		Conta conta = new Conta();
		conta.setId(8711);

		Planilha planilha = new Planilha(8171);
		planilha.setAno(Short.valueOf("2023"));
		planilha.setMes(Short.valueOf("10"));

		this.sodexoService.cargaXPCartao(conta, planilha, mFile);
	}

}
