package br.com.goodmann.contabilidadeapi.dto;

import java.util.List;

import br.com.goodmann.contabilidadeapi.model.Planilha;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PlanilhasAnoDTO {
	private Short ano;
	private List<Planilha> planilhas;
}
