package vn.edu.topica.edumall.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan("vn.edu.topica.edumall.security.sso")
@Profile({"sso_security"})
@Order(2000)
public class SSOTokenBaseConfig extends WebSecurityConfigurerAdapter {
}
