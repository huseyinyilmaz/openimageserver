package ois.exceptions;

public class NoImageFoundException extends Exception {

	private static final long serialVersionUID = 3372897838570764465L;

	public NoImageFoundException() {
		super();
	}

	public NoImageFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoImageFoundException(String message) {
		super(message);
	}

	public NoImageFoundException(Throwable cause) {
		super(cause);
	}
}
