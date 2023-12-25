package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;

public interface PlanilhaAnualRepository extends JpaRepository<PlanilhaAnual, Integer> {

	static final String INSERT_BASE = "insert into planilha2024(id, data, conta, tipo_conta, descricao, tipo_lancamento, fixo, parcelas, valor) "
			+ "	            select l.id, l.data, c.descricao, c.tipo, l.descricao, l.tipo, l.fixo, l.parcelas, l.valor"
			+ "	from lancamento l inner join conta c on c.id  = l.id_conta "
			+ "	inner join planilha p on p.id = l.id_planilha where p.ano = 2023 and p.mes = 12";

	@Modifying
	@Query(value = "delete from planilha2024", nativeQuery = true)
	void deleteAll();

	@Modifying
	@Query(value = INSERT_BASE, nativeQuery = true)
	void insertBase();
}
