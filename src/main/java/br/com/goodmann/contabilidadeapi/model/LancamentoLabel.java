package br.com.goodmann.contabilidadeapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "lancamento_label")
public class LancamentoLabel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idLancamento", nullable = false)
	private Lancamento lancamento;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idLabel", nullable = false)
	private Label label;

}
