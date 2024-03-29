package br.com.goodmann.contabilidadeapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Banco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String codigo;
	private String ispb;
	private String nomeReduzido;
	private String nomeExtenso;
	private Boolean carga;

}
