package com.upregotdev.subscription_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;      // <--- NUEVO
import org.springframework.scheduling.annotation.EnableScheduling; // <--- NUEVO

@SpringBootApplication
@EnableScheduling // Permite @Scheduled
@EnableAsync      // Permite @Async
public class SubscriptionManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionManagerApplication.class, args);
	}
}