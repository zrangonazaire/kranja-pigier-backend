package com.pigierbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@RequiredArgsConstructor
@EnableScheduling
// @OpenAPIDefinition(info = @Info(title = "Pigierbackend", version = "1.0", description = "Application de Gestion de PIGIER"))
// @SecurityScheme(name = "krinjaAuth", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "JWT")
public class PigierbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigierbackendApplication.class, args);
	}


}