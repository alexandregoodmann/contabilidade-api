package br.com.goodmann.contabilidadeapi.application.conta;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContaRepository extends MongoRepository<Conta, String> {

}
