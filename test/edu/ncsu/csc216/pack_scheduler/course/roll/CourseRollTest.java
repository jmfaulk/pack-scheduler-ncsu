package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the CourseRoll class
 * 
 * @author Ethan Taylor
 *
 */
public class CourseRollTest {
    
    private String[][] rollArray = { { "Demetrius", "Austin", "daustin" },
                                     { "Lane", "Berg", "lberg" },
                                     { "Raymond", "Brennan", "rbrennan" },
                                     { "Emerald", "Frost", "efrost" },
                                     { "Shannon", "Hansen", "shansen" },
                                     { "Althea", "Hicks", "ahicks" },
                                     { "Zahir", "King", "zking" },
                                     { "Dylan", "Nolan", "dnolan" },
                                     { "Cassandra", "Schwartz", "cschwartz" },
                                     { "Griffith", "Stone", "gstone" } };

	/**
	 * Tests the CourseRoll constructor to see if
	 */
	@Test
	public void testCourseRollint() {
		CourseRoll courseRoll = null;

		int inBound = 150;
		int lowerBound = 10;
		int upperBound = 250;
		int lowerOutBound = 9;
		int upperOutBound = 251;

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", inBound, "A");
		courseRoll = c.getCourseRoll();

		// Tests that roll initialized as an empty list by checking that
		// the open seats is the same as the enrollment cap because the
		// enrollment cap minus roll.size() should just be the enrollment
		// cap if roll.size() is 0
		try {
			courseRoll = c.getCourseRoll();
			assertEquals(inBound, courseRoll.getOpenSeats());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		courseRoll = null;

		// Tests that enrollmentCap can be a value between 10 and 250
		try {
			courseRoll = c.getCourseRoll();
			assertEquals(inBound, courseRoll.getEnrollmentCap());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		courseRoll = null;

		// Tests that enrollmentCap can be equal to MIN_ENROLLMENT, which is 10
		c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", lowerBound, "A");
		try {
			courseRoll = c.getCourseRoll();
			assertEquals(lowerBound, courseRoll.getEnrollmentCap());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		courseRoll = null;

		// Tests that enrollmentCap can be equal to MAX_ENROLLMENT, which is 250
		c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", upperBound, "A");
		try {
			courseRoll = c.getCourseRoll();
			assertEquals(upperBound, courseRoll.getEnrollmentCap());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}
		courseRoll = null;

		// Tests that an exception is thrown if enrollmentCap is less than 10

		try {
			c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", lowerOutBound, "A");
			fail("Should throw exception");
		} catch (Exception e) {
			assertNull(courseRoll);
		}
		courseRoll = null;

		// Tests that an exception is thrown if enrollmentCap is greater than 250
		try {
			c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", upperOutBound, "A");
			fail("Should throw exception");
		} catch (Exception e) {
			assertNull(courseRoll);
		}
	}

	/**
	 * Tests CourseRoll.getEnrollmentCap()
	 */
	public void testGetEnrollmentCap() {
		fail("Not yet initialized");
	}

	/**
	 * Tests CourseRoll.setEnrollmentCap()
	 */
	@Test
	public void testSetEnrollmentCap() {

		int inBound = 150;
		int lowerBound = 10;
		int upperBound = 250;
		int lowerOutBound = 9;
		int upperOutBound = 251;

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "A");
		CourseRoll courseRoll = c.getCourseRoll();

		assertEquals(100, courseRoll.getEnrollmentCap());

		// Tests that enrollmentCap can be set to value between 10 and 250
		try {
			courseRoll.setEnrollmentCap(inBound);
			assertEquals(inBound, courseRoll.getEnrollmentCap());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}

		// Tests that enrollmentCap can be set equal to MIN_ENROLLMENT, which is 10
		try {
			courseRoll.setEnrollmentCap(lowerBound);
			assertEquals(lowerBound, courseRoll.getEnrollmentCap());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}

		// Tests that enrollmentCap can be set equal to MAX_ENROLLMENT, which is 250
		try {
			courseRoll.setEnrollmentCap(upperBound);
			assertEquals(upperBound, courseRoll.getEnrollmentCap());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}

		// Tests that an exception is thrown if enrollmentCap is set to less than 10
		int cap = courseRoll.getEnrollmentCap();
		try {
			courseRoll.setEnrollmentCap(lowerOutBound);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(cap, courseRoll.getEnrollmentCap());
		}

		// Tests that an exception is thrown if enrollmentCap is set to greater than 250
		try {
			courseRoll.setEnrollmentCap(upperOutBound);
			fail("Should throw exception");
		} catch (Exception e) {
			assertEquals(cap, courseRoll.getEnrollmentCap());
		}

		// Adds students to roll
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");

			for (int i = 0; i < students.size(); i++) {
				courseRoll.enroll(students.get(i));
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		// Tests that enrollmentCap can still be set to 10 if roll.size() is 10
		try {
			courseRoll.setEnrollmentCap(10);
			assertEquals(10, courseRoll.getEnrollmentCap());
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}

		try {
			courseRoll.enroll(new Student("First", "Last", "id", "email@ncsu.edu", "password"));
		} catch (IllegalArgumentException e) {
			assertEquals(courseRoll.getOpenSeats(), 0);
		}
	}

	/**
	 * Tests CousreRoll.enroll()
	 */
	@Test
	public void testEnroll() {

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll courseRoll = c.getCourseRoll();

		// Adds students to roll
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");

			for (int i = 0; i < students.size(); i++) {
				courseRoll.enroll(students.get(i));
			}
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}

		// Tests adding a null student to courseRoll
		try {
			Student nullStudent = null;
			courseRoll.enroll(nullStudent);
			fail("Should throw exception");
		} catch (IllegalArgumentException e) {
			assertEquals("Student can't be null", e.getMessage());
			assertEquals(10, courseRoll.getEnrollmentCap()); // enrollmentCap should not have changed
		}

		// Tests adding an already enrolled student to courseRoll
		try {
			students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
			courseRoll.enroll(students.get(0));
			fail("Should throw exception");
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file");
		} catch (IllegalArgumentException e) {
//			assertEquals("Course is full or student is already enrolled", e.getMessage());
			assertEquals("The course cannot be added", e.getMessage());
			assertEquals(10, courseRoll.getEnrollmentCap()); // enrollmentCap should not have changed
		}
	}

	/**
	 * Tests CourseRoll.drop()
	 */
	@Test
	public void testDrop() {

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll courseRoll = c.getCourseRoll();

		Student student = new Student("first", "last", "id", "email@ncsu.edu", "password");

		courseRoll.enroll(student);

		// Tests dropping a null student
		try {
			Student nullStudent = null;
			courseRoll.drop(nullStudent);
			fail("Should throw exception");
		} catch (IllegalArgumentException e) {
			assertEquals("Student can't be null", e.getMessage());
		}

		// Tests dropping an enrolled student
		try {
			assertEquals(9, courseRoll.getOpenSeats());
			courseRoll.drop(student);
			assertEquals(10, courseRoll.getOpenSeats()); //10 open seats
		} catch (Exception e) {
			fail(e.getMessage());
		}

		courseRoll.enroll(student);
		courseRoll.enroll(new Student("gavin", "gavin", "gggavin", "gggavin@ncsu.edu", "gavin", 15));
		courseRoll.enroll(new Student("Matthew", "Mercer", "memercer", "memercer@ncsu.edu", "RollInitiative42"));
		courseRoll.enroll(new Student("Taliesin", "Jaffe", "tajaffe", "tajaffe@ncsu.edu", "ExecutiveXGoth"));
		courseRoll.enroll(new Student("Miles", "Morales", "mcmorales", "mcmorales@ncsu.edu", "Gr8Xpectations"));
		courseRoll.enroll(new Student("Mario", "Mario", "mmario", "mmario@ncsu.edu", "WaHoo!!"));
		courseRoll.enroll(new Student("Luigi", "Mario", "lmario", "lmario@ncsu.edu", "OhYeah!!"));
		courseRoll.enroll(new Student("Stuart", "Ashens", "sjashens", "sjashens@ncsu.edu", "Tit4Tat87"));
		courseRoll.enroll(new Student("Hallo", "Ween", "hhween", "hhween@ncsu.edu", "HappyHalloWeen!"));
		courseRoll.enroll(new Student("Gino", "Fratelli", "gmfratell", "gmfratell@ncsu.edu", "Money4Nothin"));

		courseRoll.enroll(new Student("Wait", "List", "wflist", "wflist@ncsu.edu", "FiNaLlYlOl"));
		assertEquals(courseRoll.getNumberOnWaitlist(), 1);
		courseRoll.drop(new Student("Wait", "List", "wflist", "wflist@ncsu.edu", "FiNaLlYlOl"));
		assertEquals(courseRoll.getNumberOnWaitlist(), 0);

		courseRoll.enroll(new Student("Wait", "List", "wflist", "wflist@ncsu.edu", "FiNaLlYlOl"));
        assertEquals(courseRoll.getNumberOnWaitlist(), 1);
        courseRoll.drop(new Student("Taliesin", "Jaffe", "tajaffe", "tajaffe@ncsu.edu", "ExecutiveXGoth"));
		
		// now test dropping two students to see if there will be two open seats
		// afterwards
//		try {
			courseRoll.drop(new Student("gavin", "gavin", "gggavin", "gggavin@ncsu.edu", "gavin", 15));
			courseRoll.drop(new Student("Matthew", "Mercer", "memercer", "memercer@ncsu.edu", "RollInitiative42"));
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}

	}

	/**
	 * Tests CourseRoll.getOpenSeats()
	 */
	@Test
	public void testGetOpenSeats() {

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 50, "A");
		CourseRoll courseRoll = c.getCourseRoll();

		int seats = 50;

		assertEquals(seats, courseRoll.getOpenSeats());

		courseRoll.enroll(new Student("first", "last", "id", "email@ncsu.edu", "password"));
		assertEquals(seats - 1, courseRoll.getOpenSeats());
	}

	/**
	 * Tests CourseRoll.canEnroll()
	 */
	@Test
	public void testCanEnroll() {

		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll courseRoll = c.getCourseRoll();

		Student student = new Student("first", "last", "id", "email@ncsu.edu", "password");

		// Adds students to roll
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");

			for (int i = 0; i < students.size(); i++) {
				courseRoll.enroll(students.get(i));
			}
		} catch (Exception e) {
			fail("Should not throw exception: " + e.getMessage());
		}

		courseRoll.setEnrollmentCap(12);
		assertTrue(courseRoll.canEnroll(student));

		courseRoll.enroll(student);
		assertEquals(1, courseRoll.getOpenSeats());

		assertFalse(courseRoll.canEnroll(student));
	}
	
	/**
	 * Tests getting an array of all students in a course's roll
	 */
	@Test
	public void testGetRoll() {
	    Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
        CourseRoll courseRoll = c.getCourseRoll();

        // Adds students to roll
        SortedList<Student> students;
        try {
            students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");

            for (int i = 0; i < students.size(); i++) {
                courseRoll.enroll(students.get(i));
            }
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
        
        assertArrayEquals(rollArray, courseRoll.getRoll());
	}
}
