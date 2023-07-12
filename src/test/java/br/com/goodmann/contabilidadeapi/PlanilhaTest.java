package br.com.goodmann.contabilidadeapi;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;
import br.com.goodmann.contabilidadeapi.service.PlanilhaService;

@SpringBootTest
public class PlanilhaTest {

	@Autowired
	private PlanilhaRepository planilhaRepository;

	@Autowired
	private PlanilhaService planilhaService;

	// @Test
	public void getAnaliseAno() {
		this.planilhaRepository.getAnaliseAno(2022).forEach(e -> {
			System.out.println(e);
		});
		assertTrue(this.planilhaRepository.getAnaliseAno(2022).size() > 0);
	}

	// @Test
	public void test2() {
		this.planilhaRepository.getAnaliseAnoMes(2022, 10).forEach(e -> {
			System.out.println(e);
		});
		assertTrue(this.planilhaRepository.getAnaliseAnoMes(2022, 10).size() > 0);

	}

	// @Test
	public void duplicar() throws ParseException {
		this.planilhaService.duplicarPlanilha(132);
		this.planilhaRepository.findAll().forEach(e -> {
			System.out.println(e.toString());
		});
	}

	// @Test
	public void getPlanilhasFuturas() {
		List<Planilha> planilhas = this.planilhaRepository.getPlanilhasFuturas((short) 2022);
		planilhas.forEach(p -> {
			LocalDate criacao = LocalDate.of(p.getAno(), p.getMes(), 1);
			p.setCriacao(criacao);
		});
		LocalDate hoje = LocalDate.of(2022, 11, 1);
		List<Planilha> filtrada = planilhas.stream().filter(o -> o.getCriacao().isAfter(hoje))
				.collect(Collectors.toList());
		filtrada.forEach(System.out::println);
	}

	// @Test
	public void processarPlanilhasTest() throws ParseException {
		this.planilhaService.processarPlanilhas();
	}

	@Test
	public void getExtratoTest() {
		Planilha p = this.planilhaRepository.findByAnoAndMes(Short.valueOf("2023"), Short.valueOf("7"));
		this.planilhaService.getExtrato(p.getId()).forEach(e -> {
			e.getLancamentos().forEach(l -> {
				if (l.getSublancamentos().size() > 0) {
					System.out.println("++++++++++++++++++++++++++++++++++++++++++" + l.getSublancamentos().size());
				} else {
					System.out.println(">>>>>>>>>>>>>>> vazio");
				}
			});
		});
	}

}
