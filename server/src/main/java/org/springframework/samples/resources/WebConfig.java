package org.springframework.samples.resources;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.samples.resources.handlebars.ResourceUrlHelper;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.*;


@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Value("${resources.projectRoot:}")
	private String projectRoot;


	private boolean isDevProfileActive() {
		return this.env.acceptsProfiles("development");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Bean
	public HandlebarsViewResolver viewResolver(PublicResourceUrlProvider resourceUrlProvider) {
		HandlebarsViewResolver resolver = new HandlebarsViewResolver();
		resolver.setPrefix("classpath:/handlebars/");
		resolver.registerHelper("src", new ResourceUrlHelper(resourceUrlProvider));
		resolver.setCache(!isDevProfileActive());
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String location;
		Integer cachePeriod;

		if (isDevProfileActive()) {
			Assert.state(this.projectRoot != null, "Please set \"resources.projectRoot\" in application.yml");
			location = "file:///" + this.projectRoot + "/client/src/";
			cachePeriod = 0;
		}
		else {
			location = "classpath:static/";
			cachePeriod = null;
		}

		registry.addResourceHandler("/**/*.css")
				.addResourceLocations(location)
				.setCachePeriod(cachePeriod)
				.setResourceResolvers(new FingerprintResourceResolver(), new PathResourceResolver());

		registry.addResourceHandler("/**/*.js")
				.addResourceLocations(location)
				.setCachePeriod(cachePeriod)
				.setResourceResolvers(new PrefixResourceResolver("/prefix"), new PathResourceResolver());
	}

}
