package com.jsp.studentmanagementsystem.exception;

public class StudentNotFoundByPhnoException extends RuntimeException {

	private String message;

	public StudentNotFoundByPhnoException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}	

}
