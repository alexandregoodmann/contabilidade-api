package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;

public interface AnaliseSaldoDTO {

	public Integer getAno();

	public String getMes();

	public BigDecimal getEntrada();

	public BigDecimal getSaida();

	public BigDecimal getSaldo();
}
