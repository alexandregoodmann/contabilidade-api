package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface PlanilhaRepository extends JpaRepository<Planilha, Integer> {

	@Query("from Planilha p order by p.ano desc, p.mes asc")
	List<Planilha> findAll();
}
