package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.goodmann.contabilidadeapi.enums.TipoConta;
import br.com.goodmann.contabilidadeapi.enums.TipoLancamento;
import lombok.Data;

@Data
@Entity
@Table(name = "planilha_anual")
public class PlanilhaAnual {

	@Id
	private Integer id;

	@Column(name = "id_lancamento")
	private Integer idLancamento;
	private String titulo;
	private Date data;
	private String conta;
	private String descricao;
	private String fixo;
	private String parcelas;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta", length = 50, nullable = false)
	private TipoConta tipoConta;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_lancamento", length = 50)
	private TipoLancamento tipoLancamento;

	private BigDecimal valor;

	@JsonIgnore
	private String valores;

	@Transient
	private List<BigDecimal> listValores;
}
