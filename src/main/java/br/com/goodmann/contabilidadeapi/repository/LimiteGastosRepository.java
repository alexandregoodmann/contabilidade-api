package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.LimiteGastos;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface LimiteGastosRepository extends JpaRepository<LimiteGastos, Integer> {

	public List<LimiteGastos> findAllByPlanilha(Planilha planilha);
	
	@Modifying
	@Query(value = "delete from limite_gastos lg where lg.id_planilha in (select id from planilha where criacao > date(?1));", nativeQuery = true)
	void deleteLimiteGastosCriacaoPlanilha(String data);
}
