package br.com.goodmann.contabilidadeapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "categoria")
public class Categoria {

	@Id
	private String id;
	private String descricao;
}
