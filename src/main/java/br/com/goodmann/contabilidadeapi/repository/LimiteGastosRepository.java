package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.LimiteGastos;

public interface LimiteGastosRepository extends JpaRepository<LimiteGastos, Integer> {

}
