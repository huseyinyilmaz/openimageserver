package ois.exceptions;

public class ImageDataTooBigException extends Exception {
	public ImageDataTooBigException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageDataTooBigException(String message) {
		super(message);
	}

	public ImageDataTooBigException(Throwable cause) {
		super(cause);
	}

}
