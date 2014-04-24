package org.springframework.samples.resources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class NewPrefixResourceResolver extends AbstractResourceResolver {

	private static final Log logger = LogFactory.getLog(NewPrefixResourceResolver.class);

	private final String prefix;


	public NewPrefixResourceResolver(String prefix) {
		Assert.hasText(prefix, "prefix must not be null or empty");
		this.prefix = prefix.startsWith("/") ? prefix.substring(1) : prefix;
	}

	@Override
	protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath,
	                                           List<? extends Resource> locations, ResourceResolverChain chain) {

		if (requestPath.startsWith(this.prefix)) {
			requestPath = requestPath.substring(this.prefix.length());
		}

		return chain.resolveResource(request, requestPath, locations);
	}

	@Override
	protected String resolvePublicUrlPathInternal(String resourceUrlPath, List<? extends Resource> locations,
	                                              ResourceResolverChain chain) {

		String baseUrl = chain.resolvePublicUrlPath(resourceUrlPath, locations);
		return this.prefix + (baseUrl.startsWith("/") ? baseUrl : "/" + baseUrl);
	}

}
