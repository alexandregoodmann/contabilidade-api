package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.goodmann.contabilidadeapi.dto.AnaliseCategoriaDTO;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface PlanilhaRepository extends JpaRepository<Planilha, Integer> {

	static final String ANALISE_CATEGORIA = "SELECT l2.descricao, sum(cast(l.valor * (-1) as signed)) as soma FROM lancamento l inner join planilha p on l.id_planilha = p.id"
			+ " inner join lancamento_label ll on ll.id_lancamento = l.id "
			+ " inner join label l2 on l2.id = ll.id_label where p.ano=:ano and p.mes=:mes "
			+ " and l2.analisar = true and l.valor < 0 GROUP by l2.descricao order by soma";

	@Query("from Planilha p order by p.ano, p.mes")
	List<Planilha> findAll();

	Planilha findByAnoAndMes(Short ano, Short mes);

	@Query("from Planilha p where p.ano>=:ano order by p.ano, p.mes")
	List<Planilha> getPlanilhasFuturas(@Param("ano") Short ano);

	@Modifying
	@Query(value = "delete from planilha  p where criacao > date(?1)", nativeQuery = true)
	void deletePlanilhaByCriacaoPlanilha(String data);

	@Query(value = ANALISE_CATEGORIA, nativeQuery = true)
	List<AnaliseCategoriaDTO> analiseCategoria(@Param("ano") Integer ano, @Param("mes") Integer mes);

}
