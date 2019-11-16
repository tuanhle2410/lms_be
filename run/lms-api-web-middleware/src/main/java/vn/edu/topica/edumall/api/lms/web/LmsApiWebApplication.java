package vn.edu.topica.edumall.api.lms.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import vn.edu.topica.edumall.security.config.EnableSecurityModule;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LmsApiWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsApiWebApplication.class, args);
    }

}
