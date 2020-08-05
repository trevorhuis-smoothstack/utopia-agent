package com.ss.training.utopia.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Trevor Huis in 't Veld
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableSwagger2
public class AgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
	}

}
