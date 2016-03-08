package org.springframework.samples.resources;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/groovy").setViewName("hello");
		registry.addViewController("/app").setViewName("app");
		registry.addViewController("/less").setViewName("less");
		registry.addViewController("/jsp").setViewName("hellojsp");
		registry.addViewController("/velocity").setViewName("hellovm");
	}

}
