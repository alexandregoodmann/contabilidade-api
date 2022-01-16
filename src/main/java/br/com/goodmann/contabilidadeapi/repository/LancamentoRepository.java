package br.com.goodmann.contabilidadeapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.goodmann.contabilidadeapi.model.Lancamento;

public interface LancamentoRepository extends MongoRepository<Lancamento, String> {

	@Query("{'data' : { $gte: ?0, $lte: ?1 } }")                 
	public List<Lancamento> findByPeriod(LocalDate from, LocalDate to);
}
