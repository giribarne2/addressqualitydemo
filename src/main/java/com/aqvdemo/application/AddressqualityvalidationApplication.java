package com.aqvdemo.application;
import com.aqvdemo.application.configuration.JpaConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"com.aqvdemo.application"})
public class AddressqualityvalidationApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AddressqualityvalidationApplication.class, args);
	}
}