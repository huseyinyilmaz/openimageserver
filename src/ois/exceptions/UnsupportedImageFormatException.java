package ois.exceptions;

public class UnsupportedImageFormatException extends Exception {

	private static final long serialVersionUID = 4302835826786257157L;

	public UnsupportedImageFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedImageFormatException(String message) {
		super(message);
	}

	public UnsupportedImageFormatException(Throwable cause) {
		super(cause);
	}
}
