package br.com.goodmann.contabilidadeapi.json;

import java.util.List;

public class CargaJson {

	private String idConta;
	private List<String> linhas;

	public List<String> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<String> linhas) {
		this.linhas = linhas;
	}

	public String getIdConta() {
		return idConta;
	}

	public void setIdConta(String idConta) {
		this.idConta = idConta;
	}

}
