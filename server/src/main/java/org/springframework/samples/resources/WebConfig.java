package org.springframework.samples.resources;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.samples.resources.helpers.ResourceUrlHelper;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Value("${resources.projectRoot:}")
	private String projectRoot;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Bean
	public HandlebarsViewResolver viewResolver(PublicResourceUrlProvider translator) {
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
		viewResolver.setPrefix("classpath:/handlebars/");
		viewResolver.registerHelper("src", new ResourceUrlHelper(translator));
		viewResolver.setCache(false);
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// in production, serve resources from the webjar created by the client module
		if(Arrays.asList(this.env.getActiveProfiles()).contains("production")) {

			registry.addResourceHandler("/**/*.css")
					.addResourceLocations("classpath:static/")
					.setResourceResolvers(assetsResourceResolvers());

			registry.addResourceHandler("/**/*.js")
					.addResourceLocations("classpath:static/")
					.setResourceResolvers(jsResourceResolvers());

			registry.addResourceHandler("/**")
					.addResourceLocations("classpath:static/")
					.setResourceResolvers(catchAllResourceResolvers());

		} else {
			// at dev time, serve resources from the client module directly
			if(projectRoot.isEmpty()) {
				throw new IllegalStateException("Please set the resources.projectRoot configuration key in application.yml");
			}
			registry.addResourceHandler("/**/*.css")
					.addResourceLocations("file:///" + this.projectRoot + "/client/src/")
					.setResourceResolvers(assetsResourceResolvers());

			registry.addResourceHandler("/**/*.js")
					.addResourceLocations("file:///" + this.projectRoot + "/client/src/")
					.setCachePeriod(0)
					.setResourceResolvers(jsResourceResolvers());

			registry.addResourceHandler("/**")
					.addResourceLocations("file:///" + this.projectRoot + "/client/src/")
					.setCachePeriod(0)
					.setResourceResolvers(catchAllResourceResolvers());
		}
	}

	public List<ResourceResolver> assetsResourceResolvers() {

		List<ResourceResolver> resolvers = new ArrayList<ResourceResolver>();
		resolvers.add(new FingerprintResourceResolver());
		resolvers.add(new PathResourceResolver());
		return resolvers;
	}

	public List<ResourceResolver> jsResourceResolvers() {

		List<ResourceResolver> resolvers = new ArrayList<ResourceResolver>();
		resolvers.add(new NewPrefixResourceResolver("/prefix"));
		resolvers.add(new PathResourceResolver());
		return resolvers;
	}

	public List<ResourceResolver> catchAllResourceResolvers() {

		List<ResourceResolver> resolvers = new ArrayList<ResourceResolver>();
		resolvers.add(new PathResourceResolver());
		return resolvers;
	}
}
