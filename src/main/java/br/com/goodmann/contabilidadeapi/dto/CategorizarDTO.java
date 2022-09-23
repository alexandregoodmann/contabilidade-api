package br.com.goodmann.contabilidadeapi.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorizarDTO extends ListDTO<Integer> {

	@NotNull
	private Integer idCategoria;
}
