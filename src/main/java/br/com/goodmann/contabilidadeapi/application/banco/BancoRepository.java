package br.com.goodmann.contabilidadeapi.application.banco;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BancoRepository extends MongoRepository<Banco, String> {

}
