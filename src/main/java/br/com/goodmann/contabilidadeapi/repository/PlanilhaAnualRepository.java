package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.PlanilhaAnual;

public interface PlanilhaAnualRepository extends JpaRepository<PlanilhaAnual, Integer> {
	
}
