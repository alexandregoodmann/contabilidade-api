package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "idConta", nullable = false)
	private Conta conta;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "idPlanilha", nullable = false)
	private Planilha planilha;

	@ManyToOne(optional = true)
	@JoinColumn(name = "idCategoria", nullable = true)
	private Categoria categoria;

	@NotNull
	@Column(name = "data", nullable = false)
	private Date data;

	@NotNull
	@Column(name = "descricao", length = 50, nullable = false)
	private String descricao;

	@NotNull
	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	private Boolean concluido;
}
