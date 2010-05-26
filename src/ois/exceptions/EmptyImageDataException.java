package ois.exceptions;

public class EmptyImageDataException extends Exception {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -1615503410633486316L;

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
