package com.springbank.bankacc.query.api;

import com.springbank.bankacc.core.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class})
public class BankAccQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccQueryApplication.class, args);
	}

}
