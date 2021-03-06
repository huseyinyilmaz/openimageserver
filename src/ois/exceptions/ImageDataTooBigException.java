package ois.exceptions;

public class ImageDataTooBigException extends Exception {

	/**
	 * Serial ID 
	 */
	private static final long serialVersionUID = -2673251137057258996L;

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
