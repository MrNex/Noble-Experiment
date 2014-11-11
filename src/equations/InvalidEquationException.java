package equations;

public class InvalidEquationException extends Exception {

	public InvalidEquationException() {
		super("Submitted equations must be valid equations only.");
		
	}

	public InvalidEquationException(String errorMessage) {
		super(errorMessage);
		
	}

	public InvalidEquationException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidEquationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InvalidEquationException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
