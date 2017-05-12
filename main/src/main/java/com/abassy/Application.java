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
	public CommandLineRunner loadData(UserRepository repository) {
		return (args) -> {
			// save a couple of Users
			repository.save(new User("Pepe", "Bauer"));
			repository.save(new User("Chloe", "O'Brian"));
			repository.save(new User("Kim", "Bauer"));
			repository.save(new User("David", "Palmer"));
			repository.save(new User("Michelle", "Dessler"));

			// fetch all Users
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User User : repository.findAll()) {
				log.info(User.toString());
			}
			log.info("");

			// fetch an individual User by ID
			User User = repository.findOne(1L);
			log.info("User found with findOne(1L):");
			log.info("--------------------------------");
			log.info(User.toString());
			log.info("");

			// fetch Users by last name
			log.info("User found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (User bauer : repository
					.findByLastNameStartsWithIgnoreCase("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}
}