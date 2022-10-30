package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;

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
public class LimiteGastos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idPlanilha", nullable = false)
	private Planilha planilha;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idCategoria", nullable = false)
	private Categoria categoria;

	private BigDecimal limite;

}
