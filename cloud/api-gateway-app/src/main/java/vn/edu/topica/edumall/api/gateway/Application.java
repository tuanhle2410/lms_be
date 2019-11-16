package vn.edu.topica.edumall.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.edumall.api.gateway.filter.PostLoggingFilter;
import vn.edu.topica.edumall.api.gateway.filter.PreLoggingFilter;
import vn.edu.topica.edumall.api.gateway.filter.RouteLoggingFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
//@EnableApiCommonErrorHandler
@Slf4j
public class Application {

	public static void main(String[] args) {
		log.info("Start api gateway");
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		config.addExposedHeader("X-Total-Count");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public PreLoggingFilter preLoggingFilter() {
		return new PreLoggingFilter();
	}

	@Bean
	public RouteLoggingFilter routeLoggingFilter() {
		return new RouteLoggingFilter();
	}

	@Bean
	public PostLoggingFilter postLoggingFilter() {
		return new PostLoggingFilter();
	}
}