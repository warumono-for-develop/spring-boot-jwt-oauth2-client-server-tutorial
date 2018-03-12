package com.warumono.client.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.warumono.client.helpers.AppConstant.Swagger.DataType;
import com.warumono.client.helpers.AppConstant.Swagger.ParamType;
import com.warumono.client.helpers.AppConstant.Swagger.Tag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/auth", tags = { "ClientController" })
@RequestMapping(value = "/auth")
public interface ClientControllerInterface
{
	@ApiOperation(value = "OAuth2 로그인", notes = "OAuth2 로그인 API 를 테스트한다.<br/>", tags = { Tag.COMPLETED })
	@ApiImplicitParams
	({
		@ApiImplicitParam(name = "provider_id", value = "Provider ID (facebook, kakao, ...)", dataType = DataType.STRING, paramType = ParamType.PATH, required = false, defaultValue = "cherrypica"), 
		@ApiImplicitParam(name = "username", value = "아이디", dataType = DataType.STRING, paramType = ParamType.QUERY, required = false), 
		@ApiImplicitParam(name = "password", value = "비밀번호", dataType = DataType.STRING, paramType = ParamType.QUERY, required = false), 
		@ApiImplicitParam(name = "withme", value = "사용자 정보 조회 여부", dataType = DataType.BOOOLEAN, paramType = ParamType.QUERY, required = false, defaultValue = "true")
	})
	@RequestMapping(value = "{provider_id}")
	ResponseEntity<Object> login
	(
		@PathVariable(value = "provider_id") String provider_id, 
		@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, 
		@RequestParam(value = "withme", required = false) Boolean withme
	);
	
	@ApiOperation(value = "사용자 접근 권한", notes = "사용자 정보 API 를 테스트한다.<br/>", tags = { Tag.INCOMPLETED })
	@ApiImplicitParams
	({
		@ApiImplicitParam(name = "identity", value = "사용자 identity", dataType = DataType.STRING, paramType = ParamType.PATH, required = false), 
	})
	@GetMapping(value = "role/{role}")
	ResponseEntity<Object> test(@PathVariable(value = "providerId") String providerId);
}
