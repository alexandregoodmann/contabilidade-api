package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.enums.TipoLancamento;
import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

	@Query("from Lancamento l join l.planilha p where p.id=:idPlanilha")
	List<Lancamento> findAllByIdPlanilha(Integer idPlanilha);

	List<Lancamento> findAllByPlanilha(Planilha planilha);

	List<Lancamento> findAllByPlanilhaAndConta(Planilha planilha, Conta conta);

	@Query("from Lancamento l join l.planilha p where p.id=:idPlanilha and l.numeroBradesco is not null")
	List<Lancamento> findAllNumeroBradesco(Integer idPlanilha);

	@Query("from Lancamento l where l.planilha=:planilha and l.conta=:conta and l.tipo=:tipo")
	List<Lancamento> findByPlanilhaContaTipo(Planilha planilha, Conta conta, TipoLancamento tipo);

	List<Lancamento> findByPlanilhaAndTipo(Planilha planilha, TipoLancamento tipo);

	List<Lancamento> findAllByHash(String hash);

	@Modifying
	@Query(value = "delete from lancamento l where l.id_planilha in (select id from planilha where criacao > date(?1))", nativeQuery = true)
	void deleteLancamentosByCriacaoPlanilha(String data);

}
