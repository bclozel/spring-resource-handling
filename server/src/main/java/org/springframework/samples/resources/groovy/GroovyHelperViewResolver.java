package org.springframework.samples.resources.groovy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.codehaus.groovy.runtime.MethodClosure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

/**
 * @author Brian Clozel
 */
public class GroovyHelperViewResolver extends GroovyMarkupViewResolver implements InitializingBean {

	private Map<String, MethodClosure> groovyTemplateHelpers = new HashMap<>();

	public void addTemplateHelper(String name, Function helper) {

		this.groovyTemplateHelpers.put(name, new MethodClosure(helper, "apply"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setAttributesMap(this.groovyTemplateHelpers);
	}
}
