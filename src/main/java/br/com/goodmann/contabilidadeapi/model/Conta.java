package br.com.goodmann.contabilidadeapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.goodmann.contabilidadeapi.enums.TipoConta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "idBanco", nullable = false)
	private Banco banco;

	@NotNull
	@Column(name = "descricao", length = 50, nullable = false)
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", length = 50, nullable = false)
	private TipoConta tipo;

}
