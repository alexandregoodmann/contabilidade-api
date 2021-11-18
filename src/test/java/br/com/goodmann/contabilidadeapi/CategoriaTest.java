package br.com.goodmann.contabilidadeapi;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.model.Categoria;
import br.com.goodmann.contabilidadeapi.repository.CategoriaRepository;

@SpringBootTest
public class CategoriaTest {

	@Autowired
	private CategoriaRepository repo;

	@Test
	void create() throws ParseException, IOException {
		Categoria cat = new Categoria();
		cat.setDescricao("Pessoal");
		this.repo.save(cat);
	}
}
