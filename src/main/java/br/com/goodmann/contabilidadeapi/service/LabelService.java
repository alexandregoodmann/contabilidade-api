package br.com.goodmann.contabilidadeapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.goodmann.contabilidadeapi.model.Label;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.LancamentoLabel;
import br.com.goodmann.contabilidadeapi.repository.LabelRepository;
import br.com.goodmann.contabilidadeapi.repository.LancamentoLabelRepository;

@Service
public class LabelService {

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private LancamentoLabelRepository lancamentoLabelRepository;

	public void createLabels(Lancamento lancamento) {
		this.prepareListToSave(lancamento.getLabels()).forEach(label -> {
			LancamentoLabel lanlabel = new LancamentoLabel();
			lanlabel.setLabel(label);
			lanlabel.setLancamento(lancamento);
			this.lancamentoLabelRepository.save(lanlabel);
		});
	}

	public void updateLabels(Lancamento novo, Lancamento original) {

		List<LancamentoLabel> lancamentoLabels = this.lancamentoLabelRepository.findAllByLancamento(original);
		List<Label> salvar = this.prepareListToSave(novo.getLabels());
		List<Label> originais = lancamentoLabels.stream().map(obj -> obj.getLabel()).collect(Collectors.toList());

		if (!salvar.containsAll(originais) || !originais.containsAll(salvar)) {
			this.lancamentoLabelRepository.deleteAll(lancamentoLabels);
			salvar.forEach(label -> {
				LancamentoLabel lanlabel = new LancamentoLabel();
				lanlabel.setLabel(label);
				lanlabel.setLancamento(novo);
				this.lancamentoLabelRepository.save(lanlabel);
			});
		}
	}

	private List<Label> prepareListToSave(List<String> labels) {
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

	public void processLabel(List<Lancamento> lancamentos) {
		this.labelRepository.findAll().forEach(label -> {
			if (!StringUtils.isEmpty(label.getChaves())) {
				String[] chaves = label.getChaves().toLowerCase().split(";");
				for (String chave : chaves) {
					lancamentos.forEach(lancamento -> {
						if (!lancamento.getListLabels().contains(label)) {
							if (this.contain(lancamento.getDescricao(), chave)) {
								LancamentoLabel lancamentoLabel = new LancamentoLabel();
								lancamentoLabel.setLabel(label);
								lancamentoLabel.setLancamento(lancamento);
								this.lancamentoLabelRepository.save(lancamentoLabel);
							}
						}
					});
				}
			}
		});
	}

	private boolean contain(String text, String word) {
		String[] words = text.toLowerCase().split(" ");
		Set<String> mySet = new HashSet<String>(Arrays.asList(words));
		mySet.remove("");
		return ("".equals(words)) ? false : mySet.contains(word);
	}

}
