package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.Sublancamento;

public interface SublancamentoRepository extends JpaRepository<Sublancamento, Integer> {

	@Query("from sublancamento s join s.lancamento.planilha p where p.id=:idPlanilha")
	List<Sublancamento> findAllByIdPlanilha(Integer idPlanilha);
}
