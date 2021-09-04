package br.com.goodmann.contabilidadeapi.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lancamento")
public class Lancamento {

	@Id

	private String id;

	private Conta conta;

	private Categoria categoria;

	private Date data;

	private Date efetuado;

	private String descricao;

	private BigDecimal valor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getEfetuado() {
		return efetuado;
	}

	public void setEfetuado(Date efetuado) {
		this.efetuado = efetuado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", conta=" + conta + ", categoria=" + categoria + ", data=" + data
				+ ", efetuado=" + efetuado + ", descricao=" + descricao + ", valor=" + valor + "]";
	}

}
