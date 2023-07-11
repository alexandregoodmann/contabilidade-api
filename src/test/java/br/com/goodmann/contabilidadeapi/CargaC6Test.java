package br.com.goodmann.contabilidadeapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

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
import javassist.NotFoundException;

/**
 * Feito para mudar como é feita a carga de arquivo C6. Passar a ler o novo
 * arquivo CSV dispolibinizado pelo banco.
 * 
 * @author root
 *
 */
@SpringBootTest
public class CargaC6Test {

	@Autowired
	private ArquivoService arquivoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Test
	public void cargaArquivoC6Test() throws IOException, ParseException, NotFoundException {

		// arquivo para upload
		File file = new File("/home/alexandre/projetos/contabilidade/contabilidade-api/arquivos/Fatura_2023-07-10.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		Optional<Conta> bradesco = this.contaRepository.findById(338);
		Planilha planilha = this.planilhaRepository.findByAnoAndMes(Short.valueOf("2023"), Short.valueOf("07"));

		Map<String, Object> mapa = this.arquivoService.cargaArquivoC6(bradesco.get(), planilha, mFile);

		int qtd = (int) mapa.get("qtdLancamentos");
		assertTrue(qtd == 23);

	}

}
