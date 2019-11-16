package vn.edu.topica.edumall.api.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({ "vn.edu.topica.edumall.api.core.config" })
@Configuration
@EnableSwagger2
public class ModuleConfiguration {

}
