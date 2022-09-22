package br.com.goodmann.contabilidadeapi.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteAllDTO {
	private List<Integer> ids;
}
