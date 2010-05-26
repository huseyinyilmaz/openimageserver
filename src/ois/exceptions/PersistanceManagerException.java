package ois.exceptions;

@SuppressWarnings("serial")
public class PersistanceManagerException extends Exception {

	public PersistanceManagerException() {
		super();
	}

	public PersistanceManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistanceManagerException(String message) {
		super(message);
	}

	public PersistanceManagerException(Throwable cause) {
		super(cause);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "Datastore Exception: <br></br>" 
			  + super.getMessage() + "<br></br>"
			  +"Technical Explanation:<br></br>"
			  + super.getCause().getMessage();
	}
	
	
	
}
