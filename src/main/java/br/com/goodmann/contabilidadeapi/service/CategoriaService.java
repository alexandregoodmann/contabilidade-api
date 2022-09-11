package br.com.goodmann.contabilidadeapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goodmann.contabilidadeapi.model.Categoria;
import br.com.goodmann.contabilidadeapi.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> findByOrderByDescricaoAsc() {
		return this.categoriaRepository.findByOrderByDescricaoAsc();
	}
}
