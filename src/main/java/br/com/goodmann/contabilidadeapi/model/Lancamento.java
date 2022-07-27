package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

	@ManyToOne
	private Conta conta;

	@ManyToOne
	private Planilha planilha;

	@ManyToOne
	private Categoria categoria;

	private Date data;
	private String descricao;
	private BigDecimal valor;
	private Boolean fixo;
	private Boolean concluido;
}
