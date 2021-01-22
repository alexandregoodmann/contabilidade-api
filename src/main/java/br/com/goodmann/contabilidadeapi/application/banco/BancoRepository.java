package br.com.goodmann.contabilidadeapi.application.banco;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BancoRepository extends MongoRepository<Banco, String> {

	List<Banco> findByBancoContainingIgnoreCase(String banco);
	
}
