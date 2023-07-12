package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.goodmann.contabilidadeapi.model.Sublancamento;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LancamentoDTO {
	private Integer id;
	private String categoria;
	private Date data;
	private String descricao;
	private BigDecimal valor;
	private Boolean concluido;
	private Boolean fixo;
	private List<Sublancamento> sublancamentos = new ArrayList<Sublancamento>();
}
