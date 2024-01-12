package br.com.goodmann.contabilidadeapi;

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

import br.com.goodmann.contabilidadeapi.service.PlanilhaAnualService;

@SpringBootTest
public class PlanilhaAnualTest {

	@Autowired
	private PlanilhaAnualService service;

	@Test
	void criarPlanilhaTest() {
		this.service.criarPlanilhaAnual(9455, "Teste");
	}

	@Test
	void cargaArquivo() throws IOException, ParseException {

		File file = new File("/home/alexandre/projetos/contabilidade-api/arquivos/Fatura XP - novembro 2023.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		this.service.cargaXPCartao("Janeiro 2024", 0, mFile);
	}

}
