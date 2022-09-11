package br.com.goodmann.contabilidadeapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
	@Column(name = "banco", length = 50, nullable = false)
	private String banco;

	@NotNull
	@Column(name = "descricao", length = 50, nullable = false)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "carga", nullable = true)
	private CargaEnum carga;

}
