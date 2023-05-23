package br.com.goodmann.contabilidadeapi;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.service.ArquivoService;

@SpringBootTest
public class UpLoadDownloadTest {

	@Autowired
	private ArquivoService arquivoService;

	@Test
	public void test2() throws IOException {
		this.arquivoService.downloadExtrato(2023, 5);
	}

}
