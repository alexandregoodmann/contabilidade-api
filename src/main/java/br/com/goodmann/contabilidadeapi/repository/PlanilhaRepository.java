package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.goodmann.contabilidadeapi.dto.AnaliseCategoriaDTO;
import br.com.goodmann.contabilidadeapi.dto.ResumoExtratoDTO;
import br.com.goodmann.contabilidadeapi.dto.SaldoContas;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface PlanilhaRepository extends JpaRepository<Planilha, Integer> {

	static final String ANALISE_CATEGORIA = "SELECT l2.descricao, sum(cast(l.valor * (-1) as signed)) as soma, (select valor_limite from label where descricao = l2.descricao) as limite"
			+ " FROM lancamento l "
			+ " inner join planilha p on l.id_planilha = p.id"
			+ " inner join lancamento_label ll on ll.id_lancamento = l.id "
			+ " inner join label l2 on l2.id = ll.id_label where p.ano=:ano and p.mes=:mes"
			+ " and l2.analisar = true and l.valor < 0 GROUP by l2.descricao order by soma";

	static final String RESUMO_EXTRATO = "select c.descricao as conta, l.descricao as lancamento, l.valor, l.fixo from lancamento l "
			+ "join planilha p on p.id = l.id_planilha join conta c on c.id = l.id_conta "
			+ "where p.ano=:ano and p.mes=:mes and c.tipo = 'CC' order by l.valor";

	static final String SALDO_CONTAS = "	select descricao as conta,"
			+ "	(select sum(valor) from lancamento l where l.id_conta = c.id and l.id_planilha=:idPlanilha and l.concluido = 1) as saldo,"
			+ "	(select sum(valor) from lancamento l where l.id_conta = c.id and l.id_planilha=:idPlanilha) as previsto"
			+ "	from conta c order by saldo";

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

	@Query(value = RESUMO_EXTRATO, nativeQuery = true)
	List<ResumoExtratoDTO> resumoExtrato(@Param("ano") Integer ano, @Param("mes") Integer mes);

	@Query(value = SALDO_CONTAS, nativeQuery = true)
	List<SaldoContas> getSaldoContas(@Param("idPlanilha") Integer idPlanilha);

}
