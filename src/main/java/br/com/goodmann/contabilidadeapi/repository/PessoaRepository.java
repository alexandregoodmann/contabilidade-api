package br.com.goodmann.contabilidadeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, String> {

}
