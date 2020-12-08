package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the functionality of Student. Ensures that all methods are working to specification.
 * @author jcignato
 * @author nkobodum
 */
public class StudentTest {

    /**
     * Tests the hashCode method of Student.
     * Two equal objects will return the same hashcode.
     */
    @Test
    public void testHashCode() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        User t = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        User gavin = new Student("gavin", "gavin", "200215643", "gggavin@ncsu.edu", "password");
        
        assertEquals(s.hashCode(), t.hashCode());
        assertNotEquals(s.hashCode(), gavin.hashCode());
    }

    /**
     * Tests a constructor for Student. The constructor should set the first name, last name, ID, 
     * email, hashed password, and maximum credit hours for a student.
     */
    @Test
    public void testStudentStringStringStringStringStringInt() {
        Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        assertEquals("first", s.getFirstName());
        assertEquals("last", s.getLastName());
        assertEquals("id", s.getId());
        assertEquals("email@ncsu.edu", s.getEmail());
        assertEquals("hashpw", s.getPassword());
        assertEquals(15, s.getMaxCredits());
    }

    /**
     * Tests a constructor for Student. This constructor sets first name, last name, ID, email,
     * and hashed password. The maximum credit hours are set to the default of 18.
     */
    @Test
    public void testStudentStringStringStringStringString() {
        Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw");
        assertEquals("first", s.getFirstName());
        assertEquals("last", s.getLastName());
        assertEquals("id", s.getId());
        assertEquals("email@ncsu.edu", s.getEmail());
        assertEquals("hashpw", s.getPassword());
        assertEquals(18, s.getMaxCredits());
        
        //Test to ensure that failing ID's create failing cases.
        String testId = "";
        try {
			new Student("first", "last", testId, "email@ncsu.edu", "password");
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(testId, "");
		}
        
        testId = null;
		try {
			new Student("first", "last", testId, "email@ncsu.edu", "password");
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(testId, null);
		}
    }

    /**
     * Tests the setFirstName() method of Student.
     * Constructs a student object, then tests setting a new value for the firstName field.
     * If there's an invalid value, the field should not be changed.
     */
    @Test
    public void testSetFirstName() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        s.setFirstName("gavin");
        assertEquals("gavin", s.getFirstName());
        
        try {
            s.setFirstName(null);
            fail(); //We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            //We've caught the exception, now we need to make sure that the field didn't change
            assertEquals("gavin", s.getFirstName());
        }
    }

    /**
     * Tests the setLastName() method of Student.
     * Constructs a student object, then tests setting a new value for the lastName field.
     * If there's an invalid value, the field should not be changed.
     */
    @Test
    public void testSetLastName() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        s.setLastName("gavin");
        assertEquals("gavin", s.getLastName());
        
        try {
            s.setLastName(null);
            fail(); //We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            //We've caught the exception, now we need to make sure that the field didn't change
            assertEquals("gavin", s.getLastName());
        }
    }
    
    /**
     * Tests the setEmail() method of Student.
     * Constructs a student object, then tests setting a new value for the email field.
     * If there's an invalid value, the field should not be changed.
     */
    @Test
    public void testSetEmail() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        s.setEmail("gavin@gavin.com");
        assertEquals("gavin@gavin.com", s.getEmail());
        
        try {
            s.setEmail(null);
            fail(); //We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            //We've caught the exception, now we need to make sure that the field didn't change
            assertEquals("gavin@gavin.com", s.getEmail());
        }
    }

    /**
     * Tests the setPassword() method of Student.
     * Constructs a student object, then tests setting a new value for the hashPW field.
     * If there's an invalid value, the field should not be changed.
     */
    @Test
    public void testSetPassword() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        s.setPassword("password");
        assertEquals("password", s.getPassword());
        
        try {
            s.setPassword(null);
            fail(); //We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            //We've caught the exception, now we need to make sure that the field didn't change
            assertEquals("password", s.getPassword());
        }
    }

    /**
     * Tests the setMaxCredits() method of Student.
     * Constructs a student object, then tests setting a new value for the maxCredits field.
     * If there's an invalid value, the field should not be changed.
     */
    @Test
    public void testSetMaxCredits() {
        Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        s.setMaxCredits(12);
        assertEquals(12, s.getMaxCredits());
        
        try {
            s.setMaxCredits(100);
            fail(); //We don't want to reach this point - an exception should be thrown!
        } catch (IllegalArgumentException e) {
            //We've caught the exception, now we need to make sure that the field didn't change
            assertEquals(12, s.getMaxCredits());
        }
    }

    /**
     * Tests the equals() method of Student.
     * Two Students are equal if they have the same values for all their fields.
     */
    @Test
    public void testEqualsObject() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        User t = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        User gavin = new Student("gavin", "gavin", "200215643", "gggavin@ncsu.edu", "password");
        User s2 = s;
        User n = null;
        
        assertTrue(s.equals(t));
        assertFalse(s.equals(gavin));
        assertTrue(s.equals(s2));
        assertFalse(s.equals(n));
    }

    /**
     * Tests the toString() method of Student.
     * A Student string contains a comma-separated list of all its fields.
     */
    @Test
    public void testToString() {
        User s = new Student("first", "last", "id", "email@ncsu.edu", "hashpw", 15);
        String result = "first,last,id,email@ncsu.edu,hashpw,15";
        assertEquals(result, s.toString());
    }
    
    /**
     * Tests the compareTo method in student.
     * Students are sorted alphabetically by last name, first name, and Unity ID in that order.
     * compareTo should return a positive integer if the calling object is greater than the 
     * passed-in object, a negative integer if the calling object is less than the passed-in 
     * object, and zero if the two are equal.
     */
    @Test
    public void testCompareTo() {
        Student gavin = new Student("Gavin", "Gavin", "gggavin", "gggavin@ncsu.edu", "hashedPassword");
        Student richter = new Student("Richter", "Belmont", "rpbelmon", "rpbelmon@ncsu.edu", "hashedPassword");
        Student gavinLess = new Student("Alfred", "Gavin", "avgavin", "avgavin@ncsu.edu", "hashedPassword");
        Student gavinMore = new Student("Zachary", "Gavin", "zfgavin", "zfgavin@ncsu.edu", "hashedPassword");
        Student richterIDLess = new Student("Richter", "Belmont", "rabelmon", "rabelmon@ncsu.edu", "hashedPassword");
        Student richterIDMore = new Student("Richter", "Belmont", "rzbelmon", "rzbelmon@ncsu.edu", "hashedPassword");
        Student demetrius = new Student("Demetrius", "Austin", "daustin", "daustin@ncsu.edu", "pw");
        Student daniel = new Student("Daniel", "Austin", "daustin3", "daustin3@ncsu.edu", "pw");
        
        
        assertTrue(richter.compareTo(gavin) < 0);
        assertTrue(gavin.compareTo(richter) > 0);
        assertTrue(gavin.compareTo(gavinLess) > 0);
        assertTrue(gavin.compareTo(gavinMore) < 0);
        assertTrue(richter.compareTo(richterIDLess) > 0);
        assertTrue(richter.compareTo(richterIDMore) < 0);
        assertEquals(gavin.compareTo(gavin), 0);
        assertTrue(demetrius.compareTo(daniel) > 0);
    };

    /**
     * Test if a course can be added to a student's schedule.
     */
    @Test
    public void testCanAdd() {
        Student gavin = new Student("Gavin", "Gavin", "gggavin", "gggavin@ncsu.edu", "hashedPassword");
        assertFalse(gavin.canAdd(null));
        assertTrue(gavin.canAdd(new Course("CSC217", "Programming Concepts... 2!", "001", 5, "sesmith5", 10, "MWF", 1200, 1350)));
        gavin.getSchedule().addCourseToSchedule(new Course("CSC217", "Programming Concepts... 2!", "001", 5, "sesmith5", 10, "MWF", 1200, 1350));
        gavin.getSchedule().addCourseToSchedule(new Course("CSC216", "Programming Concepts... 1.", "001", 5, "sesmith5", 10, "MWF", 1500, 1600));
        gavin.getSchedule().addCourseToSchedule(new Course("CSC215", "Programming Concepts... 0?!", "001", 5, "sesmith5", 10, "MWF", 1700, 1800));
        assertFalse(gavin.canAdd(new Course("CSC218", "Testing Course", "001", 5, "sessmith5", 10, "TH", 1240, 1520)));
    }
    
}
