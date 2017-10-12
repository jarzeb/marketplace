package org.jz.marketplace.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({RepositoryConstraintViolationException.class})
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request){

		RepositoryConstraintViolationException requestException = (RepositoryConstraintViolationException) ex;
		Map<String, String> errorMap = new HashMap<String, String>();
		List<FieldError> errors = requestException.getErrors().getFieldErrors();

		Errors e = requestException.getErrors();
		ObjectError objError = e.getGlobalError();
		String errorCode = objError == null ? null : objError.getCode();
		
		if(errorCode != null) {
			// Object level error
			errorMap.put("Validation Error", errorCode);
		} else {
			// Field level error(s)
			for(FieldError err : errors) {
				String field = err.getField();
				String code = err.getCode();
				errorMap.put(field, code);
			}
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Object>(errorMap, responseHeaders, HttpStatus.BAD_REQUEST);
	}

}