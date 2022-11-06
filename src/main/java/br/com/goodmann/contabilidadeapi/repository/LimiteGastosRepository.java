package br.com.goodmann.contabilidadeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.LimiteGastos;
import br.com.goodmann.contabilidadeapi.model.Planilha;

public interface LimiteGastosRepository extends JpaRepository<LimiteGastos, Integer> {

	public List<LimiteGastos> findAllByPlanilha(Planilha planilha);
}
