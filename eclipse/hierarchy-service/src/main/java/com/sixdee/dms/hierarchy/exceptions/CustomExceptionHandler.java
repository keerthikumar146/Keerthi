package com.sixdee.dms.hierarchy.exceptions;

import java.sql.SQLException;

import javax.persistence.NonUniqueResultException;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sixdee.dms.hierarchy.utils.Sequencer;
import com.sixdee.dms.hierarchy.utils.StatusCodes;
import com.sixdee.dms.utils.common.CommonResponse;
import com.sixdee.dms.utils.exceptions.CommonException;
import com.sixdee.dms.utils.exceptions.ResourceNotFoundException;
import com.sixdee.dms.utils.service.MessageUtil;

/**
 * @author balu.s
 *
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@Autowired
	Sequencer sequencer;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String transactionId = sequencer.getSequenceId();

		CommonResponse errorDetail = new CommonResponse(StatusCodes.INTERNAL_ERROR_CODE,
				ex.getBindingResult().getFieldError().getDefaultMessage(), transactionId);
		if (LOGGER.isErrorEnabled())
			LOGGER.error("Method Argument Not Valid : {}", errorDetail.toString());

		if (LOGGER.isDebugEnabled()) {
			ex.printStackTrace();
		}

		return new ResponseEntity<Object>(errorDetail, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles client, server and gateway exceptions
	 *
	 * @param ex Exception that denotes any problems.
	 * @return response entity that represents the exception response
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleException(Throwable ex) {

		if (LOGGER.isErrorEnabled())
			LOGGER.error("Internal server error : {}", ex.toString());

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.INTERNAL_ERROR_CODE, ex.getMessage(), transactionId);

		if (LOGGER.isDebugEnabled()) {
			ex.printStackTrace();
		}

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleRuntimeException(Throwable ex) {

		if (LOGGER.isErrorEnabled())
			LOGGER.error("Runtime Exception error : {}", ex.toString());

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.INTERNAL_ERROR_CODE,
				MessageUtil.get("dms.runtime.exception.message"), transactionId);
		ex.printStackTrace();
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleDataException(Throwable ex) {

		if (LOGGER.isErrorEnabled())
			LOGGER.error("Runtime Exception error : {}", ex.toString());

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.INTERNAL_ERROR_CODE,
				MessageUtil.get("dms.runtime.exception.message"), transactionId);
		ex.printStackTrace();
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handlerExceptionResolver(IllegalArgumentException e) {

		if (LOGGER.isInfoEnabled())
			LOGGER.info("Returning HTTP 400 Bad Request", e);

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.INTERNAL_ERROR_CODE, e.getMessage(), transactionId);

		if (LOGGER.isDebugEnabled()) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ResourceNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handlerExceptionResolver(ResourceNotFoundException e) {

		if (LOGGER.isInfoEnabled())
			LOGGER.info("Resource Not Found : {}", e);

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.INTERNAL_ERROR_CODE, e.getMessage(), transactionId);

		if (LOGGER.isDebugEnabled()) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ BadCredentialsException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handlerExceptionResolver(BadCredentialsException e) {
		
		if (LOGGER.isInfoEnabled())
			LOGGER.info("Invalid username/Password : {}", e.getMessage());
		
		String transactionId = sequencer.getSequenceId();
		
		CommonResponse response = new CommonResponse(StatusCodes.UNAUTHORIZED_STATUS_CODE, e.getMessage(),
				transactionId);
		
		if (LOGGER.isDebugEnabled()) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handlerExceptionResolver(AccessDeniedException e) {

		if (LOGGER.isInfoEnabled())
			LOGGER.info("Unauthorized access : {}", e.getMessage());

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.UNAUTHORIZED_STATUS_CODE, e.getMessage(),
				transactionId);

		return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ LockedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handlerExceptionResolver(LockedException e) {

		if (LOGGER.isInfoEnabled())
			LOGGER.info("Account Blocked : {}", e.getMessage());

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.FORBIDDEN_STATUS_CODE, e.getMessage(), transactionId);
		return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ CommonException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> commonException(CommonException ex) {
		String statusCode = StatusCodes.INTERNAL_ERROR_CODE;
		String message = (ex != null) ? ex.getMessage() : MessageUtil.get("dms.runtime.exception.message");

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(statusCode, message, transactionId);

		LOGGER.error("CommonException : ResultCode-" + statusCode + " : Message : " + message);

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ SQLException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> sqlException(SQLException ex) {
		String statusCode = StatusCodes.INTERNAL_ERROR_CODE;
		String message = MessageUtil.get("dms.runtime.exception.message");

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(statusCode, message, transactionId);

		LOGGER.error("CommonException : ResultCode-" + statusCode + " : Message : " + message);

		if (LOGGER.isDebugEnabled()) {
			ex.printStackTrace();
		}

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ HttpMessageConversionException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> httpMessageConversionException(HttpMessageConversionException ex) {
		String statusCode = StatusCodes.INTERNAL_ERROR_CODE;
		String message = MessageUtil.get("dms.runtime.exception.message");

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(statusCode, message, transactionId);

		LOGGER.error("CommonException : ResultCode-" + statusCode + " : Message : " + message);

		if (LOGGER.isDebugEnabled()) {
			ex.printStackTrace();
		}

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException e) {

		if (LOGGER.isInfoEnabled())
			LOGGER.info("File upload size exceeded", e);

		String transactionId = sequencer.getSequenceId();
		return new ResponseEntity<Object>(
				new CommonResponse(StatusCodes.CUSTOM_FIELD_VALIDATION, "File upload size exceeded.", transactionId),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ NonUniqueResultException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> nonUniqueResultPersistanceExceptiom(NonUniqueResultException ex) {
		String message = MessageUtil.get("dms.runtime.exception.message");

		String transactionId = sequencer.getSequenceId();

		CommonResponse response = new CommonResponse(StatusCodes.INTERNAL_ERROR_CODE, message, transactionId);

		LOGGER.error(
				"NonUniqueResultException : ResultCode-" + StatusCodes.INTERNAL_ERROR_CODE + " : Message : " + message);

		if (LOGGER.isDebugEnabled()) {
			ex.printStackTrace();
		}

		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
