package com.conf;

import javax.ws.rs.core.Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.rs.JaxRsApiApplication;
import com.rs.PasserelleRest;

/**
 * Classe qui charge la configuration pour la passerelle REST
 * 
 * @author Dhersin Jérôme Knapik Christopher
 *
 */
@javax.ws.rs.ApplicationPath("resources")
public class App_Config extends Application {
	@Bean( destroyMethod = "shutdown" )
	public SpringBus cxf() {
		return new SpringBus();
	}
	
	@Bean @DependsOn( "cxf" )
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
		
		List<Object> serviceBeans = new ArrayList<Object>();
		serviceBeans.add(new PasserelleRest());
		
		factory.setServiceBeans(serviceBeans);
		factory.setAddress( "/" + factory.getAddress() );
		factory.setProviders( Arrays.< Object >asList( jsonProvider() ) );
		return factory.create();
	}
	
	@Bean 
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
		
	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}
}