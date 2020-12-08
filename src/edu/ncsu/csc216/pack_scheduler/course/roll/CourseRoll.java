/**
* 
*/
package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Handles enrolling and dropping students from a course.
 * 
 * @author Ethan Taylor
 * 
 */
public class CourseRoll {

	/** Students enrolled in the course */
	private LinkedAbstractList<Student> roll;
	/** Waitlist for enrolling into course */
	private ArrayQueue<Student> waitList;
	/** Students allowed in the course */
	private int enrollmentCap;
	/**
	 * Minimum students to be enrolled in any course; minimum value of enrollmentCap
	 */
	private static final int MIN_ENROLLMENT = 10;
	/**
	 * Maximum students to be enrolled in any course; maximum value of enrollmentCap
	 */
	private static final int MAX_ENROLLMENT = 250;

	/**
	 * Constructs a CourseRoll for a course. Declares roll as an empty
	 * LinkedAbstractList. Sets the enrollmentCap.
	 * 
	 * @param enrollmentCap the maximum number of students allowed in the course
	 * @param c             Course course to pass in
	 */
	public CourseRoll(Course c, int enrollmentCap) {
	    this.waitList = new ArrayQueue<Student>(10);
		if (c == null)
			throw new IllegalArgumentException("Invalid course");
		roll = new LinkedAbstractList<Student>(MAX_ENROLLMENT);
		setEnrollmentCap(enrollmentCap);
//		this.waitList.setCapacity(10); // default capacity is 10
	}

	/**
	 * Returns the enrollmentCap
	 * 
	 * @return the enrollmentCap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets the enrollmentCap
	 * 
	 * @param enrollmentCap the value to be set to enrollmentCap
	 * @throws IllegalArgumentException if enrollment cap is not within min and max
	 *                                  enrollment bounds of 10 to 250
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap >= MIN_ENROLLMENT && enrollmentCap <= MAX_ENROLLMENT) {
			if (roll != null && enrollmentCap < roll.size()) {
				throw new IllegalArgumentException(
						"Enrollment cap cannot be less than number of students already enrolled");
			} else {
				this.enrollmentCap = enrollmentCap;
			}
		} else {
			throw new IllegalArgumentException("Enrollment cap is not between 10 and 250");
		}
		if (roll != null) {
			roll.setCapacity(enrollmentCap);
		}
	}

	/**
	 * Adds a student to the course roll
	 * 
	 * @param student the student to enroll
	 * @throws IllegalArgumentException if student is null, course if full or
	 *                                  student is already enrolled, or if there was
	 *                                  a problem when adding student to the
	 *                                  LinkedAbstractList roll
	 */
	public void enroll(Student student) {
		if (student == null) {
			throw new IllegalArgumentException("Student can't be null");
		} else if (!canEnroll(student)) {
			throw new IllegalArgumentException("The course cannot be added");
		} else if (roll.size() >= getEnrollmentCap()) {
            waitList.enqueue(student);
		} else {
			try {
				roll.add(roll.size(), student);
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}
	}

	/**
	 * Removes a student from the course roll
	 * 
	 * @param student the student to drop
	 * @throws IllegalArgumentException thrown if student is null, or whatever
	 *                                  exception remove() might throw
	 */
	public void drop(Student student) {
		if (student == null) {
			throw new IllegalArgumentException("Student can't be null");
		}
		if (waitList.contains(student)) {
			ArrayQueue<Student> wL = new ArrayQueue<Student>(10); //capcity 10
			for (int i = 0; i < waitList.size(); i++) {
				Student check = waitList.dequeue(); //dummy var to check
				if (check.compareTo(student) != 0) { //check if waitlisted student is not empty 
					wL.enqueue(check);
				}
			}
			waitList = wL;
			return;
		}
		try {
		    for(int i = 0; i < roll.size(); i++) {
		        if(roll.get(i).equals(student)) {
		            roll.remove(i);
		            if(!waitList.isEmpty()) {
		                roll.add(waitList.dequeue());
		            }
		            break;
		        }
		    }
			
			
		} catch (UnsupportedOperationException e) {
			throw new IllegalArgumentException("Student was not found");
		}
	}

	/**
	 * Returns the number of open seats in the course
	 * 
	 * @return the open seats in the course, calculated by subtracting number of
	 *         students in the course from the enrollmentCap
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/** Returns the number of students on the waitlist
	 * 
	 * @return Returns the number of students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitList.size();
	}
	
	/**
	 * Checks if student can enroll
	 * 
	 * @param student the student to be checked if they can enroll
	 * @return boolean whether student can be enrolled
	 */
	public boolean canEnroll(Student student) {
		for (int i = 0; i < roll.size(); i++) {
			if (student.compareTo(roll.get(i)) == 0) {
				return false;
			}
		}
		if (waitList.size() < 10) {
			ArrayList<Student> wL = waitList.getArrayQueue();
			for (int i = 0; i < wL.size(); i++) {
				if (student.compareTo(wL.get(i)) == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Builds and returns a 2D array of all of the students in a course's roll. The array contains first name, last name, and ID, of each student.
	 * @return array of all students in the roll
	 */
	public String[][] getRoll() {
	    String[][] students = new String[roll.size()][3];
	    for(int i = 0; i < roll.size(); i++) {
	        students[i][0] = roll.get(i).getFirstName();
	        students[i][1] = roll.get(i).getLastName();
	        students[i][2] = roll.get(i).getId();
	    }
	    
	    return students;
	}

}
