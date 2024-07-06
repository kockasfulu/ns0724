package com.primetoolrentals.tooltrek_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Main entry point for the Tool Trek API application.
 */
@SpringBootApplication
public class ToolTrekApiApplication {

	/**
	 * Configures the message source bean for internationalization support.
	 *
	 * @return ReloadableResourceBundleMessageSource configured with base name and encoding.
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource
				= new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/**
	 * Configures the validator bean to use the custom message source for validation messages.
	 *
	 * @return LocalValidatorFactoryBean configured with the message source.
	 */
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	/**
	 * Main method to start the Spring Boot application.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ToolTrekApiApplication.class, args);
	}

}
