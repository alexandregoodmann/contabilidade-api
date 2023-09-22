package br.com.goodmann.contabilidadeapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goodmann.contabilidadeapi.model.Label;
import br.com.goodmann.contabilidadeapi.repository.LabelRepository;

@Service
public class LabelService {

	@Autowired
	private LabelRepository labelRepository;

	/**
	 * Verify if label exists in database. Otherwise insert a new one.
	 * @param labels
	 * @return
	 */
	public List<Label> createAll(List<String> labels) {
		List<Label> list = new ArrayList<Label>();
		labels.forEach(label -> {
			this.labelRepository.findByDescricao(label).ifPresentOrElse(obj -> {
				list.add(obj);
			}, () -> {
				Label obj = new Label();
				obj.setDescricao(label);
				list.add(this.labelRepository.save(obj));
			});
		});
		return list;
	}
}
