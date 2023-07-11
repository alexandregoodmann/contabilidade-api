package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.SubLancamento;

public interface SubLancamentoRepository extends JpaRepository<SubLancamento, Integer> {

}
