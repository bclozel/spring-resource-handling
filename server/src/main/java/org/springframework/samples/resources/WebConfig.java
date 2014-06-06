package org.springframework.samples.resources;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.samples.resources.groovy.GroovyHelperViewResolver;
import org.springframework.samples.resources.handlebars.ResourceUrlHelper;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.*;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;


@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Value("${resources.projectRoot:}")
	private String projectRoot;


	private String getProjectRootRequired() {
		Assert.state(this.projectRoot != null, "Please set \"resources.projectRoot\" in application.yml");
		return this.projectRoot;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/groovy").setViewName("hello");
	}

	@Bean
	public HandlebarsViewResolver handlebarsViewResolver(ResourceUrlProvider urlProvider) {
		HandlebarsViewResolver resolver = new HandlebarsViewResolver();
		resolver.setPrefix("classpath:/handlebars/");
		resolver.registerHelper("src", new ResourceUrlHelper(urlProvider));
		resolver.setCache(!this.env.acceptsProfiles("development"));
		return resolver;
	}

	@Bean
	public GroovyHelperViewResolver groovyViewResolver(ResourceUrlProvider urlProvider) {
		GroovyHelperViewResolver resolver = new GroovyHelperViewResolver();
		resolver.setSuffix(".tpl");
		resolver.setOrder(1);
		resolver.addTemplateHelper("css",
				s -> String.format("<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\">",
					urlProvider.getForLookupPath((String)s)));
		return resolver;
	}

	@Bean
	public GroovyMarkupConfigurer groovyTemplateConfigurer() {

		GroovyMarkupConfigurer configurer = new GroovyMarkupConfigurer();
		configurer.setResourceLoaderPath("classpath:groovy/");
		configurer.setAutoIndent(true);
		configurer.setAutoNewLine(true);
		return configurer;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		CachingResourceResolver cachingResolver = new CachingResourceResolver(resourceResolverCache());
		FingerprintResourceResolver fingerprintResolver = new FingerprintResourceResolver();
		PrefixResourceResolver prefixResolver = new PrefixResourceResolver("/prefix");
		PathResourceResolver pathResolver = new PathResourceResolver();

		CachingResourceTransformer cachingTransformer = new CachingResourceTransformer(resourceResolverCache());
		CssLinkResourceTransformer cssLinkTransformer = new CssLinkResourceTransformer();

		if (this.env.acceptsProfiles("development")) {

			String location = "file:///" + getProjectRootRequired() + "/client/src/";
			int cachePeriod = 0;

			registry.addResourceHandler("/**/*.css", "/**/*.png")
					.addResourceLocations(location)
					.setCachePeriod(cachePeriod)
					.setResourceResolvers(fingerprintResolver, pathResolver)
					.setResourceTransformers(cssLinkTransformer);

			registry.addResourceHandler("/**/*.js")
					.addResourceLocations(location)
					.setCachePeriod(cachePeriod)
					.setResourceResolvers(prefixResolver, pathResolver);
		}
		else {

			String location = "classpath:static/";

			registry.addResourceHandler("/**/*.css", "/**/*.png")
					.addResourceLocations(location)
					.setResourceResolvers(cachingResolver, fingerprintResolver, pathResolver)
					.setResourceTransformers(cachingTransformer, cssLinkTransformer);

			registry.addResourceHandler("/**/*.js")
					.addResourceLocations(location)
					.setResourceResolvers(cachingResolver, prefixResolver, pathResolver);
		}
	}

	@Bean
	public Cache resourceResolverCache() {
		return new ConcurrentMapCache("resource-resolver-cache", false);
	}

}
