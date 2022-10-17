package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;
import java.util.Date;

public interface AnaliseDTO {

	public Integer getIdLancamento();

	public Integer getAno();

	public String getPlanilha();

	public Integer getMes();

	public String getBanco();

	public String getTipo();

	public String getCategoria();

	public Boolean getAnalisar();

	public Date getData();

	public String getDescricao();

	public BigDecimal getValor();

	public Boolean getFixo();

	public Boolean getConcluido();
}
