package com.example.HackathonEvaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HackathonEvaluatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackathonEvaluatorApplication.class, args);
	}

}
