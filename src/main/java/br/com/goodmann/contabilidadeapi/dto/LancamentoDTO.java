package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoDTO {
	private Integer id;
	private String categoria;
	private Date data;
	private String descricao;
	private BigDecimal valor;
	private Boolean concluido;
}
