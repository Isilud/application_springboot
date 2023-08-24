package com.safetynet.SafetyNetAlert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        System.out.println("Starting !");
    }
}
