package br.com.goodmann.contabilidadeapi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Planilha {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@Column(name = "descricao", length = 50, nullable = false)
	private String descricao;

	@NotNull
	@Column(name = "mes", nullable = false)
	private Short mes;

	@NotNull
	@Column(name = "ano", nullable = false)
	private Short ano;

	@Transient
	List<Lancamento> lancamentos;
}
