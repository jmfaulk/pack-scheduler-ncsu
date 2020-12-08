/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/** Represents and individual faculty record. 
 * 
 * @author Sumit Biswas
 * @author Jared Faulk
 * @author Jeremy Ignatowitz
 *
 */
public class Faculty extends User {
	
	/** Constant for minimum number of courses a Faculty member can teach. */
	private static final int MIN_COURSES = 1;
	/** Constant for maximum number of courses a Faculty member can teach. */
	private static final int MAX_COURSES = 3;

	/** Max number of courses a Faculty member can teach. */
	private int maxCourses;
	/** Schedule for the faculty member. */
	private FacultySchedule schedule;
	
	/** Constructor that constructs a Faculty with values for all fields.
	 * 
	 * @param firstName is the first name
	 * @param lastName is the last name
	 * @param id is the user id
	 * @param email is the user email
	 * @param password is the user's password
	 * @param maxCourses is the max number of courses a faculty member can teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		schedule = new FacultySchedule(id);
		setMaxCourses(maxCourses);
	}
	
	/** Method that sets the max number of courses a faculty member can teach.
	 * 
	 * @param maxCourses is the max number of courses a faculty member can teach
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}
	
	/**Accessor method for maxCourses
	 * @return returns the maximum number of courses a Faculty member can teach
	 */
	public int getMaxCourses() {
		return maxCourses;
	}
	
	/** Returns the schedule for the faculty member
	 * 
	 * @return Returns the schedule for the faculty member
	 */
	public FacultySchedule getSchedule() { 
		return schedule;
	}
	
	/** Returns if number of scheduled courses is greater than faculty's max number of courses
	 * 
	 * @return returns if number of scheduled courses is greater than faculty's max number of courses
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > this.maxCourses;
	}

	/** Generates a hash code for each Faculty member
	 * @return returns the generated hash code 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/** Equals method that checks if this Faculty and another Faculty object are the same
	 * @param obj is the other Faculty object
	 * @return returns a boolean to indicate the result
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}
	
	/** String representation of a Faculty.
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + 
				getMaxCourses();
	}
}


