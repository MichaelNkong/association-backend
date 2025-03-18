package com.culture.association_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

@EnableJpaRepositories(basePackages = "com.culture.association_backend.repository")
@EntityScan(basePackages = "com.culture.association_backend.model")
@SpringBootApplication
public class AssociationBackendApplication {

	// Set System Properties

	public static void main(String[] args) {
		SpringApplication.run(AssociationBackendApplication.class, args);
	}

}
