package com.robot.prototype.robot.exceptions;

public class IllegalUrlException extends Exception {

	private String message;
	public IllegalUrlException() {
	}
	public IllegalUrlException(String message) {
		super(message);
		this.message=message;
	}
	
	public String getException() {
		return message;
	}
}
