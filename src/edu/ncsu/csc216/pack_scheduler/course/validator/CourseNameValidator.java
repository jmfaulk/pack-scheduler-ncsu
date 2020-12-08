package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Class used to implement the Course validation FSM
 * @author Ethan Taylor
 *
 */
public class CourseNameValidator {

	/**
	 * Abstract class to contain parent base methods for the FSM transitions
	 * @author ectaylor
	 *
	 */
	abstract class State {
		
		/**
		 * Method to provide an FSM path for all transitions regarding a letter input
		 * @throws InvalidTransitionException in a child class implementation if necessary
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * Method to provide an FSM path for all transitions regarding a digit input
		 * @throws InvalidTransitionException in a child implementation if necessary
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * Method to provide an error path for all transitions which are not characters
		 * or digits
		 * @throws InvalidTransitionException any time this method is called, as the
		 * 		only acceptable transitions are with digits and letters.
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}
	
	/**
	 * Inner class to represent the initial state of the FSM
	 * @author ectaylor
	 *
	 */
	private class InitialState extends State {
		
		/**
		 * Private constructor for the initial state of the FSM, to only be
		 * used for one reference state
		 */
		private InitialState() {	}
		
		/**
		 * Method which assesses the FSM on a letter input for the initial state
		 */
		public void onLetter() {
			letterCount++;
			currentState = letter;
		}
		
		/**
		 * Method which assesses the FSM on a digit input for the initial state (throw an error)
		 * @throws InvalidTransitionException thrown if method is called, since the only acceptable
		 * transition is with letters
		 */
		public void onDigit() throws InvalidTransitionException {
		    throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}
	
	/**
	 * Inner class to represent the letter state of the FSM
	 * @author ectaylor
	 *
	 */
	private class LetterState extends State {
	    
		/**
		 * Private constructor for the letter state of the FSM, to only be used for
		 * one reference state
		 */
		private LetterState() {		}
		
		/**
		 * Method which assesses the FSM on a letter input for the letter state, making sure that
		 * the proper number of letters is met and not surpassed
		 * 
		 * @throws InvalidTransitionException throw if method is called, since the only acceptable
		 * transitions are with digits
		 */
		public void onLetter() throws InvalidTransitionException {
			if(letterCount < maxPrefixLetters) {
			    letterCount++;
			}
			else {
			    throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}
		
		/**
		 * Method which assesses the FSM on a digit input for the letter state (transitions to digit state)
		 */
		public void onDigit() {
			digitCount++;
			currentState = digit;
		}
	}
	
	/**
	 * Inner class to represent the nuber state in the FSM
	 * @author ectaylor
	 *
	 */
	private class NumberState extends State {
		
		/**
		 * Private constructor for the number state of the FSM, to only
		 * be used for one reference State
		 */
		private NumberState() {		}
		
		/**
		 * Method which assesses the FSM on a letter input for the digit state, throwing an exception unless
		 * the letter is being appended after a legal amount of numbers is added
		 * 
		 * @throws InvalidTransitionException thrown if method is called and course name does not have 3 digits yet
		 */
		public void onLetter() throws InvalidTransitionException {
		    if(digitCount == courseNumberLength) {
                currentState = suffix;
                validEndState = true;
            }
		    else {
                throw new InvalidTransitionException("Course name must have 3 digits.");
            }

		}
		
		/**
		 * Method which assesses the FSM on a digit input for the digit state, ensuring that the proper number
		 * of digits for a Course name is met and not surpassed
		 * 
		 * @throws InvalidTransitionException thrown if method is called and course name already has 3 digits
		 */
		public void onDigit() throws InvalidTransitionException {
			if(digitCount < courseNumberLength) {
			    digitCount++;
			    if(digitCount == courseNumberLength) {
			        validEndState = true;
			    }
			}
			else
			    throw new InvalidTransitionException("Course name can only have 3 digits.");
		}
	}
	
	/**
	 * Inner class to represent the closing state of the FSM that has no other transitions
	 * @author ectaylor
	 *
	 */
	private class SuffixState extends State {
		
		/**
		 * Private constructor for the number state of the FSM, to only
		 * be used for one reference State
		 */
		private SuffixState() {		}
		
		/**
		 * Method which assesses the FSM on a letter input for the suffix state, throwing an error
		 * as there is no transition in this state
		 * 
		 * @throws InvalidTransitionException thrown if method is called, since course name
		 * suffix should only have 1 letter
		 */
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}
		
		/**
		 * Method which assesses the FSM on a digit input for the suffix state, throwing an error
		 * as there is no transition in this state
		 * 
		 * @throws InvalidTransitionException thrown if method is called, since course name
		 * suffix cannot have digits
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
	
	/** 
	 * The boolean to hold whether the course name reaches a valid end
	 *  state in the FSM 
	 */
	private boolean validEndState = false;
	
	/** Counts letters present in the name tested in the FSM */	
	private int letterCount;
	
	/** Counts digits present in the name tested in the FSM */
	private int digitCount;
	
	/** The maximum amount of letters allowed for a Course name*/
	private final int maxPrefixLetters = 4;
	
	/** The only length of the number allowed for a course name (3 digits)*/
	private final int courseNumberLength = 3;
	
	/** The single instance of the initial state for the FSM*/
	private final State initial = new InitialState();
	
	/** The single instance of the letter state for the FSM*/
	private final State letter = new LetterState();
	
	/** The single instance of the digit state for the FSM*/	
	private final State digit = new NumberState();
	
	/** The single instance of the suffix state for the FSM*/
	private final State suffix = new SuffixState();
	
	private State currentState = initial;
	
	/**
	 * Blank constructor of CourseNameValidator, so that the isValid method can be referenced
	 * outside of the class
	 */
	public CourseNameValidator() {	
		//Blank per reasons provided in the Javadoc comment
	}

	/**
	 * Method which is passed a Course name, and tests it through the use of a FSM to determine
	 * if the Course name is within Course name requirements
	 * 
	 * @param name the course name being tested for validity
	 * @return true if the Course name is acceptable, false if it is not
	 * @throws InvalidTransitionException if one of the state methods has thrown an
	 * 			InvalidTransitionException (i.e. when an error state is reached)
	 */
	public boolean isValid(String name) throws InvalidTransitionException {
	    currentState = initial;
	    letterCount = 0;
	    digitCount = 0;
	    
	    try {
	        for(int i = 0; i < name.length(); i++) {
	            char c = name.charAt(i);
	            if(Character.isDigit(c)) {
	                currentState.onDigit();
	            }
	            else if(Character.isLetter(c)) {
	                currentState.onLetter();
	            }
	            else {
	                currentState.onOther();
	            }
	        }
	    }
	    catch(InvalidTransitionException e) {
	        throw new InvalidTransitionException(e.getMessage());
	    }
		return validEndState;
	}
}
