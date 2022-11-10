package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

	@Query("from Lancamento l join l.planilha p where p.id=:idPlanilha")
	List<Lancamento> getLancamentos(Integer idPlanilha);

	List<Lancamento> findAllByPlanilha(Planilha planilha);

	List<Lancamento> findAllByContaAndPlanilha(Conta conta, Planilha planilha);

	@Query("from Lancamento l join l.planilha p where p.id=:idPlanilha and l.numeroBradesco is not null")
	List<Lancamento> getNumerosBradesco(Integer idPlanilha);

	@Query("from Lancamento l where l.planilha=:planilha and l.conta=:conta and l.descricao = 'Saldo Anterior'")
	Optional<Lancamento> getLancamentoSaldo(Planilha planilha, Conta conta);

}
