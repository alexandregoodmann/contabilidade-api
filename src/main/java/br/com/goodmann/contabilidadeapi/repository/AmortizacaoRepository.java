package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.Amortizacao;

public interface AmortizacaoRepository extends JpaRepository<Amortizacao, Integer> {
}
