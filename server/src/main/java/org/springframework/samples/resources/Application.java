package org.springframework.samples.resources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class})
@ComponentScan
public class Application extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(new Object[] {Application.class}, args);
	}

}