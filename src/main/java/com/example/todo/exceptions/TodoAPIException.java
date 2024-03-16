package com.example.todo.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoAPIException extends RuntimeException{
	
	private HttpStatus httpStatus;
	private String message;

}
