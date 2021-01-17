package br.com.goodmann.contabilidadeapi.application.pessoa;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {

}
