package br.com.goodmann.contabilidadeapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goodmann.contabilidadeapi.model.Label;
import br.com.goodmann.contabilidadeapi.repository.LabelRepository;

@Service
public class LabelService {

	@Autowired
	private LabelRepository labelRepository;

	public void saveAll(List<Label> labels) {
		labels.forEach(label -> {
			if (this.labelRepository.findByDescricao(label.getDescricao()).isEmpty()) {
				this.labelRepository.save(label);
			}
		});
	}
}
