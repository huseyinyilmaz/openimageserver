package ois.exceptions;

public class DeleteSystemRevisionException extends Exception {

	private static final long serialVersionUID = 8419235676505740088L;

	public DeleteSystemRevisionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteSystemRevisionException(String message) {
		super(message);
	}

	public DeleteSystemRevisionException(Throwable cause) {
		super(cause);
	}

}
