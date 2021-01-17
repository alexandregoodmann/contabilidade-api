package br.com.goodmann.contabilidadeapi.application.lancamento;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LancamentoRepository extends MongoRepository<Lancamento, String> {

}
