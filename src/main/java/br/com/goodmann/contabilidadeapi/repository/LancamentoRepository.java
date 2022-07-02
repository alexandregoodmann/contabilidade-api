package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

	@Query("from Lancamento l where l.fixo = true")
	List<Lancamento> findAllFixos();
	
	@Query("from Lancamento l join l.planilha p where p.id=:idPlanilha")
	List<Lancamento> getLancamentos(Integer idPlanilha);
}
