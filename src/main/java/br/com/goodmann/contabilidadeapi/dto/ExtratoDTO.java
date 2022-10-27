package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.goodmann.contabilidadeapi.model.CargaEnum;
import br.com.goodmann.contabilidadeapi.model.TipoContaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDTO {
	private Integer id;
	private String banco;
	private String descricao;
	private CargaEnum carga;
	private TipoContaEnum tipo;
	private Boolean marcado;
	private BigDecimal saldoPrevisto = BigDecimal.ZERO;
	private BigDecimal saldoEfetivado = BigDecimal.ZERO;
	private List<LancamentoDTO> lancamentos = new ArrayList<LancamentoDTO>();

}
