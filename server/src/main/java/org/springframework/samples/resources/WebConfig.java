package org.springframework.samples.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.samples.resources.handlebars.ProfileHelper;
import org.springframework.samples.resources.handlebars.ResourceUrlHelper;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	private GroovyMarkupViewResolver groovyMarkupViewResolver;

	@Autowired
	private ResourceUrlProvider urlProvider;

	@Value("${resources.projectroot:}")
	private String projectRoot;

	@Value("${app.version:}")
	private String appVersion;


	private String getProjectRootRequired() {
		Assert.state(this.projectRoot != null, "Please set \"resources.projectRoot\" in application.yml");
		return this.projectRoot;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/groovy").setViewName("hello");
		registry.addViewController("/app").setViewName("app");
		registry.addViewController("/less").setViewName("less");
		registry.addViewController("/jsp").setViewName("hellojsp");
	}

	@Bean
	public HandlebarsViewResolver handlebarsViewResolver() {
		HandlebarsViewResolver resolver = new HandlebarsViewResolver();
		resolver.setPrefix("classpath:/handlebars/");
		resolver.registerHelper("src", new ResourceUrlHelper(this.urlProvider));
		resolver.registerHelper(ProfileHelper.NAME, new ProfileHelper(this.env.getActiveProfiles()));
		resolver.setCache(!this.env.acceptsProfiles("development"));
		resolver.setFailOnMissingFile(false);
		return resolver;
	}

	@PostConstruct
	public void registerGroovyTemplateHelpers() {
		Map<String, Function> groovyTemplateHelpers = new HashMap<>();
		groovyTemplateHelpers.put("linkTo", s -> this.urlProvider.getForLookupPath((String) s));
		this.groovyMarkupViewResolver.setAttributesMap(groovyTemplateHelpers);
	}

	@Bean
	public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
		return new ResourceUrlEncodingFilter();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		boolean devMode = this.env.acceptsProfiles("development");

		String location = devMode ? "file:///" + getProjectRootRequired() + "/client/src/" : "classpath:static/";
		Integer cachePeriod = devMode ? 0 : null;
		boolean useResourceCache = !devMode;
		String version = devMode ? "dev" : this.appVersion;

		AppCacheManifestTransformer appCacheTransformer = new AppCacheManifestTransformer();
		VersionResourceResolver versionResolver = new VersionResourceResolver()
				.addFixedVersionStrategy(version, "/**/*.js", "/**/*.map")
				.addContentVersionStrategy("/**");

		registry.addResourceHandler("/**")
				.addResourceLocations(location)
				.setCachePeriod(cachePeriod)
				.resourceChain(useResourceCache)
					.addResolver(versionResolver)
					.addTransformer(appCacheTransformer);
	}

}
