package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.LancamentoLabel;

public interface LancamentoLabelRepository extends JpaRepository<LancamentoLabel, Integer> {

	public List<LancamentoLabel> findAllByLancamento(Lancamento lancamento);

}
