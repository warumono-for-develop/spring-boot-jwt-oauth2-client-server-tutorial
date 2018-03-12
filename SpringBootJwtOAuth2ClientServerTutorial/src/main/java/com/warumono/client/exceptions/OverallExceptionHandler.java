package com.warumono.client.exceptions;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintViolationException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class OverallExceptionHandler
{
	private ResponseEntity<ExceptionResponse> exception(ExceptionReason reason, Throwable e)
	{
		return exception(reason, HttpStatus.INTERNAL_SERVER_ERROR, e);
	}
	
	private ResponseEntity<ExceptionResponse> exception(ExceptionReason reason, HttpStatus error, Throwable e)
	{
		return new ResponseEntity<ExceptionResponse>
		(
			ExceptionResponse.builder()
				.code(reason.getCode())
				.cause(reason.getCause())
				.status(error.value())
				.error(error.getReasonPhrase())
				.message(e.getMessage())
				.build(), 
			error
		);
	}
	
	@ExceptionHandler(value = { RestException.class })
	public ResponseEntity<ExceptionResponse> handleRestException(RestException e)
	{
		return exception(e.getReason(), e.getError(), e);
	}
	
	/**
	 * Handle failures commonly thrown from code
	 * 
	 * @param e
	 * @return
	 */
//	@ExceptionHandler(value = { InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class, ConversionFailedException.class, ConstraintViolationException.class })
//	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException e)
	@ExceptionHandler(value = { InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class, ConversionFailedException.class })
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(Throwable e)
	{
		return exception(ExceptionReason.WRONG_REQUEST, HttpStatus.BAD_REQUEST, e);
	}
	
	/**
	 * Send a 409 Conflict in case of concurrent modification
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class })
	public ResponseEntity<ExceptionResponse> handleConflict(Exception e)
	{
		return exception(ExceptionReason.SERVER_ERROR, HttpStatus.CONFLICT, e);
	}
	
	/**
	 * Others
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ExceptionResponse> handleException(Exception e)
	{
		return exception(ExceptionReason.SERVER_ERROR, e);
	}
	
	//////
	
	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	protected ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, WebRequest request)
	{
		return exception(ExceptionReason.SERVER_ERROR, e);
	}
	
	//////

	/**
	 * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
	 * 
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected ResponseEntity<ExceptionResponse> handleDataIntegrityViolation(DataIntegrityViolationException e, WebRequest request)
	{
		if(e.getCause() instanceof ConstraintViolationException)
		{
			return exception(ExceptionReason.MISMATCH_USERNAME, HttpStatus.CONFLICT, e);
		}
		
		return exception(ExceptionReason.SERVER_ERROR, e);
	}

    /**
     * 
     * Handle Exception, handle generic Exception.class
     * 
     */
	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
	protected ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, WebRequest request)
	{
		return exception(ExceptionReason.WRONG_REQUEST, HttpStatus.BAD_REQUEST, e);
	}
}
