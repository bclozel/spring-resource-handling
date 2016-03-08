package org.springframework.samples.resources;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
public class ApplicationProperties {

	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
