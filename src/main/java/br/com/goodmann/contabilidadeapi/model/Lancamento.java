package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Document(collection = "lancamento")
public class Lancamento {

	@Id
	private String id;
	private Conta conta;
	private Categoria categoria;
	private LocalDate data;
	private String descricao;
	private BigDecimal valor;
	private Integer repetir;
}
