package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;

public interface AnaliseDTO {

	public Integer getMes();

	public String getDescricao();

	public Integer getQtd();

	public BigDecimal getValor();
}
