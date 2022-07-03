package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.PlanilhaConta;

public interface PlanilhaContaRepository extends JpaRepository<PlanilhaConta, Integer> {

}
