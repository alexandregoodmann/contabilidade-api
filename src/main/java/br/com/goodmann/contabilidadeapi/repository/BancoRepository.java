package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.goodmann.contabilidadeapi.model.Banco;

public interface BancoRepository extends MongoRepository<Banco, String> {

}
