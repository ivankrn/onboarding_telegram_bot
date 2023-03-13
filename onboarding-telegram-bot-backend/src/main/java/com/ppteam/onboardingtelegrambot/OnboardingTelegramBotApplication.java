package com.ppteam.onboardingtelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OnboardingTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnboardingTelegramBotApplication.class, args);
	}

}
