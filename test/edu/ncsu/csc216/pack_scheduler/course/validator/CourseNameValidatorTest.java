package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for the CourseNameValidatorFSM class
 * @author gwfringe
 *
 */
public class CourseNameValidatorTest {
	
	/**
	 * Tests the FSM for the StateL state
	 */
	@Test
	public void testIsValid() {
		CourseNameValidator fsm = new CourseNameValidator();
		String courseName = "CSC116A";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "CSC116";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "CS116A";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "CS116";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "C116A";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "C116";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "CSCC116A";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "CSCC116";
		try {	
			assertTrue(fsm.isValid(courseName));
		} catch(InvalidTransitionException e) {
			fail();
		}
		
		courseName = "1";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
		
		courseName = "@";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		courseName = "CSCSC116A";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
		
		courseName = "CSC11A";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		courseName = "CSC1A";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		courseName = "CSC1167A";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
		
		courseName = "CSC116AB";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
		
		courseName = "CSC116A1";
		try {	
			fsm.isValid(courseName);
			fail();
		} catch(InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
	}
}
