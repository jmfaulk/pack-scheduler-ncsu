package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * A class that represents a catalog of courses and its functionality.
 * @author Jeremy Ignatowitz
 * @author Ryan Hurlbut
 * @author Noble Obodum
 */
public class CourseCatalog {
    /** The list to hold Courses to be referenced by CourseCatalog methods*/
	private SortedList<Course> catalog;
    
    /**
     * Constructs a new object of type CourseCatalog.
     * This will create a new SortedList of courses to apply to the catalog field.
     */
    public CourseCatalog() {
        this.newCourseCatalog();
    }
    
    /**
     * Creates a new course catalog, or in practice resets the current one.
     * This will create a new SortedList of courses to apply to the catalog field.
     */
    public void newCourseCatalog() {
        this.catalog = new SortedList<Course>();
    }
    
    /**
     * Loads in courses from a file, then adds those courses to the catalog.
     * This basically just calls CourseRecordIO.readCourseRecords(), because it does the same thing.
     * @param fileName the name of the file to read the courses in from
     * @throws IllegalArgumentException thrown if file is not found
     */
    public void loadCoursesFromFile(String fileName) {
        try {
            this.catalog = CourseRecordIO.readCourseRecords(fileName);
        }
        catch(FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Adds in the specified course to the catalog.
     * The course will be created based on the parameters, and then added directly to the SortedList
     * for the catalog.
     * @param name name of the course
     * @param title title of the course
     * @param section section number for the course
     * @param credits credit hours the course provides
     * @param instructorId unity id of the instructor
     * @param classSize the number of seats in the class
     * @param meetingDays days on which the class meets
     * @param startTime time at which the class starts
     * @param endTime time at which the class ends
     * @return true if the course can be added, false if it cannot
     */
    public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int classSize, String meetingDays,
            int startTime, int endTime) {
        if(meetingDays.equals("A")) {
            return addCourseToCatalog(name, title, section, credits, instructorId, classSize, meetingDays);
        }
        Course c = new Course(name, title, section, credits, instructorId, classSize, meetingDays, startTime, endTime);
        for(int i = 0; i < catalog.size(); i++) {
            if(c.isDuplicate(catalog.get(i)) && section.equals(catalog.get(i).getSection()))
                return false;
        }
        return this.catalog.add(c);
    }
    
    /**
     * Adds in the specified course to the catalog.
     * The course will be created based on the parameters, and then added directly to the SortedList
     * for the catalog.
     * This version of the method is used when the course is supposed to be arranged.
     * @param name name of the course
     * @param title title of the course
     * @param section section number for the course
     * @param credits credit hours the course provides
     * @param instructorId unity id of the instructor
     * @param classSize the number of seats in a class
     * @param meetingDays days on which the class meets
     * @return true if the course can be added, false if it cannot
     */
    public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int classSize, String meetingDays) {
        Course c = new Course(name, title, section, credits, instructorId, classSize, meetingDays);
        for(int i = 0; i < catalog.size(); i++) {
            if(c.isDuplicate(catalog.get(i)))
                return false;
        }
        return this.catalog.add(c);
    }
    
    /**
     * Removes the specified course from the catalog in the client. If the client isn't in the 
     * catalog, nothing is removed and the method returns false.
     * @param name the name of the course
     * @param section the section number for the course
     * @return true if the course can be and is removed, otherwise false
     */
    public boolean removeCourseFromCatalog(String name, String section) {
       for(int i = 0; i < catalog.size(); i++) {
           if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
               catalog.remove(i);
               return true;
           }
       }
        return false;
    }
    
    /**
     * Returns the specified course from the catalog, if it exists.
     * Searches the catalog for a course with a matching name and section. If it exists, the method 
     * returns that class. Otherwise, it returns null.
     * @param name name of the course
     * @param section section number for the course
     * @return the specified course if it exists in the catalog, or null if it doesn't
     */
    public Course getCourseFromCatalog(String name, String section) {
        for(int i = 0; i < catalog.size(); i++) {
            if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
                return catalog.get(i);
            }
        }
        return null;
    }

    /**
     * Returns a 2D array of the course catalog.
     * The catalog is arranged with each row being a different course, and the columns
     * going in the order of name, section, title, and meeting string.
     * @return 2D array representing course catalog
     */
    public String[][] getCourseCatalog() {
        String[][] catalogArray = new String[catalog.size()][5];
        for(int i = 0; i < catalog.size(); i++) {
            catalogArray[i][0] = catalog.get(i).getName();
            catalogArray[i][1] = catalog.get(i).getSection();
            catalogArray[i][2] = catalog.get(i).getTitle();
            catalogArray[i][3] = catalog.get(i).getMeetingString();
            catalogArray[i][4] = "" + catalog.get(i).getCourseRoll().getEnrollmentCap();
        }
        return catalogArray;
    }
    
    /**
     * Saves the current Course Catalog to the disk.
     * The catalog will be saved as a text file, in the directory specified by the filename.
     * @param fileName name of the file to save the catalog under
     * @throws IllegalArgumentException thrown if file cannot be written to
     */
    public void saveCourseCatalog(String fileName) {
        try {
            CourseRecordIO.writeCourseRecords(fileName, catalog);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
