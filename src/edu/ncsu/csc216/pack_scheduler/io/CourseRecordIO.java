package edu.ncsu.csc216.pack_scheduler.io;

import java.io.*;
import java.util.*;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Reads course info from a text file, and writes a set of courses to a file.
 * @author Jeremy Ignatowitz
 */
public class CourseRecordIO {

    /**
     * Reads in a file, and generates a list of valid courses.
     * A course is valid if it has every field covered; name, title, section, instructor, credits,
     * meeting days, and start and end times.
     * Code given in the guided project.
     * @author Sarah Heckman
     * @param fileName name of file to read courses from
     * @return an ArrayList of valid courses
     * @throws FileNotFoundException if the specified file doesn't exist
     */
    public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileInputStream(fileName));
        SortedList<Course> courses = new SortedList<Course>();
        while (fileReader.hasNextLine()) {
            try {
                Course course = readCourse(fileReader.nextLine());
                boolean duplicate = false;
                for (int i = 0; i < courses.size(); i++) {
                    Course c = courses.get(i);
                    if (course.getName().equals(c.getName()) &&
                            course.getSection().equals(c.getSection())) {
                        //it's a duplicate
                        duplicate = true;
                    }
                }
                if (!duplicate) {
                    courses.add(course);
                }
            } catch (IllegalArgumentException e) {
                //skip the line
            }
        }
        fileReader.close();
        return courses;
    }
    
    /**
     * TODO test this: broken right now
     * Reads course data from a line in a file containing courses, and constructs a Course object for it.
     * Extracts all relevant field data, or determines if a line doesn't contain a course.
     * @param nextLine the line to be read for course data
     * @return a Course object with the proper course data
     * @throws IllegalArgumentException thrown if cannot read from course from line or if meeting
     * days is invalid
     */
    private static Course readCourse(String nextLine) {
        //I probably have some redundant code in here but it *works* and that's enough for me!
        Course course;
        Scanner tokenizer = new Scanner(nextLine);
        tokenizer.useDelimiter(",");
        String name;
        String title;
        String section;
        int credits;
        String instructorId;
        int enrollmentCap;
        String meetingDays;
        int startTime = 0;
        int endTime = 0;
        try {
            name = tokenizer.next();
            title = tokenizer.next();
            section = tokenizer.next();
            credits = Integer.parseInt(tokenizer.next());
            instructorId = tokenizer.next();
            enrollmentCap = Integer.parseInt(tokenizer.next());
            meetingDays = tokenizer.next();
            if(tokenizer.hasNext()) {
                if(meetingDays.equals("A")) {
                    tokenizer.close();
                    throw new IllegalArgumentException();
                }
                else {
                    startTime = Integer.parseInt(tokenizer.next());
                    endTime = Integer.parseInt(tokenizer.next());
                }
            }
        }
        catch(NoSuchElementException e) {
            throw new IllegalArgumentException();
        }
            
        tokenizer.close();
        try { //TODO note cahnges the construcotrs from instructorId spot which was null to instructorId
            if(!meetingDays.equals("A")) {
            	//String name, String title, String section, int credits, String instructorId, int classSize, String meetingDays,
    			//int startTime, int endTime
            	
                course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
            }
            else {//for "A" meetingday
            	//String name, String title, String section, int credits, String instructorId, int classSize, String meetingDays
                if(startTime == 0 && endTime == 0) {
                    course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays);
                }
                else
                    throw new IllegalArgumentException();
            }
                
        }
        
        catch(Exception e) {
            throw new IllegalArgumentException();
        }
        Faculty check = null;
        check = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
        if (check != null) {
        	//TODO something with addCourse to Schedule is finding a duplicate somwhere for sesmith5 in course_record.txt
        	RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId).getSchedule().addCourseToSchedule(course);
        }
        return course;
    }
    
    /**
     * Writes an ArrayList of courses to an external file.
     * @param fileName the file to be written to
     * @param courses the ArrayList of courses to write onto the file
     * @throws IOException if the file to write to cannot be created or written to
     */
    public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
        PrintStream fileWriter = new PrintStream(new File(fileName));
    
        for (int i = 0; i < courses.size(); i++) {
            fileWriter.println(courses.get(i).toString());
        }
    
        fileWriter.close();
    
    }
    
}
