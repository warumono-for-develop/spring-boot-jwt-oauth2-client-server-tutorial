package com.warumono.client.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.warumono.client.helpers.SwaggerParameter;

import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration
{
	@Autowired
	private SwaggerParameter swaggerParameter;
	
	@Bean
	public Docket appAPI()
	{
		return swaggerParameter.docket(Boolean.TRUE, "client", "/**");
	}
}
