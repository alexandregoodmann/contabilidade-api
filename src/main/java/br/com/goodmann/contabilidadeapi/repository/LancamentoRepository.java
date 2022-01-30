package br.com.goodmann.contabilidadeapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, String> {

	@Query("{'data' : { $gte: ?0, $lte: ?1 } }")
	public List<Lancamento> findByPeriod(LocalDate from, LocalDate to);
}
