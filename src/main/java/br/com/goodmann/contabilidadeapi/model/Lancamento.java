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

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idConta", nullable = false)
	private Conta conta;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idPlanilha", nullable = false)
	private Planilha planilha;

	@ManyToOne(optional = true)
	@JoinColumn(name = "idCategoria", nullable = true)
	private Categoria categoria;

	@Column(name = "data", nullable = false)
	private Date data;

	@Column(name = "descricao", length = 50, nullable = false)
	private String descricao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	private Boolean fixo;

	private Boolean concluido;

	@Column(name = "numero_bradesco")
	private String numeroBradesco;

}
