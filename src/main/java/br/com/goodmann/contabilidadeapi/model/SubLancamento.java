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
public class SubLancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "idCategoria", nullable = true)
	private Categoria categoria;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "idlancamento", nullable = false)
	private Lancamento lancamento;

	@Column(name = "data", nullable = false)
	private Date data;

	@Column(name = "descricao", length = 50, nullable = false)
	private String descricao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

}
