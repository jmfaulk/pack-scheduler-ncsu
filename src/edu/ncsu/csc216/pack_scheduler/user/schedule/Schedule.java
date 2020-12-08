package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Schedule object that holds a list of Course objects allowing adding and removing of Courses
 * 
 * @author Ethan Taylor
 * @author Gage Fringer
 * @author Jeremy Ignatowitz
 */
public class Schedule {

	/** Title of schedule */
	String title;
	/** List of courses in schedule */
	ArrayList<Course> schedule;
	
	/**
	 * Creates a default schedule object with an empty schedule and title of "My Schedule"
	 */
	public Schedule() {
		setTitle("My Schedule");
		resetSchedule();
	}

	/**
	 * Adds a Course object to the schedule ArrayList
	 * 
	 * @param course course to add to schedule
	 * @return boolean whether adding course was successful
	 * @throws IllegalArgumentException thrown if course is already in schedule
	 * or if there is a conflict in meeting days with a course in schedule
	 */
	public boolean addCourseToSchedule(Course course) {
		for (Course c : schedule) {
			if (c.equals(course) || c.getName().equals(course.getName())) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			}

			try {
				c.checkConflict(course);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		return schedule.add(course);
	}
	
	/**
	 * Removes a Course object from the schedule ArrayList
	 * 
	 * @param course course to remove from schedule
	 * @return boolean whether removing course was successful
	 */
	public boolean removeCourseFromSchedule(Course course) {
		if(course == null) {
			return false;
		}
		
		int index = 0;
		Course filler = null;
		for(int i = 0; i < schedule.size(); i++) {
			if(this.schedule.get(i).getName().equals(course.getName())) {
				index = i;
				filler = this.schedule.remove(index);
			}
		}
		
		if(filler != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sets schedule to an empty ArrayList
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
	}
	
	/**
	 * Returns schedule as a 2D String array
	 * 
	 * @return String[][] of schedule
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][5];
		
		for (int i = 0; i < schedule.size(); i++) {
			scheduleArray[i] = schedule.get(i).getShortDisplayArray();
		}
		return scheduleArray;
	}
	
	/**
	 * Sets Schedule title to specified title
	 * 
	 * @param title title to be set for Schedule
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}
	
	/**
	 * Returns Schedule's title
	 * 
	 * @return String of title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the number of credit hours in the schedule.
	 * @return the number of credit hours in the schedule
	 */
	public int getScheduleCredits() {
	    int credits = 0;
	    for(Course c : schedule) {
	        credits += c.getCredits();
	    }
	    return credits;
	}
	
	/**
	 * Tests whether the given course can be added to the schedule.
	 * A course can be added if it's not null, not already in the schedule, and not conflicting with another course.
	 * @param course the course to check if it can be added
	 * @return false if the class cannot be added, otherwise true
	 */
	public boolean canAdd(Course course) {
	    if(course == null) {
	        return false;
	    }
	     
	    for(Course c : schedule) {
	        if(c.getName().equals(course.getName())) {
	            return false;
	        }
	        
	        try {
	            c.checkConflict(course);
	        }
	        catch(ConflictException e) {
	            return false;
	        }
	    }
	    return true;
	    
	}
}
