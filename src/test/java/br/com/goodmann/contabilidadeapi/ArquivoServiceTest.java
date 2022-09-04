package br.com.goodmann.contabilidadeapi;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;

@SpringBootTest
public class ArquivoServiceTest {

	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Test
	public void readFileTest() throws IOException, ParseException {

		File file = new File(
				"/home/alexandre/projetos/contabilidade-api/src/main/resources/cargaArquivo/fatura-c6-julho.txt");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE,
				stream);
		
		Conta conta = this.contaRepository.findAll().get(0);
		Planilha planilha = this.planilhaRepository.findAll().get(0);
		
		this.arquivoService.lerArquivoC6(conta.getId(), planilha.getId(), multipartFileToSend);
		
		assertTrue(true);
	}
}
