package br.com.goodmann.contabilidadeapi;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import br.com.goodmann.contabilidadeapi.json.CargaJson;
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

		List<String> lista = this.service.leitor(this.resource.getFile().getPath());
		List<Conta> contas = this.conta.findAll();

		CargaJson json = new CargaJson();
		json.setIdConta(contas.get(0).getId());
		json.setLinhas(lista);

		this.service.cargaArquivo(json);

		this.repo.findAll().forEach(e -> {
			System.out.println(e);
		});
	}
}
