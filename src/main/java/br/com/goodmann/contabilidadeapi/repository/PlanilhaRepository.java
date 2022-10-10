package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.goodmann.contabilidadeapi.dto.AnaliseDTO;
import br.com.goodmann.contabilidadeapi.dto.AnaliseSaldoDTO;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface PlanilhaRepository extends JpaRepository<Planilha, Integer> {

	@Query("from Planilha p order by p.ano desc, p.mes asc")
	List<Planilha> findAll();

	Planilha findByAnoAndMes(Short ano, Short mes);

	@Query(value = "select c.descricao, count(*) as qtd, sum(l.valor)*(-1) as valor "
			+ "	from lancamento l join categoria c on l.id_categoria = c.id where l.id_planilha=:idPlanilha "
			+ "	and c.analisar = 1 and l.concluido = 1 group by c.descricao order by valor", nativeQuery = true)
	public List<AnaliseDTO> getAnalisePlanilha(@Param("idPlanilha") Integer idPlanilha);

	@Query(value = "select p.mes, c.descricao, count(*) as qtd, sum(l.valor)*(-1) as valor from lancamento l "
			+ "	join categoria c on l.id_categoria = c.id " + "	inner join planilha p on p.id = l.id_planilha "
			+ "	and c.analisar = 1 " + "	and p.ano=:ano" + "	group by p.mes, c.descricao "
			+ "	order by p.mes, c.descricao " + "	", nativeQuery = true)
	public List<AnaliseDTO> getAnaliseCategoriaAno(@Param("ano") Integer ano);

	@Query(value = "select ano, descricao as mes, "
			+ "	(select sum(valor) from lancamento l where l.valor > 0 and l.id_planilha = p.id) as entrada, "
			+ "	(select sum(valor)*(-1) from lancamento l where l.valor < 0 and l.id_planilha = p.id) as saida, "
			+ "	(select sum(valor) from lancamento l where l.id_planilha = p.id) as saldo "
			+ "from planilha p where p.ano=:ano", nativeQuery = true)
	public List<AnaliseSaldoDTO> getAnaliseSaldoAno(@Param("ano") Integer ano);

}
