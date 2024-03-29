package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.goodmann.contabilidadeapi.enums.TipoLancamento;
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

	@Column(name = "data", nullable = false)
	private Date data;

	@Column(name = "descricao", length = 50, nullable = false)
	private String descricao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	private String fixo;

	private Boolean concluido;

	@Column(name = "numero_bradesco")
	private String numeroBradesco;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", length = 50)
	private TipoLancamento tipo;

	private String parcelas;

	@Transient
	private List<String> labels = new ArrayList<String>();

	@JsonIgnore
	@Transient
	private List<Label> listLabels = new ArrayList<Label>();

}
