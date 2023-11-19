package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@Column(length = 50, nullable = false)
	private String descricao;

	private String chaves;

	private Boolean analisar;

	@Column(name = "valor_limite", nullable = false)
	private BigDecimal valorLimite;

	private String cor;
}
