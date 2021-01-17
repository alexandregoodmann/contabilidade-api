package br.com.goodmann.contabilidadeapi.application.categoria;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {

}
