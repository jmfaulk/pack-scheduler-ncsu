/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A custom exception used when two activities have a schedule conflict.
 * @author Jeremy Ignatowitz
 */
public class ConflictException extends Exception {

    /** ID used for serialization */
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new ConflictException by passing the passed-in message to a new Exception.
     * @param message the message for the exception
     */
    public ConflictException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new ConflictException using the default message of "Schedule conflict."
     */
    public ConflictException() {
        this("Schedule conflict.");
    }
}
