package vn.edu.topica.edumall.locale.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * To define multiple language for the validated bean
 * 
 * @author trungnt9
 *
 */
@Configuration
public class LocalValidatorConfig {
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(validateMessageSource());
		return bean;
	}

	public MessageSource validateMessageSource() {
		ResourceBundleMessageSource bundle = new ResourceBundleMessageSource();
		bundle.setBasenames("validator_messages");
		return bundle;
	}
}
