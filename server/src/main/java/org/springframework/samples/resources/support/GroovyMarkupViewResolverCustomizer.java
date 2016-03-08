package org.springframework.samples.resources.support;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.resources.ApplicationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

@Component
public class GroovyMarkupViewResolverCustomizer {

	private final GroovyMarkupViewResolver groovyMarkupViewResolver;

	private final ResourceUrlProvider urlProvider;

	private final ApplicationProperties applicationProperties;

	@Autowired
	public GroovyMarkupViewResolverCustomizer(GroovyMarkupViewResolver groovyMarkupViewResolver,
			ResourceUrlProvider urlProvider, ApplicationProperties applicationProperties) {
		this.groovyMarkupViewResolver = groovyMarkupViewResolver;
		this.urlProvider = urlProvider;
		this.applicationProperties = applicationProperties;
	}

	@PostConstruct
	public void customizeViewResolver() {

		Map<String, Function> groovyTemplateHelpers = new HashMap<>();
		groovyTemplateHelpers.put("linkTo", s -> this.urlProvider.getForLookupPath((String) s));
		groovyTemplateHelpers.put("appVersion", s -> applicationProperties.getVersion());
		this.groovyMarkupViewResolver.setAttributesMap(groovyTemplateHelpers);
	}
}
