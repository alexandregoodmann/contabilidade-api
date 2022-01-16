package br.com.goodmann.contabilidadeapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.service.LancamentoService;

@SpringBootTest
public class LancamentoTest {

	@Value("classpath:c6-novembro.txt")
	private Resource resource;

	@Autowired
	private LancamentoService service;

	@Autowired
	private LancamentoRepository repo;

	@Autowired
	private ContaRepository conta;

	@Test
	void cargaArquivo() throws ParseException, IOException {

		File file = this.resource.getFile();
		InputStream stream = new FileInputStream(file);
		MultipartFile multipartFileToSend = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE,
				stream);

		List<String> lista = this.service.lerArquivo(multipartFileToSend);
		List<Conta> contas = this.conta.findAll();

		this.repo.findAll().forEach(e -> {
			System.out.println(e);
		});
	}
}
