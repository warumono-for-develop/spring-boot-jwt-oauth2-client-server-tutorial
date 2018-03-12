package com.warumono.client.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception Reason
 * 
 * <pre>
 * <header>USER code <b>1xxx</b></header>
 * 사용자를 찾을 수 없음	NOT_FOUND_USER(10000, "Not found user")
 * 비밀번호 미일치		MISMATCH_USERNAME(10001, "Mismatch your username, try again")
 * 
 * <header>OVERALL code <b>9xxx</b></header>
 * 잘못된 요청		WRONG_REQUEST(9000, "Wrong request")
 * 서버 오류		SERVER_ERROR(9001, "Server error")
 * 리소스를 찾지 못함		NOT_FOUND(9002, "Not found")
 * 필수 항목		REQUIRED(9003, "Required")
 * 알 수 없는 예외사항	UNCLASSIFIED(9999, "Unclassified error")
 * </pre>
 * 
 * @author warumono
 *
 */
@AllArgsConstructor
@Getter
public enum ExceptionReason
{
	// USER
	NOT_YET_AUTHORIZED(10000, "Not yet authorized"), 
	UNKOWN_USER(10001, "Unkown user"), 
	MISMATCH_USERNAME(10002, "Mismatch your username, try again"), 
	
	// OVERALL
	WRONG_REQUEST(9000, "Wrong request"), 
	SERVER_ERROR(9001, "Server error"), 
	NOT_FOUND(9002, "Not found"), 
	REQUIRED(9003, "Required"), 
	UNCLASSIFIED(9999, "Unclassified error");
	
	private Integer code;
	private String cause;
}
