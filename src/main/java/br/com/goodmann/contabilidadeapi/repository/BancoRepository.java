package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.Banco;

public interface BancoRepository extends JpaRepository<Banco, String> {

}
