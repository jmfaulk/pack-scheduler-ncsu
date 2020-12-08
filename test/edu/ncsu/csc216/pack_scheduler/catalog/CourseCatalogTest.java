package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * A suite of JUnit tests for the CourseCatalog class.
 * @author Jeremy Ignatowitz
 */
public class CourseCatalogTest {

    /** The test file used to read courses in from a file. */
    private final String validTestFile = "test-files/course_records.txt";
    
    private final String[] validCourse1 = { "CSC116", "001", "Intro to Programming - Java", "MW 9:10AM-11:00AM"};
    private final String[] validCourse2 = { "CSC116", "002", "Intro to Programming - Java", "MW 11:20AM-1:10PM" };
    private final String[] validCourse3 = { "CSC116", "003", "Intro to Programming - Java", "TH 11:20AM-1:10PM" };
    private final String[] validCourse4 = { "CSC216", "001", "Programming Concepts - Java", "TH 1:30PM-2:45PM" };
    private final String[] validCourse5 = { "CSC216", "002", "Programming Concepts - Java", "MW 1:30PM-2:45PM" };
    private final String[] validCourse6 = { "CSC216", "601", "Programming Concepts - Java", "Arranged" };
    private final String[] validCourse7 = { "CSC226", "001", "Discrete Mathematics for Computer Scientists", "MWF 9:35AM-10:25AM" };
    private final String[] validCourse8 = { "CSC230", "001", "C and Software Tools", "MW 11:45AM-1:00PM" };
    
    private final String[][] validCourses = {validCourse1, validCourse2, validCourse3, validCourse4,
            validCourse5, validCourse6, validCourse7, validCourse8};
    
    /**
     * Tests constructing the CourseCatalog object.
     * This should make a new CourseCatalog object, with an empty SortedList.
     */
    @Test
    public void testCourseCatalog() {
        CourseCatalog cc = new CourseCatalog();
        
        assertEquals(0, cc.getCourseCatalog().length);
    }
    
    /**
     * Tests loading in courses from a file.
     * This should add all the courses listed in the given test file to the catalog, then 
     * ensure that all of them are added to the SortedList.
     */
    @Test
    public void testLoadCoursesFromFile() {
        CourseCatalog cc = new CourseCatalog();
        cc.loadCoursesFromFile(validTestFile);
        String[][] courses = cc.getCourseCatalog();
        for (int i = 0; i < validCourses.length; i++) {
            for(int j = 0; j < validCourses[0].length; j++) {
                assertEquals(validCourses[i][j], courses[i][j]);
            }
        }
        
    }

    /**
     * Tests adding a course to the catalog.
     * This should pass in the parameters for a Course object, then add it to the SortedList
     * in CourseCatalog. It should then make sure that course is added by comparing the catalog array.
     */
    @Test
    public void testAddCourseToCatalog() {
        CourseCatalog cc = new CourseCatalog();
        cc.addCourseToCatalog("CSC217", "Programming Concepts - Java ...2!", "001", 4, "sesmith5", 10, "MWF", 1120, 1250);
        assertFalse(cc.addCourseToCatalog("CSC217", "Programming Concepts - Java ...2!", "001", 4, "sesmith5", 10, "MWF", 1120, 1250));
        assertArrayEquals(cc.getCourseCatalog(), new String[][] { { "CSC217",  "001", "Programming Concepts - Java ...2!", "MWF 11:20AM-12:50PM", "10" } });
    }
    
    /**
     * Tests removing a course from the catalog.
     * This should add a course to the catalog, ensure it was added, and then remove it and ensure it was removed.
     * This should ensure that the Course array is empty after it's removed.
     */
    @Test
    public void testRemoveCourseFromCatalog() {
        CourseCatalog cc = new CourseCatalog();
        cc.addCourseToCatalog("CSC217", "Programming Concepts - Java ...2!", "001", 4, "sesmith5", 10, "MWF", 1120, 1250);
        assertArrayEquals(cc.getCourseCatalog(), new String[][] { { "CSC217",  "001", "Programming Concepts - Java ...2!", "MWF 11:20AM-12:50PM", "10" } });
        cc.removeCourseFromCatalog("CSC217", "001");
        assertEquals(cc.getCourseCatalog().length, 0);
    }
    
    /**
     * Tests getting a course from the catalog.
     * This should add a course to the catalog, then try to pull it from the catalog.
     * A new Course object is constructed with the same parameters; the two objects should be equal.
     */
    @Test
    public void testGetCourseFromCatalog() {
        CourseCatalog cc = new CourseCatalog();
        cc.addCourseToCatalog("CSC217", "Programming Concepts - Java ...2!", "001", 4, "sesmith5", 10, "MWF", 1120, 1250);
        Course course = new Course("CSC217", "Programming Concepts - Java ...2!", "001", 4, "sesmith5", 10, "MWF", 1120, 1250);
        assertEquals(cc.getCourseFromCatalog("CSC217", "001"), course);
    }
    
    /**
     * Test saving the course catalog to a file.
     * This should add the given courses to the SortedList in CourseCatalog, then write the SortedList to a file.
     * Then the two files are compared using the checkFiles() helper method.
     */
    @Test
    public void testSaveCourseCatalog() {
        CourseCatalog cc = new CourseCatalog();
        cc.addCourseToCatalog("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440);
        cc.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "MW", 1330, 1445);
        cc.addCourseToCatalog("CSC216", "Programming Concepts - Java", "601", 4, "jep", 10, "A");
        cc.saveCourseCatalog("test-files/actual_course_records.txt");
        
        
        String expFile = "test-files/expected_course_records.txt"; 
        String actFile = "test-files/actual_course_records.txt";
        
        try {
            Scanner expScanner = new Scanner(new File (expFile));
            Scanner actScanner = new Scanner(new File(actFile));
            
            while (expScanner.hasNextLine()) {
                assertEquals(expScanner.nextLine(), actScanner.nextLine());
            }
            
            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
    }
}
