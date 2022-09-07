package br.com.goodmann.contabilidadeapi;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;

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
public class CargaArquivoBradescoTest {

	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Test
	public void cargaArquivoC6Test() throws IOException, ParseException {

		File file = new File(
				"/home/alexandre/projetos/contabilidade-api/src/main/resources/cargaArquivo/Bradesco_07092022_124002.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		Conta conta = this.contaRepository.findAll().get(0);
		Planilha planilha = this.planilhaRepository.findAll().get(0);

		Map<String, Object> mapa = this.arquivoService.cargaArquivoBradesco(conta.getId(), planilha.getId(), mFile);

		int qtd = (int) mapa.get("qtdLancamentos");
		assertTrue(qtd == 14);
	}
}
