package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LancamentoDTO {
	private Integer id;
	private Date data;
	private String descricao;
	private BigDecimal valor;
	private Boolean concluido;
	private Boolean fixo;
	private List<String> labels;
}
