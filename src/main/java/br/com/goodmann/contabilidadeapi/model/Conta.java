package br.com.goodmann.contabilidadeapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String descricao;
	private String label;
	
	@ManyToOne
	private Banco banco;
	private String corLabel;
}
