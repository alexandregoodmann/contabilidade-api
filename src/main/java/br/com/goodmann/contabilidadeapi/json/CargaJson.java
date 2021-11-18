package br.com.goodmann.contabilidadeapi.json;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
public class CargaJson {

	@NonNull
	private String idConta;
	private List<String> linhas;

}
