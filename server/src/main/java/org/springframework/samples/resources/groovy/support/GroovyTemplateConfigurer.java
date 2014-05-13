package org.springframework.samples.resources.groovy.support;

import groovy.text.markup.BaseTemplate;
import groovy.text.markup.MarkupTemplateEngine;
import groovy.text.markup.TemplateConfiguration;
import org.codehaus.groovy.runtime.MethodClosure;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GroovyTemplateConfigurer implements GroovyTemplateConfig {

	private TemplateConfiguration config;

	private Class <? extends BaseTemplate> templateClass;

	private Map<String, MethodClosure> groovyTemplateHelpers = new HashMap<String, MethodClosure>();

	public GroovyTemplateConfigurer() {

		this.config = new TemplateConfiguration();
	}

	public void setConfig(TemplateConfiguration config) {
		this.config = config;
	}

	public void setTemplateClass(Class<? extends BaseTemplate> templateClass) {
		this.templateClass = templateClass;
	}

	@Override
	public MarkupTemplateEngine getGroovyTemplateEngine() {

		if(this.templateClass != null) {
			this.config.setBaseTemplateClass(this.templateClass);
		}
		return new MarkupTemplateEngine(this.config);
	}

	public void addTemplateHelper(String name, Function helper) {

		this.groovyTemplateHelpers.put(name, new MethodClosure(helper, "apply"));
	}

	@Override
	public Map<String, MethodClosure> getGroovyTemplateHelpers() {
		return groovyTemplateHelpers;
	}
}
