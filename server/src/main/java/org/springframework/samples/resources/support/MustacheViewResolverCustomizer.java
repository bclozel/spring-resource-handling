package org.springframework.samples.resources.support;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mustache.web.MustacheViewResolver;
import org.springframework.core.env.Environment;
import org.springframework.samples.resources.ApplicationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@Component
public class MustacheViewResolverCustomizer {

	private final MustacheViewResolver mustacheViewResolver;

	private final ResourceUrlProvider urlProvider;

	private final ApplicationProperties applicationProperties;

	private final Environment environment;

	@Autowired
	public MustacheViewResolverCustomizer(MustacheViewResolver mustacheViewResolver,
			ResourceUrlProvider urlProvider, ApplicationProperties applicationProperties,
			Environment environment) {
		this.mustacheViewResolver = mustacheViewResolver;
		this.urlProvider = urlProvider;
		this.applicationProperties = applicationProperties;
		this.environment = environment;
	}

	@PostConstruct
	public void customizeViewResolver() {
		boolean isDevMode = this.environment.acceptsProfiles("development");
		Map<String, Object> attributesMap = new HashMap<>();
		attributesMap.put("src", (Mustache.Lambda) (frag, out) -> {
			String url = frag.execute();
			String resourceUrl = urlProvider.getForLookupPath(frag.execute());
			if(StringUtils.hasLength(resourceUrl)) {
				out.write(resourceUrl);
			}
			else {
				out.write(url);
			}
		});
		attributesMap.put("isDevMode", new Mustache.InvertibleLambda() {

			@Override
			public void execute(Template.Fragment frag, Writer out) throws IOException {
				if (isDevMode) {
					out.write(frag.execute());
				}
			}

			@Override
			public void executeInverse(Template.Fragment frag, Writer out) throws IOException {
				if (!isDevMode) {
					out.write(frag.execute());
				}
			}

		});
		attributesMap.put("applicationVersion", this.applicationProperties.getVersion());
		this.mustacheViewResolver.setAttributesMap(attributesMap);
	}

}
