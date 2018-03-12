package com.warumono.client.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.warumono.client.controllers.interfaces.ClientControllerInterface;
import com.warumono.client.exceptions.ExceptionReason;
import com.warumono.client.exceptions.RestException;
import com.warumono.client.services.DeviceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ClientController implements ClientControllerInterface
{
	// http://localhost:8082/user/me
	@Value("${security.oauth2.resource.user-info-uri}")
	private String USER_INFO_URI;
	
	@Autowired
	private OAuth2RestOperations restTemplate;
	
	@Autowired
	private DeviceService deviceService;
 	
	@Override
	public ResponseEntity<Object> login
	(
		@PathVariable String provider_id, 
		String username, String password, 
		Boolean withme
	)
	{
		log.debug("provider_id : {}", provider_id);
		
		log.debug("restTemplate : {}", restTemplate);
		log.debug("USER_INFO_URI : {}", USER_INFO_URI);
		
		Object principalNode = null;
		
		
		deviceService.test();
		
//		Integer code = Exceptions.log(logger, "Hello exception!").get(() -> Integer.parseInt("ABC")).orElse(HttpStatus.BAD_REQUEST.value());
//		log.debug("code : {}", code);
		
		try
		{
			if(Objects.nonNull(withme) && withme)
			{
				MultiValueMap<String, Object> resource = new LinkedMultiValueMap<>();
				resource.set("username", username);
				resource.set("password", password);
				
				principalNode = restTemplate.getForObject(USER_INFO_URI, JsonNode.class, resource);
			}
			else
			{
				principalNode = restTemplate.getAccessToken();
			}
			
			log.debug("principal : {}", principalNode);
		}
		catch(HttpClientErrorException e)
		{
			log.error("{}", e.getResponseBodyAsString());
			log.error("{}", e.getStatusText());
			
			throw e;
		}
		
		return ResponseEntity.ok(principalNode);
	}

	@Override
	public ResponseEntity<Object> test(@PathVariable String providerId)
	{
		ResponseEntity<Object> response = restTemplate.getForEntity("http://localhost:8082/user/profile/" + providerId, Object.class);
		
		log.debug("response : {}", response);
		
		return response;
	}
}
