package br.com.goodmann.contabilidadeapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.goodmann.contabilidadeapi.model.Conta;
import br.com.goodmann.contabilidadeapi.model.Lancamento;
import br.com.goodmann.contabilidadeapi.model.MesesAbreviadosEnum;
import br.com.goodmann.contabilidadeapi.model.Planilha;
import br.com.goodmann.contabilidadeapi.repository.LancamentoRepository;
import br.com.goodmann.contabilidadeapi.repository.PlanilhaRepository;

@Service
public class ArquivoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private PlanilhaRepository planilhaRepository;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public void lerArquivoC6(Integer idConta, Integer idPlanilha, MultipartFile multipartFile) throws IOException {

		Planilha planilha = this.planilhaRepository.findById(idPlanilha).get();
		Conta conta = new Conta(idConta);

		InputStream inputStream = multipartFile.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

		reader.lines().forEach(line -> {

			line = line.replaceAll("\\.", "").replaceAll("\\,", ".");
			String dia = line.substring(0, 2);
			String mes = "";

			int i = MesesAbreviadosEnum.valueOf(line.substring(3, 6).toUpperCase()).ordinal() + 1;
			if (i < 10)
				mes = "0" + i;
			else
				mes = String.valueOf(i);

			Date data;

			try {
				data = this.sdf.parse(dia + "/" + mes + "/" + planilha.getAno());
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage());
			}

			String[] vet = line.split(" ");
			String valor = (vet[vet.length - 1]);

			String descricao = line.substring(7).replace(valor, "");

			Lancamento lancamento = new Lancamento();
			lancamento.setConta(conta);
			lancamento.setPlanilha(planilha);
			lancamento.setDescricao(descricao);
			lancamento.setValor(BigDecimal.valueOf(Double.valueOf(valor)*(-1)));
			lancamento.setData(data);

			this.lancamentoRepository.save(lancamento);

		});

		reader.close();
	}

}
