package org.springframework.samples.resources.groovy.support;

import groovy.text.markup.MarkupTemplateEngine;
import org.codehaus.groovy.runtime.MethodClosure;

import java.util.Map;

public interface GroovyTemplateConfig {
	MarkupTemplateEngine getGroovyTemplateEngine();

	Map<String, MethodClosure> getGroovyTemplateHelpers();
}
