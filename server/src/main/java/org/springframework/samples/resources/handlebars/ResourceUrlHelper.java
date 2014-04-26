package org.springframework.samples.resources.handlebars;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.io.IOException;

/**
 * A Handlebars Helper to help with rendering resource URLs in Mustache templates
 * through Spring's
 * {@link org.springframework.web.servlet.resource.ResourceUrlProvider}.
 *
 * <p>Registered in {@link org.springframework.samples.resources.WebConfig} with
 * the name "src" so that the following template syntax will trigger its use:
 * <pre class="code">
 * href="{{src "/css/main.css"}}"
 * </pre>
 */
public class ResourceUrlHelper implements Helper<String> {

	private final ResourceUrlProvider resourceUrlProvider;


	public ResourceUrlHelper(ResourceUrlProvider resourceUrlProvider) {
		this.resourceUrlProvider = resourceUrlProvider;
	}

	@Override
	public CharSequence apply(String context, Options options) throws IOException {
		return this.resourceUrlProvider.getForLookupPath(context);
	}

}
