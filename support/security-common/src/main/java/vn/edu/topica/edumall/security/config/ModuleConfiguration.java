package vn.edu.topica.edumall.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({ "vn.edu.topica.edumall.security.core" })
@Configuration
public class ModuleConfiguration {
}
