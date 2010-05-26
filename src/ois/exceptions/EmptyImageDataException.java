package ois.exceptions;

@SuppressWarnings("serial")
public class EmptyImageDataException extends Exception {

	public EmptyImageDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyImageDataException(String message) {
		super(message);
	}

	public EmptyImageDataException(Throwable cause) {
		super(cause);
	}

}
