package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	private BigDecimal valor;

	@JsonIgnore
	private String valores;

	@Transient
	private List<BigDecimal> listValores;
}
