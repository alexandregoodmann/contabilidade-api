package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.goodmann.contabilidadeapi.model.Pessoa;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {

}
