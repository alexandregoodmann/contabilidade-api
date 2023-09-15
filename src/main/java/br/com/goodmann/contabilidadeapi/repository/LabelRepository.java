package br.com.goodmann.contabilidadeapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.goodmann.contabilidadeapi.model.Label;

public interface LabelRepository extends JpaRepository<Label, Integer> {

	public Optional<Label> findByDescricao(String descricao);

}
