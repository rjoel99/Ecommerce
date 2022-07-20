package com.joel.controller.exception;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.joel.message.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author joel.rubio
 *
 */
@Slf4j
@ControllerAdvice
public class ControllerException {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(WebRequest request, EntityNotFoundException exception) {
		
		ErrorMessage message = ErrorMessage.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(exception.getMessage())
				.datetime(LocalDateTime.now())
				.path(((ServletWebRequest) request).getRequest().getRequestURI())
				.build();
		
		log.error(exception.getMessage());
		
		return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({IllegalArgumentException.class, ClassCastException.class})
	public ResponseEntity<Object> handleIllegalArgumentException(WebRequest request, RuntimeException exception) {
		
		ErrorMessage message = ErrorMessage.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(exception.getMessage())
				.datetime(LocalDateTime.now())
				.path(((ServletWebRequest) request).getRequest().getRequestURI())
				.build();
	
		log.error(exception.getMessage());
		
		return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
