package br.com.goodmann.contabilidadeapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

import br.com.goodmann.contabilidadeapi.service.ArquivoService;
import javassist.NotFoundException;

@SpringBootTest
public class CargaC6Test {

	@Autowired
	private ArquivoService arquivoService;

	@Test
	public void cargaArquivoC6Test() throws IOException, ParseException, NotFoundException {

		File file = new File("/home/alexandre/projetos/contabilidade-api/arquivos/Fatura_2023-08-10.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		Map<String, Object> mapa = this.arquivoService.cargaArquivo(false, 340, 7367, mFile);

		int qtd = (int) mapa.get("qtdLancamentos");
		assertTrue(qtd == 22);

	}

}
