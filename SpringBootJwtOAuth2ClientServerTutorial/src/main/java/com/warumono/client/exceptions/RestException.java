package com.warumono.client.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	private ExceptionReason reason;
	private HttpStatus error;
	
	public RestException(ExceptionReason reason)
	{
		this(reason, new RuntimeException());
	}
	
	public RestException(ExceptionReason reason, Throwable e)
	{
		this(reason, HttpStatus.INTERNAL_SERVER_ERROR, e);
	}
	
	/**
	 * @param reason 사용자 정의 예외 사항
	 * @param error 실질적으로 발생하는 예외 사항
	 * @param e Exception
	 */
	public RestException(ExceptionReason reason, HttpStatus error, Throwable e)
	{
		super(e.getMessage());
		
		this.reason = reason;
		this.error = error;
	}
}
