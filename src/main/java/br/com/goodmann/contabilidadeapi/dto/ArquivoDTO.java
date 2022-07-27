package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class ArquivoDTO {
	private Date dataLancamento;
	private String item;
	private BigDecimal valor;
}
