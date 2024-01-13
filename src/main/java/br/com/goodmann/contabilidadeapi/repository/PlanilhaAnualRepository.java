package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;

public interface PlanilhaAnualRepository extends JpaRepository<PlanilhaAnual, Integer> {

	static final String INSERT_BASE = "insert into planilha_anual(id_lancamento, titulo, data, conta, tipo_conta, descricao, tipo_lancamento, fixo, parcelas, valor) "
			+ "	            select l.id, ?2, l.data, c.descricao, c.tipo, l.descricao, l.tipo, l.fixo, l.parcelas, l.valor"
			+ "	from lancamento l inner join conta c on c.id  = l.id_conta where l.id_planilha=?1";

	static final String DUPLICAR_PLANILHA = "INSERT INTO planilha_anual (id_lancamento, titulo, data, conta, tipo_conta, descricao, tipo_lancamento, fixo, parcelas, valor, valores) "
			+ "select id_lancamento, ?1, data, conta, tipo_conta, descricao, tipo_lancamento, fixo, parcelas, valor, valores from planilha_anual where titulo=?2";

	@Modifying
	@Query(value = "delete from planilha_anual where titulo=?1", nativeQuery = true)
	void delete(String titulo);

	@Modifying
	@Query(value = INSERT_BASE, nativeQuery = true)
	void criarPlanilhaAnual(Integer idPlanilha, String titulo);

	@Modifying
	@Query(value = DUPLICAR_PLANILHA, nativeQuery = true)
	void duplicar(String novoTitulo, String titulo);

	List<PlanilhaAnual> findAllByTitulo(String titulo);

	@Query(value = "select distinct(titulo) from planilha_anual order by titulo", nativeQuery = true)
	List<String> getPlanilhas();

	@Modifying
	@Query(value = "update planilha_anual set titulo=?1 where titulo=?2", nativeQuery = true)
	void rename(String novo, String velho);

	@Query("from planilhaAnual p where p.conta=:conta and p.parcelas is not null")
	List<PlanilhaAnual> getParcelados(String conta);

}
