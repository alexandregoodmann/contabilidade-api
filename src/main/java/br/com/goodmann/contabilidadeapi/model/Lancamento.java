package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString
@EqualsAndHashCode
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@ManyToOne
	private Conta conta;

	@NotNull
	@ManyToOne
	private Planilha planilha;

	@NotNull
	@ManyToOne
	private Categoria categoria;

	@NotNull
	private Date data;

	@NotNull
	private String descricao;

	@NotNull
	private BigDecimal valor;

	private Boolean concluido;
}
