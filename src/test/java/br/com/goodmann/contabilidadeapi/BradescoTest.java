package br.com.goodmann.contabilidadeapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Map;

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
import br.com.goodmann.contabilidadeapi.service.BradescoService;
import javassist.NotFoundException;

@SpringBootTest
public class BradescoTest {

	@Autowired
	private BradescoService bradescoService;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	// @Test
	public void linhaTeste() throws IOException {

		File file = new File(
				"/home/alexandre/projetos/contabilidade/contabilidade-api/arquivos/Bradesco_15052023_223228.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		this.bradescoService.montarLinhas(mFile).forEach(line -> {
			System.out.println(line);
		});
	}

	@Test
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

		Map<String, Object> mp = this.bradescoService.cargaArquivo(conta.getId(), planilha.getId(), mFile);
		System.out.println(mp.get("qtdLancamentos"));
	}

}
