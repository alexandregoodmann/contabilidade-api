package br.com.goodmann.contabilidadeapi;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import br.com.goodmann.contabilidadeapi.util.DateUtil;

@Configuration
public class UtilConfiguration {

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public DateUtil dateUtil() {
		return new DateUtil();
	}
}
