package com.lfn.Web.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lfn.Exception.EntityNotFoundException;
import com.lfn.Exception.UsernameUniqueViolationException;

import jakarta.servlet.http.HttpServletRequest;


//Esta anotação funciona como uma " ouvinte ", vai capitar todas as excecoes e elas vao vir para esta classe
@RestControllerAdvice 
public class ApiExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMesage> entityNotFoundException(EntityNotFoundException ex,
																		HttpServletRequest request){
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMesage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
		
	}
	@ExceptionHandler(UsernameUniqueViolationException.class)
	public ResponseEntity<ErrorMesage> uniqueViolationException(RuntimeException ex,
			HttpServletRequest request){
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMesage(request, HttpStatus.CONFLICT, ex.getMessage()));
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMesage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request,
			BindingResult result){
		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMesage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalidos", result));
		
	}

}
