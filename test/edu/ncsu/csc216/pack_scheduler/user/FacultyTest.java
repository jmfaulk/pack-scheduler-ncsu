package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

/** Test class to test Faculty member.
 * 
 * @author Sumit Biswas
 * @author Jared Faulk
 * @author Jeremy Ignatowitz
 */
public class FacultyTest {

	/** Tests hash code. */
	@Test
	public void testHashCode() {
		User f1 = new Faculty("Some", "Guy", "sguy", "sguy@someuni.edu", "randompw", 3);
		User f2 = new Faculty("Some", "Guy", "sguy", "sguy@someuni.edu", "randompw", 3);
		User f3 = new Faculty("Someother", "Guy", "soguy", "soguy@someuni.edu", "randompw2", 3);
		
		assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1.hashCode(), f3.hashCode());
	}

	/** Tests Faculty.equals(). */
	@Test
	public void testEqualsObject() {
		User f1 = new Faculty("Some", "Guy", "sguy", "sguy@someuni.edu", "randompw", 3);
		User f2 = new Faculty("Some", "Guy", "sguy", "sguy@someuni.edu", "randompw", 3);
		User f3 = new Faculty("Someother", "Guy", "soguy", "soguy@someuni.edu", "randompw2", 3);
		
		assertTrue(f1.equals(f2));
		assertFalse(f1.equals(f3));
	}

	/** Tests constructor. */
	@Test
	public void testFaculty() {
		Faculty f1 = new Faculty("Some", "Guy", "sguy", "sguy@someuni.edu", "randompw", 3);
		assertEquals("Some", f1.getFirstName());
		assertEquals("Guy", f1.getLastName());
		assertEquals("sguy", f1.getId());
		assertEquals("sguy@someuni.edu", f1.getEmail());
		assertEquals("randompw", f1.getPassword());
		assertEquals(3, f1.getMaxCourses());
	}

	/** 
	 * Tests Faculty.setMaxCourses() and Faculty.getMaxCourses().
	 */
	@Test
	public void testMaxCourses() {
		Faculty f1 = new Faculty("Some", "Guy", "sguy", "sguy@someuni.edu", "randompw", 3);
		
		try {
			f1.setMaxCourses(5);
		} catch (IllegalArgumentException e) {
			assertEquals(3, f1.getMaxCourses());
		}
		
		try {
			f1.setMaxCourses(0);
		} catch (IllegalArgumentException e) {
			assertEquals(3, f1.getMaxCourses());
		}
	}

	/** Tests Faculty.toString. */
	@Test
	public void testToString() {
		Faculty f1 = new Faculty("Some", "Guy", "sguy", "sguy@someuni.edu", "randompw", 3);
		String fts = "Some,Guy,sguy,sguy@someuni.edu,randompw,3";
		assertEquals(fts, f1.toString());
	}

}
