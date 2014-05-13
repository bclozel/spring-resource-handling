package org.springframework.samples.resources.groovy.support;

import groovy.lang.Writable;
import groovy.text.Template;
import groovy.text.markup.MarkupTemplateEngine;
import org.codehaus.groovy.runtime.MethodClosure;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class GroovyView extends AbstractTemplateView {

	private MarkupTemplateEngine engine;

	private Template template;

	private Map<String, MethodClosure> helpers = new HashMap<String, MethodClosure>();

	public MarkupTemplateEngine getEngine() {
		return engine;
	}

	public void setEngine(MarkupTemplateEngine engine) {
		this.engine = engine;
	}

	@Override
	protected void initApplicationContext(ApplicationContext context) {
		super.initApplicationContext(context);
		if(getEngine() == null) {
			setEngine(autodetectTemplateEngine());
		}
		this.helpers.putAll(autodetectHelpers());
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
	                                         HttpServletResponse response) throws Exception {
		model.putAll(helpers);
		Writable output = template.make(model);
		output.writeTo(response.getWriter());
	}

	@Override
	public boolean checkResource(Locale locale) throws Exception {
		try {
			this.template = getTemplate();
			return true;
		} catch (IOException rnfe) {
			if(logger.isDebugEnabled()) {
				logger.debug("No Velocity view found for URL: " + getUrl());
			}
			return false;
		} catch (Exception ex) {
			throw new NestedIOException(
					"Could not load Velocity template for URL [" + getUrl() + "]", ex);
		}
	}

	protected Template getTemplate() throws Exception {

		if(this.template == null) {
			Resource tpl = this.getApplicationContext().getResource(getUrl());
			this.template = engine.createTemplate(tpl.getFile());
		}

		return this.template;
	}

	protected MarkupTemplateEngine autodetectTemplateEngine() {

		try {
			GroovyTemplateConfig groovyTemplateConfig = BeanFactoryUtils.beanOfTypeIncludingAncestors(
					getApplicationContext(), GroovyTemplateConfig.class, true, false);
			return groovyTemplateConfig.getGroovyTemplateEngine();
		}
		catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException(
					"Must define a single GroovyTemplateConfig bean in this web application context " +
							"(may be inherited): GroovyTemplateConfigurer is the usual implementation. " +
							"This bean may be given any name.", ex);
		}

	}

	protected Map<String, MethodClosure> autodetectHelpers() {

			GroovyTemplateConfig groovyTemplateConfig = BeanFactoryUtils.beanOfTypeIncludingAncestors(
					getApplicationContext(), GroovyTemplateConfig.class, true, false);
			return groovyTemplateConfig.getGroovyTemplateHelpers();
	}

}
