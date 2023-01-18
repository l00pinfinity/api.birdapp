package com.boitdroid.birdapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan(basePackages = {"com.boitdroid.birdapp"})
@EntityScan(basePackageClasses = {
		Application.class, Jsr310JpaConverters.class
})

@OpenAPIDefinition(
		info = @Info(
				title = "Birdapp",
				version = "1.0",
				description = "Made using Spring Boot for the BirdApp(TwitterClone). Continued Spring Learning"
		),
		security = @SecurityRequirement(name = "basicAuth"),
		servers = {
				@Server(url = "http://localhost:5000", description = "Local server"),
				@Server(url = "https://birdapp.com", description = "Live server"),
				@Server(url = "https://birdapp.netlify.app/login", description = "FrontEnd Server")
		}
)
public class Application {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
