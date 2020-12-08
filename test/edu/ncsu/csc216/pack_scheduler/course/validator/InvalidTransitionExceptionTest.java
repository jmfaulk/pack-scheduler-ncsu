package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the InvalidTransitionException class
 * 
 * @author Ethan Taylor
 * @author Gage Fringer
 * @author Jeremy Ignatowitz
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Tests the InvalidTransitionException constructor with a String parameter, 
	 * and checks that getMessage() can be used from the super class.
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ite = new InvalidTransitionException("Custom exception message");
		assertEquals("Custom exception message", ite.getMessage());
	}

	/**
	 * Tests the InvalidTransitionException constructor with without parameters,
	 * checks that default message is correct, and checks that getMessage() can be used from the super class.
	 */
	@Test
	public void testInvalidTransitionException() {
		InvalidTransitionException ite = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ite.getMessage());
	}
}
