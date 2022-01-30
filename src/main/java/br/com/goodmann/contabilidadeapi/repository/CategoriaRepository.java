package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
