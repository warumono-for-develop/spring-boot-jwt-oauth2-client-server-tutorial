package com.warumono.client.annotations;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OAuth2UsernameArgumentResolver implements HandlerMethodArgumentResolver
{
	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		return parameter.getParameterAnnotation(CurrentUsername.class) != null && parameter.getParameterType().equals(String.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null)
		{
			return null;
		}
		
		String username = null;

		if(authentication.getClass().isAssignableFrom(OAuth2Authentication.class))
		{
			username = ((OAuth2Authentication)authentication).getUserAuthentication().getName();
		}
		
		return username;
	}
}
