package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.goodmann.contabilidadeapi.model.Conta;

public interface ContaRepository extends MongoRepository<Conta, String> {

}
