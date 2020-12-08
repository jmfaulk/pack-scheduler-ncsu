/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Schedule class
 * 
 * @author Ethan Taylor
 * @author Gage Fringer
 * @author Jeremy Ignatowitz
 */
public class ScheduleTest {

	/**
	 * Tests the default Schedule constructor
	 */
	@Test
	public void testSchedule() {
		
		String defaultTitle = "My Schedule";
		String[][] defaultEmptyList = new String[0][5];
		
		Schedule schedule = new Schedule();
		
		assertEquals(defaultTitle, schedule.getTitle());
		assertArrayEquals(defaultEmptyList, schedule.getScheduledCourses());
	}
	
	/**
	 * Tests adding a Course to Schedule
	 */
	@Test
	public void testAddCourseToSchedule() {
		
		CourseCatalog catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
		
		Schedule schedule = new Schedule();
		
		Course course1 = catalog.getCourseFromCatalog("CSC116", "001");
		Course course2 = catalog.getCourseFromCatalog("CSC116", "001");
		Course course3 = catalog.getCourseFromCatalog("CSC226", "001");
		
		assertTrue(schedule.addCourseToSchedule(course1));
				
		assertEquals("CSC116", schedule.getScheduledCourses()[0][0]);
		assertEquals("001", schedule.getScheduledCourses()[0][1]);
		assertEquals("Intro to Programming - Java", schedule.getScheduledCourses()[0][2]);
		assertEquals("MW 9:10AM-11:00AM", schedule.getScheduledCourses()[0][3]);
		
		try {
			schedule.addCourseToSchedule(course2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in CSC116", e.getMessage());
		}
		
		assertFalse(schedule.canAdd(course2));
		
		try {
			schedule.addCourseToSchedule(course3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
		
		assertFalse(schedule.canAdd(course3));
	}
	
	/**
	 * Tests removing a Course from Schedule
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		
		CourseCatalog catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
		
		Schedule schedule = new Schedule();
		
		Course course = catalog.getCourseFromCatalog("CSC116", "001");
		
		assertTrue(schedule.addCourseToSchedule(course));
		
		assertEquals("CSC116", schedule.getScheduledCourses()[0][0]);
		assertEquals("001", schedule.getScheduledCourses()[0][1]);
		assertEquals("Intro to Programming - Java", schedule.getScheduledCourses()[0][2]);
		assertEquals("MW 9:10AM-11:00AM", schedule.getScheduledCourses()[0][3]);
		
		assertTrue(schedule.removeCourseFromSchedule(course));
		
		assertEquals(0, schedule.getScheduledCourses().length);
	}
	
	/**
	 * Tests reseting a Schedule to empty
	 */
	@Test
	public void testResetSchedule() {
		
		String[][] defaultEmptyList = new String[0][4];
		
		CourseCatalog catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
		
		Schedule schedule = new Schedule();
		
		Course course = catalog.getCourseFromCatalog("CSC116", "001");
		
		assertTrue(schedule.addCourseToSchedule(course));
		
		assertEquals("CSC116", schedule.getScheduledCourses()[0][0]);
		assertEquals("001", schedule.getScheduledCourses()[0][1]);
		assertEquals("Intro to Programming - Java", schedule.getScheduledCourses()[0][2]);
		assertEquals("MW 9:10AM-11:00AM", schedule.getScheduledCourses()[0][3]);
		
		schedule.resetSchedule();
		assertArrayEquals(defaultEmptyList, schedule.getScheduledCourses());
	}
	
	/**
	 * Tests getting a scheduled courses as a 2D String array
	 */
	@Test
	public void testGetScheduledCourses() {
		
		CourseCatalog catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
		
		Schedule schedule = new Schedule();
		
		Course course = catalog.getCourseFromCatalog("CSC116", "001");
		
		assertTrue(schedule.addCourseToSchedule(course));
		
		assertEquals("CSC116", schedule.getScheduledCourses()[0][0]);
		assertEquals("001", schedule.getScheduledCourses()[0][1]);
		assertEquals("Intro to Programming - Java", schedule.getScheduledCourses()[0][2]);
		assertEquals("MW 9:10AM-11:00AM", schedule.getScheduledCourses()[0][3]);
	}
	
	/**
	 * Tests setting a custom title for Schedule
	 */
	@Test
	public void testSetTitle() {
		
		String defaultTitle = "My Schedule";
		String newTitle = "New Title";
		
		Schedule schedule = new Schedule();
		
		assertEquals(defaultTitle, schedule.getTitle());
		
		schedule.setTitle(newTitle);
		assertEquals(newTitle, schedule.getTitle());
	}
	
	/**
	 * Tests getting title of Schedule
	 */
	@Test
	public void testGetTitle() {
		
		String defaultTitle = "My Schedule";
		
		Schedule schedule = new Schedule();
		
		assertEquals(defaultTitle, schedule.getTitle());
	}
	
	/**
	 * Test if a class can be added to a schedule.
	 */
	@Test
	public void testGetScheduleCredits() {
	    Schedule schedule = new Schedule();
	    
	    schedule.addCourseToSchedule(new Course("CSC217", "Programming Concepts... 2!", "001", 4, "sesmith5", 10, "MWF", 1200, 1350));
	    schedule.addCourseToSchedule(new Course("CSC226", "Discrete Math 2: Return of Jafar", "001", 4, "ixdoming", 10, "TH", 935, 1025));
	    schedule.addCourseToSchedule(new Course("HESF101", "Health and Wellness", "003", 1, "dxdoctor", 10, "TF", 1630, 1745));
	    
	    assertEquals(schedule.getScheduleCredits(), 9);
	}
}
