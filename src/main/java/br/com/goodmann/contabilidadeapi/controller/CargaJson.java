package br.com.goodmann.contabilidadeapi.controller;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargaJson {
	
	private Integer idConta;
	private List<String> linhas;

}
