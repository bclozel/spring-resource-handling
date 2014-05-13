package org.springframework.samples.resources.groovy.support;


import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;


public class GroovyViewResolver extends AbstractTemplateViewResolver {

	private String DEFAULT_PREFIX = "/";

	private String DEFAULT_SUFFIX = ".tpl";

	public GroovyViewResolver() {
		this.setViewClass(requiredViewClass());
		this.setPrefix(DEFAULT_PREFIX);
		this.setSuffix(DEFAULT_SUFFIX);
	}

	@Override
	protected Class<?> requiredViewClass() {
		return GroovyView.class;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		GroovyView view = (GroovyView) super.buildView(viewName);
		return view;
	}
}
