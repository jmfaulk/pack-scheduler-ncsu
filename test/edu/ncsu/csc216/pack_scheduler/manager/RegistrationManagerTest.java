package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;


/**
 * Test of RegistrationManager Class
 * @author Gage Fringer
 * @author Jeremy Ignatowitz 
 * @author Ethan Taylor
 *
 */
public class RegistrationManagerTest {
	
	private RegistrationManager manager;
	
	private final String propFile = "registrar.properties";	
	
	/**
	 * Sets up the RegistrationManager and clears the data.
	 * @throws Exception if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
		manager.logout();
	}

	/**
	 * Tests the getCourseCatalog method
	 */
	@Test
	public void testGetCourseCatalog() {
		try {
			setUp();
			assertArrayEquals(manager.getCourseCatalog().getCourseCatalog(),
					new CourseCatalog().getCourseCatalog());
			
			String name = "CSC216";
			String title = "Intro to Programming - Java";
			String section = "001";
			int credits = 4;
			String instructorId = "sesmith5";
			String meetingDays = "MWF";
			int startTime = 1120;
			int endTime = 1250;
			int classSize = 10;

			Course testCourse = new Course(name, title, section, credits, instructorId, classSize, meetingDays, startTime, endTime);
			
			manager.getCourseCatalog().addCourseToCatalog(name, title, section, credits, instructorId, classSize, meetingDays, startTime, endTime);
			
			assertEquals(manager.getCourseCatalog().getCourseCatalog()[0][0], testCourse.getName());
			assertEquals(manager.getCourseCatalog().getCourseCatalog()[0][1], testCourse.getSection());
			assertEquals(manager.getCourseCatalog().getCourseCatalog()[0][2], testCourse.getTitle());
			assertEquals(manager.getCourseCatalog().getCourseCatalog()[0][3], testCourse.getMeetingString());
			
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail("Could not set up RegistrationManager");
		}
	}

	/**
	 * Tests the getStudentDirectory method
	 */
	@Test
	public void testGetStudentDirectory() {
		try {
			setUp();
			assertArrayEquals(manager.getStudentDirectory().getStudentDirectory(),
					new StudentDirectory().getStudentDirectory());
			
			String testFirst = "Sam";
			String testLast = "Smith";
			String testId = "ssmith";
			String testEmail = "ssmith@ncsu.edu";
			String testPw = "mypassword789";
			
			Student testStudent = new Student(testFirst, testLast, testId, testEmail, testPw);
			
			manager.getStudentDirectory().addStudent(testFirst, testLast, testId, testEmail, testPw, testPw, 18);

			assertEquals(manager.getStudentDirectory().getStudentDirectory()[0][0], testStudent.getFirstName());
			assertEquals(manager.getStudentDirectory().getStudentDirectory()[0][1], testStudent.getLastName());
			assertEquals(manager.getStudentDirectory().getStudentDirectory()[0][2], testStudent.getId());
			
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail("Could not set up RegistrationManager: " + e.getMessage());
		}
	}

	/**
	 * Tests the login method
	 */
	@Test
	public void testLogin() {
		
		String id = "tester"; //Change using .PROCESS
		String password = "pword123";
		
			try {
                setUp();
                manager.login(id, password);
                fail();
            } catch (Exception e) {
                assertEquals(e.getMessage(), "User doesn't exist.");
            }
			
		
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(propFile);
			prop.load(input);
			
			id = prop.getProperty("id");
			password = prop.getProperty("pw");
		} catch (IOException e) {
			fail("file not found");
		}
		
		assertTrue(manager.login(id, password));
		User current = manager.getCurrentUser();
		try {
		    assertFalse(manager.login("efrost", "pw"));
		}
		catch (IllegalArgumentException e) {
		    assertEquals(manager.getCurrentUser(), current);
		}
		manager.logout();
		//testId = manager.getProperty(id);
		assertFalse(manager.login(id, "bogus"));
	}

	/**
	 * Tests the logout method
	 */
	@Test
	public void testLogout() {
		
		String id;
		String password;
		
		try {
			setUp();
		} catch (Exception e) {
			fail("setUp failed");
		}
		
		Properties prop = new Properties();
		InputStream input;
		
		try {
			input = new FileInputStream(propFile);
			prop.load(input);
			
			id = prop.getProperty("id");
			password = prop.getProperty("pw");
			
			assertTrue(manager.login(id, password));
		} catch (IOException e) {
			fail("File not found");
		}
		
		manager.logout();
		
		assertEquals(manager.getCurrentUser(), null);
	}

	/**
	 * Tests the getCurrentUser method
	 */
	@Test
	public void testGetCurrentUser() {
		String first;
		String last;
		String id; 
		String email;
		String password;
		
		String testFirst = "Sam";
		String testLast = "Smith";
		String testId = "ssmith";
		String testEmail = "ssmith@ncsu.edu";
		String testPw = "mypassword789";
		
		Student testStudent = new Student(testFirst, testLast, testId, testEmail, testPw);
		
		try {
			setUp();
		} catch (Exception e) {
			fail("setUp failed");
		}
		
		manager.getStudentDirectory().addStudent(testFirst, testLast, testId, testEmail, testPw, testPw, 18);
		
		assertEquals(manager.getCurrentUser(), null);
		
		assertTrue(manager.login(testId, testPw));

		assertEquals(manager.getCurrentUser().getFirstName(), testStudent.getFirstName());
		assertEquals(manager.getCurrentUser().getLastName(), testStudent.getLastName());
		assertEquals(manager.getCurrentUser().getId(), testStudent.getId());
		assertEquals(manager.getCurrentUser().getEmail(), testStudent.getEmail());
		
		manager.logout();
		
		Properties prop = new Properties();
		InputStream input;
		
		try {
			input = new FileInputStream(propFile);
			prop.load(input);
			
			first = prop.getProperty("first");
			last = prop.getProperty("last");
			id = prop.getProperty("id");
			email = prop.getProperty("email");
			password = prop.getProperty("pw");
			
			assertTrue(manager.login(id, password));
			
			assertEquals(manager.getCurrentUser().getFirstName(), first);
			assertEquals(manager.getCurrentUser().getLastName(), last);
			assertEquals(manager.getCurrentUser().getId(), id);
			assertEquals(manager.getCurrentUser().getEmail(), email);
		} catch (IOException e) {
			fail("File not found");
		}
	}

	
	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "002"));
	        fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(3, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(1, scheduleFrostArray.length);
	    assertEquals("CSC226", scheduleFrostArray[0][0]);
	    assertEquals("001", scheduleFrostArray[0][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleFrostArray[0][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleFrostArray[0][3]);
	    assertEquals("9", scheduleFrostArray[0][4]);
	            
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    //Check Student Schedule
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(10, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(3, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC226", scheduleHicksArray[1][0]);
	    assertEquals("001", scheduleHicksArray[1][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
	    assertEquals("8", scheduleHicksArray[1][4]);
	    assertEquals("CSC116", scheduleHicksArray[2][0]);
	    assertEquals("003", scheduleHicksArray[2][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
	    assertEquals("9", scheduleHicksArray[2][4]);
	    
	    manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    assertFalse("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: False - student not enrolled in the course", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(0, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(0, scheduleFrostArray.length);
	    
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(10, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(3, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC226", scheduleHicksArray[1][0]);
	    assertEquals("001", scheduleHicksArray[1][1]);
	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
	    assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
	    assertEquals("9", scheduleHicksArray[1][4]);
	    assertEquals("CSC116", scheduleHicksArray[2][0]);
	    assertEquals("003", scheduleHicksArray[2][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
	    assertEquals("9", scheduleHicksArray[2][4]);
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(7, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(2, scheduleHicksArray.length);
	    assertEquals("CSC216", scheduleHicksArray[0][0]);
	    assertEquals("001", scheduleHicksArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    assertEquals("CSC116", scheduleHicksArray[1][0]);
	    assertEquals("003", scheduleHicksArray[1][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
	    assertEquals("9", scheduleHicksArray[1][4]);
	    
	    assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(3, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(1, scheduleHicksArray.length);
	    assertEquals("CSC116", scheduleHicksArray[0][0]);
	    assertEquals("003", scheduleHicksArray[0][1]);
	    assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2]);
	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
	    assertEquals("9", scheduleHicksArray[0][4]);
	    
	    assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True", manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    
	    //Check schedule
	    ahicks = directory.getStudentById("ahicks");
	    scheduleHicks = ahicks.getSchedule();
	    assertEquals(0, scheduleHicks.getScheduleCredits());
	    scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(0, scheduleHicksArray.length);
	    
	    manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
	    StudentDirectory directory = manager.getStudentDirectory();
	    directory.loadStudentsFromFile("test-files/student_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt");
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //Test if not logged in
	    try {
	        manager.resetSchedule();
	        fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	        manager.resetSchedule();
	        fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    manager.login("efrost", "pw");
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    manager.resetSchedule();
	    //Check Student Schedule
	    Student efrost = directory.getStudentById("efrost");
	    Schedule scheduleFrost = efrost.getSchedule();
	    assertEquals(0, scheduleFrost.getScheduleCredits());
	    String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
	    assertEquals(0, scheduleFrostArray.length);
	    
	    manager.logout();
	    
	    manager.login("ahicks", "pw");
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
	    
	    manager.resetSchedule();
	    //Check Student schedule
	    Student ahicks = directory.getStudentById("ahicks");
	    Schedule scheduleHicks = ahicks.getSchedule();
	    assertEquals(0, scheduleHicks.getScheduleCredits());
	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
	    assertEquals(0, scheduleHicksArray.length);
	    
	    manager.logout();
	}
	
	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testAddFacultyToCourse() {
	    FacultyDirectory directory = manager.getFacultyDirectory();
	    directory.loadFacultyFromFile("test-files/faculty_records_extended.txt"); //loads the faculty direcdtory
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/add_faculty.txt"); //loads the courses available to assign faculty to
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	        assertFalse(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC226", "001"), directory.getFacultyById("sesmith"))); //returns boolean false
	        //fail("RegistrationManager.enrollFacultyToCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.addFacultyToCourse() - currentUser is null, so cannot add a faculty.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    Course test = null;
	    Faculty fac = null;
	    
	    //add faculty to course
	    try {
	    	test = catalog.getCourseFromCatalog("CSC216", "001");
	    	fac = directory.getFacultyById("sesmith5");
	        boolean holder = manager.addFacultyToCourse(test, fac); //add faculty to course
	        assertTrue(holder);
	        
	    } catch (IllegalArgumentException e) {
	    	fail("RegistrationManager.addFacultyToCourse() - If the current user is registrar, an IllegalArgumentException should not be thrown, but was.");
	        
	    }
	    manager.logout();

	    //Check Faculty Schedule
	    Faculty smith = directory.getFacultyById("sesmith5");
	    FacultySchedule scheduleSmith = smith.getSchedule();
	    assertEquals(1, scheduleSmith.getNumScheduledCourses());
	    String[][] scheduleSmithArray = scheduleSmith.getScheduledCourses(); //name,section,title meetingString, open seats
	    assertEquals(1, scheduleSmithArray.length);
	    assertEquals("CSC216", scheduleSmithArray[0][0]);
	    assertEquals("001", scheduleSmithArray[0][1]);
	    assertEquals("Programming Concepts - Java", scheduleSmithArray[0][2]);
	    assertEquals("MW 1:30PM-2:45PM", scheduleSmithArray[0][3]);
	    assertEquals("10", scheduleSmithArray[0][4]);
	    smith.toString();
	    
	  //test duplicate course  
	    manager.login("registrar", "Regi5tr@r");
	    try {
	    	 fac = directory.getFacultyById("sesmith5");
	        assertFalse(manager.addFacultyToCourse(catalog.getCourseFromCatalog("CSC216", "001"), fac));
	    } catch (IllegalArgumentException e) {
	    	fail();
	          
	    }
	            
	    manager.logout();
//	    
//	    manager.login("ahicks", "pw");
//	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
//	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
//	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
//	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
//	    assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
//	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
//	    assertFalse("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits", manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));
//	    
//	    //Check Student Schedule
//	    Student ahicks = directory.getStudentById("ahicks");
//	    Schedule scheduleHicks = ahicks.getSchedule();
//	    assertEquals(10, scheduleHicks.getScheduleCredits());
//	    String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
//	    assertEquals(3, scheduleHicksArray.length);
//	    assertEquals("CSC216", scheduleHicksArray[0][0]);
//	    assertEquals("001", scheduleHicksArray[0][1]);
//	    assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
//	    assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
//	    assertEquals("9", scheduleHicksArray[0][4]);
//	    assertEquals("CSC226", scheduleHicksArray[1][0]);
//	    assertEquals("001", scheduleHicksArray[1][1]);
//	    assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
//	    assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
//	    assertEquals("8", scheduleHicksArray[1][4]);
//	    assertEquals("CSC116", scheduleHicksArray[2][0]);
//	    assertEquals("003", scheduleHicksArray[2][1]);
//	    assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
//	    assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
//	    assertEquals("9", scheduleHicksArray[2][4]);
//	    
	    //manager.logout();
	}
	
	/**
	 * Tests RegistrationManager.removeFacultyFromCourse()
	 */
	@Test
	public void testRemoveFacultyFromCourse() {
	    FacultyDirectory directory = manager.getFacultyDirectory();
	    directory.loadFacultyFromFile("test-files/faculty_records.txt");
	    
	    //this actually tests logging in as a faculty member
	    manager.login("awitt", "pw");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt"); //TODO we may need a different file
	    
	    manager.logout(); //In case not handled elsewhere
	    
	    //test if not logged in
	    try {
	    	Faculty fac = directory.getFacultyById("sesmith5");
	        manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC216", "001"), fac);
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.", manager.getCurrentUser());
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	    	Faculty fac = directory.getFacultyById("awitt");
	        manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC216", "001"), fac);
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
	    
	    //Check Faculty's Schedule
	    //TODO stopped here; we need more test files, ask the TA's
	    Faculty smith = directory.getFacultyById("awitt");
	    FacultySchedule scheduleSmith = smith.getSchedule();
	    assertEquals(0, scheduleSmith.getNumScheduledCourses()); //assuming starts with 2 and then removed one
	    String[][] scheduleSmithArray = scheduleSmith.getScheduledCourses();
	    assertEquals(0, scheduleSmithArray.length);
	    
	     
	  //test invalid course
	    manager.login("registrar", "Regi5tr@r");
	    try {
	    	Faculty fac = directory.getFacultyById("awitt");
	        assertFalse(manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC216", "003"), fac));
	    } catch (IllegalArgumentException e) {
	    	fail();
	          
	    }
    
	    manager.logout();
	    
	    
	    
	}
	
	
	/**
	 * Tests RegistrationManager.removeFacultyFromCourse()
	 */
	@Test
	public void testResetFacultySchedule() {
		
		FacultyDirectory directory = manager.getFacultyDirectory();
	    directory.loadFacultyFromFile("test-files/faculty_records.txt");
	    
	    CourseCatalog catalog = manager.getCourseCatalog();
	    catalog.loadCoursesFromFile("test-files/course_records.txt"); 
	    
		//test if not logged in
	    Faculty fac = null; 
	    int size = 0; 
	    manager.logout(); 
	    try {
	    	fac = directory.getFacultyById("sesmith5");    
	    	//size = fac.getSchedule().getNumScheduledCourses(); 
	        manager.resetFacultySchedule(fac);
	        fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
	    } catch (IllegalArgumentException e) {
	        assertEquals(size, 0 );
	    }
	    
	    //test if registrar is logged in
	    manager.login("registrar", "Regi5tr@r");
	    try {
	    	fac = directory.getFacultyById("awitt");
	        manager.removeFacultyFromCourse(catalog.getCourseFromCatalog("CSC216", "001"), fac);
	    } catch (IllegalArgumentException e) {
	        assertEquals("RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.", "registrar", manager.getCurrentUser().getId());
	    }
	    manager.logout();
		
	}

	
}
