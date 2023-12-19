package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "planilha2024")
public class PlanilhaAnual {

	@Id
	private Integer id;
	private Date data;
	private String conta;
	private String descricao;
	private String fixo;
	private String parcelas;
	private BigDecimal valor1;
	private BigDecimal valor2;
	private BigDecimal valor3;
	private BigDecimal valor4;
	private BigDecimal valor5;
	private BigDecimal valor6;
	private BigDecimal valor7;
	private BigDecimal valor8;
	private BigDecimal valor9;
	private BigDecimal valor10;
	private BigDecimal valor11;
	private BigDecimal valor12;

}
