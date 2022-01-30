package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

}
