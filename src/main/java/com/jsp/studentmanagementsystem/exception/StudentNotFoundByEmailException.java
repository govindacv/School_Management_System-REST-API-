package com.jsp.studentmanagementsystem.exception;

public class StudentNotFoundByEmailException extends RuntimeException {
	private String message;

	public StudentNotFoundByEmailException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	

}
