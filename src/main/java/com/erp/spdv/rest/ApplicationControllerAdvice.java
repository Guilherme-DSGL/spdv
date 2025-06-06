package com.erp.spdv.rest;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.erp.spdv.rest.exceptions.ApiErrors;

import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationErrors(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<String> messages = bindingResult.getAllErrors()
				.stream()
				.map(objectError -> objectError.getDefaultMessage())
				.collect(Collectors.toList());
		return new ApiErrors(messages);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiErrors> handleResponseStatusException(ResponseStatusException ex) {
		String message = ex.getMessage();
		HttpStatus codigoStatus = (HttpStatus) ex.getStatusCode();
		ApiErrors apiErros = new ApiErrors(message);
		return new ResponseEntity<ApiErrors>(apiErros, codigoStatus);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiErrors> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		String message = ex.getMessage();
		ApiErrors apiErros = new ApiErrors(message);
		return new ResponseEntity<ApiErrors>(apiErros, HttpStatus.BAD_REQUEST);
	}
}
