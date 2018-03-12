package com.warumono.client.helpers;

import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Sets;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Component
public class SwaggerParameter
{
	@Autowired
	private TypeResolver typeResolver;
	
	public Docket docket(Boolean enabled, String groupName, String path)
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(enabled)
				.groupName(groupName)
				.apiInfo(apiInfo())
				.produces(produces())
				.select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.ant(path))
				.build()
				.pathMapping("/")
				.useDefaultResponseMessages(Boolean.TRUE)
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(new AlternateTypeRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)), typeResolver.resolve(WildcardType.class)))
				.ignoredParameterTypes
				(
//					Pageable.class
				);
	}
	
	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder()
				.title("CLIENT")
				.description("<strong>Client Server</strong> documents for <strong>OAuth2</strong>.")
				.contact(new Contact("warumono", "https://github.com/warumono-for-develop", "warumono.for.develop@gmail.com"))
				.version("1.0.0")
				.build();
	}
	
	private Set<String> produces()
	{
		return Sets.newHashSet
				(
//					MediaType.APPLICATION_XML_VALUE, 
					MediaType.APPLICATION_JSON_VALUE
				);
	}
}
