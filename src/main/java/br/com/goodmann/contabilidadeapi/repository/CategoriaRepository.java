package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.goodmann.contabilidadeapi.model.Categoria;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {

}
