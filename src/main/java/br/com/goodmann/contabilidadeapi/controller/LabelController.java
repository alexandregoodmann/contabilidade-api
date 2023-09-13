package br.com.goodmann.contabilidadeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goodmann.contabilidadeapi.model.Label;

@RestController
@RequestMapping(value = "v1/labels")
public class LabelController extends BaseController<Label, Integer> {

}
