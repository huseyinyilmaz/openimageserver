package ois.exceptions;

public class InvalidNameException extends Exception {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 3847261821281429358L;

	public InvalidNameException(String msg){
		super(msg);
	}
}
