package org.springframework.samples.resources.groovy.support;

import groovy.text.Template;
import groovy.text.markup.BaseTemplate;
import groovy.text.markup.MarkupTemplateEngine;
import groovy.text.markup.TemplateConfiguration;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://gist.github.com/melix/240e961b6cce70d37592">Melix's Gist</a>
 */
public abstract class FragmentTemplate extends BaseTemplate {

	private final Map<String, Template> cachedFragments = new HashMap<>();

	private final MarkupTemplateEngine templateEngine;

	public FragmentTemplate(
			final MarkupTemplateEngine templateEngine,
			final Map model,
			final Map<String, String> modelTypes,
			final TemplateConfiguration configuration) {

		super(templateEngine, model, modelTypes, configuration);
		this.templateEngine = templateEngine;
	}

	public Object fragment(Map model, String tpl) throws Exception {

		if(!this.cachedFragments.containsKey(tpl)) {
			this.cachedFragments.put(tpl, this.templateEngine.createTemplate(new StringReader(tpl)));
		}
		this.cachedFragments.get(tpl).make(model).writeTo(this.getOut());
		return this;
	}
}

