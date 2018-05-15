package exceptions;

public class InvalidTupleValueException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidTupleValueException(String message) {
		super(message);
	}

}
