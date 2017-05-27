package com.abassy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abassy.tables.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
	public CommandLineRunner loadData(LocalRepository repository) {
		return (args) -> {
			// save a couple of Users
			repository.save(new Local("Santa Rosa", "San Fernando"));
			repository.save(new Local("Calle Real", "Cadiz"));
			repository.save(new Local("La Granja", "San Fernando"));
			repository.save(new Local("Calle 4", "Jerez"));
			repository.save(new Local("Candelaria", "Cadiz"));

			// fetch all Users
			log.info("Local found with findAll():");
			log.info("-------------------------------");
			for (Local Local : repository.findAll()) {
				log.info(Local.toString());
			}
			log.info("");

			// fetch an individual User by ID
			//Local Local = repository.findOne(1);
			log.info("Local found with findOne(1L):");
			log.info("--------------------------------");
		//	log.info(Local.toString());
			log.info("");

			// fetch Local by last name
			log.info("Local found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (Local bauer : repository
					.findByDireccionStartsWithIgnoreCase("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}
}