package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.goodmann.contabilidadeapi.dto.AnaliseDTO;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface PlanilhaRepository extends JpaRepository<Planilha, Integer> {

	static final String SQL_ANALISE = "select l.id as idLancamento, p.ano, p.descricao as planilha, p.mes, c.descricao as banco, c.tipo, "
			+ "c2.descricao as categoria, c2.analisar, l.`data`, l.descricao,l.valor,l.fixo,l.concluido from lancamento "
			+ "l inner join conta c on c.id = l.id_conta inner join categoria c2 on c2.id = l.id_categoria inner "
			+ "join planilha p on p.id = l.id_planilha where p.ano=:ano";

	@Query("from Planilha p order by p.ano, p.mes")
	List<Planilha> findAll();

	Planilha findByAnoAndMes(Short ano, Short mes);

	@Query(value = SQL_ANALISE, nativeQuery = true)
	List<AnaliseDTO> getAnaliseAno(@Param("ano") Integer ano);

	@Query(value = SQL_ANALISE + " and p.mes=:mes", nativeQuery = true)
	List<AnaliseDTO> getAnaliseAnoMes(@Param("ano") Integer ano, @Param("mes") Integer mes);

}
