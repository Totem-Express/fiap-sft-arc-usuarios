package br.com.fiap.totem_express;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TotemExpressApplication implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
				.addMapping("/**")
				.allowedMethods("*")
				.allowedOrigins("*");
	}

	public static void main(String[] args) {
		SpringApplication.run(TotemExpressApplication.class, args);
	}

}
