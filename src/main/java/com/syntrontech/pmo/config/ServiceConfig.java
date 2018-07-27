package com.syntrontech.pmo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.syntrontech.pmo.properties.ServiceProperties;
import com.syntrontech.pmo.properties.SolrProperties;
import com.syntrontech.pmo.provider.CORSFilter;
import com.syntrontech.pmo.solr.Solr;
import com.syntrontech.pmo.solr.SolrBuilder;


@Configuration
public class ServiceConfig {
	
	@Autowired 
	protected ServiceProperties serviceProperties;
	
	@Autowired
	protected SolrProperties solrProperties;
	
	@Bean
	public EmbeddedServletContainerCustomizer getEmbeddedServletContainerCustomizer(){
		return new EmbeddedServletContainerCustomizer(){

			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.setPort(serviceProperties.getPort());
			}
			
		};
	}
	
	@Bean
	public FilterRegistrationBean someFilterRegistration(){
		
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new CORSFilter());
		registration.addUrlPatterns("/*");
		return registration;
	}
	
	@Bean
	public Solr getSolr(){
		return SolrBuilder.newSolr(solrProperties.getUrl());
	}
}
