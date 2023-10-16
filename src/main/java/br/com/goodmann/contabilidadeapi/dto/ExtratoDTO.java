package br.com.goodmann.contabilidadeapi.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ExtratoDTO {
	private Integer idLancamento;
	private Date data;
	private String descricao;
	private Double valor;
	private String fixo;
	private Boolean concluido;
	private String numero_bradesco;
	private String tipo;
	private String parcelas;
	private Integer idConta;
	private String conta;
	private String tipoconta;
	private List<String> labels = new ArrayList<String>();
}
