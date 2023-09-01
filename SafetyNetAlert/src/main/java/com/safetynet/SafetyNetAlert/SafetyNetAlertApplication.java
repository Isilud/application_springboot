package com.safetynet.SafetyNetAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.safetynet.service.DataService;

@ComponentScan(basePackages = "com.safetynet.service")
@SpringBootApplication
public class SafetyNetAlertApplication implements CommandLineRunner {

	private final DataService datas;

	public SafetyNetAlertApplication(@Autowired @Qualifier("JsonDataService") DataService datas) {
		this.datas = datas;
	};

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
