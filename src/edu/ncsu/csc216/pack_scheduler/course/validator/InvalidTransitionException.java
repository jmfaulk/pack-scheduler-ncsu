package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * A custom Exception class for invalid FSM transitions.
 * Thrown when no requested FSM transition is available.
 * 
 * @author Ethan Taylor
 * @author Gage Fringer
 * @author Jeremy Ignatowitz
 */
public class InvalidTransitionException extends Exception {
	
	/**
	 *  Default serialVersion ID as provided by the Quick Fix
	 */
	private static final long serialVersionUID = 1L;
		
	/**
	 * Constructs an Exception with the specified message.
	 * 
	 * @param message exception message
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Constructs an InvalidTransitionException with its default message.
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}
}
