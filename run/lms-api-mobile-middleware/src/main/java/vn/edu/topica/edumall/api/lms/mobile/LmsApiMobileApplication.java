package vn.edu.topica.edumall.api.lms.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LmsApiMobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsApiMobileApplication.class, args);
	}

}
