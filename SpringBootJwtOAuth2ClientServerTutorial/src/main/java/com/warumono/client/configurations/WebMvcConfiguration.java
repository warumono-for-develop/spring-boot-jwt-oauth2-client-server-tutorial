package com.warumono.client.configurations;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@EnableWebMvc
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter
{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addRedirectViewController("/", "/swagger-ui.html");
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		converters.add(jackson2HttpMessageConverter());
//		converters.add(stringHttpMessageConverter());
		
//		super.configureMessageConverters(converters);
	}
	
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter()
	{
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jackson2HttpMessageConverter.setObjectMapper(jsonObjectMapper());
		jackson2HttpMessageConverter.setPrettyPrint(Boolean.TRUE);
		jackson2HttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
		
		return jackson2HttpMessageConverter;
	}

	@Bean
	public ObjectMapper jsonObjectMapper()
	{
		Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder.json();
		jackson2ObjectMapperBuilder.serializationInclusion(Include.USE_DEFAULTS);
		jackson2ObjectMapperBuilder.serializationInclusion(Include.NON_NULL);
		jackson2ObjectMapperBuilder.serializationInclusion(Include.NON_EMPTY);
		jackson2ObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// disabled
		jackson2ObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		jackson2ObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		jackson2ObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// enabled
		jackson2ObjectMapperBuilder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);
		jackson2ObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		jackson2ObjectMapperBuilder.featuresToEnable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		
		return jackson2ObjectMapperBuilder.build().registerModule(new Hibernate5Module());
	}
}
