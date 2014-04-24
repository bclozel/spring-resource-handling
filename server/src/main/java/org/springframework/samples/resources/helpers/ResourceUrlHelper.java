package org.springframework.samples.resources.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import org.springframework.web.servlet.resource.PublicResourceUrlProvider;

import java.io.IOException;

public class ResourceUrlHelper implements Helper<String> {

	private final PublicResourceUrlProvider translator;

	public ResourceUrlHelper(PublicResourceUrlProvider translator) {
		this.translator = translator;
	}

	@Override
	public CharSequence apply(String context, Options options) throws IOException {
		return translator.getForLookupPath(context);
	}
}
