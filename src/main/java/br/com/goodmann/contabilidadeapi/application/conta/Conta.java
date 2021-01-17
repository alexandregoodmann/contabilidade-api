package br.com.goodmann.contabilidadeapi.application.conta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.goodmann.contabilidadeapi.application.banco.Banco;

@Document(collection = "conta")
public class Conta {

	@Id
	private String id;
	private String conta;
	private Banco banco;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

}
