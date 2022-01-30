package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Conta conta;
	private Categoria categoria;
	private LocalDate data;
	private String descricao;
	private BigDecimal valor;
	private Integer repetir;
}
