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
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.CargaEnum;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.ContaRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.service.ArquivoService;
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
	private LancamentoRepository lancamentoRepository;

	@Test
	public void cargaArquivoC6Test() throws IOException, ParseException, NotFoundException {

		// arquivo para upload
		File file = new File(
				"/home/alexandre/projetos/contabilidade-api/src/main/resources/cargaArquivo/fatura-c6-julho.txt");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		// Exemplo de conta
		Conta conta = new Conta();
		conta.setCarga(CargaEnum.C6);
		Example<Conta> example = Example.of(conta);
		conta = this.contaRepository.findOne(example).get();

		// Example de Planilha
		Planilha planilha = new Planilha();
		planilha.setAno((short) 2022);
		planilha.setMes((short) 7);
		Example<Planilha> pExample = Example.of(planilha);
		planilha = this.planilhaRepository.findOne(pExample).get();

		Map<String, Object> mapa = this.arquivoService.cargaArquivo(conta.getId(), planilha.getId(), mFile);

		int qtd = (int) mapa.get("qtdLancamentos");
		assertTrue(qtd == 47);
	}

	@Test
	public void cargaArquivoBradescoTest() throws IOException, ParseException, NotFoundException {

		File file = new File(
				"/home/alexandre/projetos/contabilidade-api/src/main/resources/cargaArquivo/Bradesco_07092022_124002.csv");
		InputStream stream;
		stream = new FileInputStream(file);
		MultipartFile mFile = new MockMultipartFile("file", file.getName(), MediaType.TEXT_HTML_VALUE, stream);

		// Exemplo de conta
		Conta conta = new Conta();
		conta.setCarga(CargaEnum.BRADESCO);
		Example<Conta> example = Example.of(conta);
		conta = this.contaRepository.findOne(example).get();

		// Example de Planilha
		Planilha planilha = new Planilha();
		planilha.setAno((short) 2022);
		planilha.setMes((short) 7);
		Example<Planilha> pExample = Example.of(planilha);
		planilha = this.planilhaRepository.findOne(pExample).get();

		Map<String, Object> mapa = this.arquivoService.cargaArquivo(conta.getId(), planilha.getId(), mFile);

		int qtd = (int) mapa.get("qtdLancamentos");
		assertTrue(qtd == 14);
	}

}
