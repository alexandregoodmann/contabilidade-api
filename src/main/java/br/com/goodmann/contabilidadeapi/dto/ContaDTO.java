package br.com.goodmann.contabilidadeapi.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.goodmann.contabilidadeapi.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO extends Conta {

	private BigDecimal total = BigDecimal.ZERO;

	private List<LancamentoDTO> lancamentos = new ArrayList<LancamentoDTO>();

}
