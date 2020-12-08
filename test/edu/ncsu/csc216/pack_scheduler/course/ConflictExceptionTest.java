/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the custom Exception, ConflictException.
 * If the constructors work, the getMessage() method should return the same message as was passed in,
 * or the default message if one wasn't passed in.
 * @author Jeremy Ignatowitz
 */
public class ConflictExceptionTest {

    /**
     * Tests the constructor, ConflictException(String). Passing in a message should mean that
     * same message is return by calling the getMessage() method on the object.
     */
    @Test
    public void testConflictExceptionString() {
        ConflictException ce = new ConflictException("Custom exception message");
        assertEquals("Custom exception message", ce.getMessage());
    }


    /**
     * Tests the constructor, ConflictException(). Not passing in a message should mean that
     * the default message is returned when the getMessage() method is called on the object.
     */
    @Test
    public void testConflictException() {
        ConflictException ce = new ConflictException();
        assertEquals("Schedule conflict.", ce.getMessage());
    }

}
