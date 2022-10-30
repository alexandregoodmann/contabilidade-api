package br.com.goodmann.contabilidadeapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Banco;
import br.com.goodmann.contabilidadeapi.repository.BancoRepository;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;

@SpringBootTest
public class BancoTeste {

	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private BancoRepository bancoRepository;

	@Test
	public void cargaBancoTeste() throws IOException {

		// arquivo para upload
		File file = new File("/home/alexandre/projetos/contabilidade/contabilidade-api/arquivos/bancos.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		if (this.bancoRepository.findAll().size() > 0)
			return;

		List<String> lines = this.arquivoService.readLines(mFile);

		for (int i = 1; i < lines.size(); i++) {

			String[] vet = lines.get(i).split(",");

			Banco banco = new Banco();
			banco.setIspb(vet[0]);
			banco.setNomeReduzido(vet[1]);
			banco.setCodigo(vet[2]);
			banco.setNomeExtenso(vet[5]);

			this.bancoRepository.save(banco);
		}

		lines.forEach(e -> {
			System.out.println(e);
		});
	}
}
