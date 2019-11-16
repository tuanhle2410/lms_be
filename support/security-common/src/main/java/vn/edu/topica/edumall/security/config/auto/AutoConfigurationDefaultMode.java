package vn.edu.topica.edumall.security.config.auto;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import vn.edu.topica.edumall.security.core.filter.TokenProvider;

@Configuration
public class AutoConfigurationDefaultMode {

	@Autowired
	DataSource dataSource;

	@Bean
	@ConditionalOnMissingBean(TokenStore.class)
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
	@Autowired
	UserDetailsService lmsUserDetail;

//	@Bean
//	@ConditionalOnMissingBean(UserDetailsService.class)
//	public UserDetailsService buildUserDetailService(@Autowired EntityManagerFactory emf) {
//		return new DefaultUserDetailService(emf);
//	}

	@Bean
	@Lazy
	@ConditionalOnMissingBean(TokenProvider.class)
	public TokenProvider defaultTokenProvider(
			@Autowired DefaultTokenServices defaultTokenServices, @Autowired TokenStore tokenStore) {
		return new DefaultTokenProvider(lmsUserDetail, defaultTokenServices, tokenStore);
	}

	@Bean
	@ConditionalOnMissingBean(DefaultTokenServices.class)
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
