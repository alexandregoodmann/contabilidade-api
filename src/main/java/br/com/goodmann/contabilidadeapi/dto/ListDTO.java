package br.com.goodmann.contabilidadeapi.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDTO<T> {

	@NotEmpty
	private List<T> list;
}
