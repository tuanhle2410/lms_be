package vn.edu.topica.edumall.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import lombok.extern.slf4j.Slf4j;

/**
 * This class to initiate configuration server
 * 
 * @author trungnt9
 *
 */
@EnableConfigServer
@SpringBootApplication
@Slf4j
public class ConfigServerApplication {
	public static void main(String[] args) {
		log.info("Config server starting");
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
