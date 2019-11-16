package vn.edu.topica.edumall.api.lms.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import vn.edu.topica.edumall.security.config.EnableSecurityModule;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSecurityModule
public class LmsApiMobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsApiMobileApplication.class, args);
	}

}
