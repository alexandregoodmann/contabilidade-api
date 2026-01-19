package br.com.goodmann.contabilidadeapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.goodmann.contabilidadeapi.model.Amortizacao;
import br.com.goodmann.contabilidadeapi.repository.AmortizacaoRepository;

@SpringBootTest
public class AmortizacaoTest {

	@Autowired
	private AmortizacaoRepository amortizacaoRepository;

	// @Test
	public void amortizacaoTest() throws FileNotFoundException {

		File file = new File("/home/alexandre/projetos/contabilidade-api/arquivos/planilha.csv");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		br.lines().forEach(linha -> {
			Amortizacao amo = new Amortizacao();
			try {
				amo.setData(DateUtils.parseDate(linha.substring(0, 10), "dd/MM/yyyy"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			amo.setValor(new BigDecimal(Double.valueOf(linha.substring(11, 17).replace(",", "."))));
			amo.setPago(false);

			this.amortizacaoRepository.save(amo);
		});
	}

}
