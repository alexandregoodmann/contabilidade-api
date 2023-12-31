package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;

public interface PlanilhaAnualRepository extends JpaRepository<PlanilhaAnual, Integer> {

	static final String INSERT_BASE = "insert into planilha_anual(id, titulo, data, conta, tipo_conta, descricao, tipo_lancamento, fixo, parcelas, valor) "
			+ "	            select l.id, ?2, l.data, c.descricao, c.tipo, l.descricao, l.tipo, l.fixo, l.parcelas, l.valor"
			+ "	from lancamento l inner join conta c on c.id  = l.id_conta where l.id_planilha=?1";

	@Modifying
	@Query(value = "delete from planilha2024", nativeQuery = true)
	void deleteAll();

	@Modifying
	@Query(value = INSERT_BASE, nativeQuery = true)
	void criarPlanilhaAnual(Integer idPlanilha, String titulo);

	List<PlanilhaAnual> findAllByTitulo(String titulo);

	@Query(value = "select distinct(titulo) from planilha_anual order by titulo", nativeQuery = true)
	List<String> getPlanilhas();

}
