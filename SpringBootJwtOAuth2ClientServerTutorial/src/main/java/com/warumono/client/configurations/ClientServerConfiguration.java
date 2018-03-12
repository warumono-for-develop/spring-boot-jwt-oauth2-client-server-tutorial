package com.warumono.client.configurations;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import lombok.extern.slf4j.Slf4j;

@EnableOAuth2Client
@Configuration
@Slf4j
public class ClientServerConfiguration
{
	@Value("${security.oauth2.client.client-id}")
	private String CLIENT_ID;
	
	@Value("${security.oauth2.client.client-secret}")
	private String CLIENT_SECRET;

    // http://localhost:8081/oauth/authorize
	@Value("${security.oauth2.client.user-authorization-uri}")
	private String USER_AUTHORIZATION_URI;
	
	// http://localhost:8081/oauth/token
	@Value("${security.oauth2.client.access-token-uri}")
	private String ACCESS_TOKEN_URI;
	
	@Value("${security.oauth2.resource.id}")
	private String RESOURCE_ID;

	/*
	
	//// 본 코드를 사용할 경우, 
	//// 접근자에게는 스프링 시큐리티에서 제공하는 로그인 페이지를 보여주고
	//// 로그인 후, 정보 동의 화면(옵션으로써 AuthorizationServer 에서 설정이 가능)이 보여지게 된다.
	//// 즉, 반드시 로그인 창을 통해서 username, password 를 입력하여야하고, 정보 동의 화면은 설정에 따라 동의 또는 생략하게 된다.
	//// 그래서, API 서버보다는 개별적인 간편로그인 서버로 적합하다.
	
	@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
	@Bean
	public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext)
	{
		return new OAuth2RestTemplate(resource(), oauth2ClientContext);
	}
	
	private OAuth2ProtectedResourceDetails resource()
	{
		log.info("RESOURCE_ID: {}", RESOURCE_ID);
		log.info("CLIENT_ID: {}", CLIENT_ID);
		log.info("CLIENT_SECRET: {}", CLIENT_SECRET);
		log.info("ACCESS_TOKEN_URI: {}", ACCESS_TOKEN_URI);
		log.info("USER_AUTHORIZATION_URI: {}", USER_AUTHORIZATION_URI);
		
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
//		resource.setId(RESOURCE_ID);
		resource.setClientId(CLIENT_ID);
		resource.setClientSecret(CLIENT_SECRET);
		resource.setUserAuthorizationUri(USER_AUTHORIZATION_URI);
		resource.setAccessTokenUri(ACCESS_TOKEN_URI);
		resource.setScope(Arrays.asList("read"));

		return resource;
	}
	
	
	/*/

	
	//// 본 코드를 사용할 경우, 
	//// 접근자에게는 username, password 를 파라메터로 전달받아 로그인을 처리하고
	//// 로그인 후, 정보 동의 화면(옵션으로써 AuthorizationServer 에서 설정이 가능)이 보여지게 된다.
	//// 즉, username, password 입력하는 화면을 통하지 않고서도 로그인이 가능하고, 정보 동의 화면은 설정에 따라 동의 또는 생략하게 된다.
	//// 그래서, API 서버로 적합하다.
	
	@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
	@Bean
	public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext)
	{
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource(), oauth2ClientContext);
		restTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
		
		return restTemplate;
	}
	
	private OAuth2ProtectedResourceDetails resource()
	{
		log.info("RESOURCE_ID: {}", RESOURCE_ID);
		log.info("CLIENT_ID: {}", CLIENT_ID);
		log.info("CLIENT_SECRET: {}", CLIENT_SECRET);
		log.info("ACCESS_TOKEN_URI: {}", ACCESS_TOKEN_URI);
		log.info("USER_AUTHORIZATION_URI: {}", USER_AUTHORIZATION_URI);
		
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setId(RESOURCE_ID);
		resourceDetails.setClientId(CLIENT_ID);
		resourceDetails.setClientSecret(CLIENT_SECRET);
		resourceDetails.setAccessTokenUri(ACCESS_TOKEN_URI);
		resourceDetails.setGrantType("password");
		resourceDetails.setScope(Arrays.asList("read"));
//		resourceDetails.setUsername("cherrypica"); // 파라메터로 받게 됨.
//		resourceDetails.setPassword("password"); // 파라메터로 받게 됨.
//		resourceDetails.setAuthenticationScheme(AuthenticationScheme.header);
		
		return resourceDetails;
	}
	//*/
}
