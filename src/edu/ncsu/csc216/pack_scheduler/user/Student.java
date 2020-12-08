package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * A class that represents a student at NCSU.
 * @author Jeremy Ignatowitz
 * @author Ryan Hurlbut
 */
public class Student extends User implements Comparable<Student> {
	/** Maximum credits this student can take */
	private int maxCredits;
	
	/** Creates a new Schedule object for this student, this will be the student's schedule */
	private Schedule schedule = new Schedule();
	
	/** The maximum number of credits that a student should be allowed to take */
	public final static int MAX_CREDITS = 18; 

	/**
	 * Constructor for the student class. Sets the first and last name, as well as ID number, 
	 * email, hashed password, and maximum credit hours for the student.
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param id student's id number
	 * @param email student's email address
	 * @param hashPW student's password, already hashed
	 * @param maxCredits student's maximum credit hours
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
	}
	/**
	 * Constructor for the student class. Sets the first and last name, as well as ID number, 
	 * email, and hashed password for the student. Assumes that the max number of credits is the default 18.
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param id student's id number
	 * @param email student's email address
	 * @param hashPW student's password, already hashed
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}
	
	/**
	 * Get the student's maximum number of credit hours
	 * @return student's maximum number of credit hours
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Set the student's maximum number of credit hours to the entered integer between 3 and 18
	 * @param maxCredits maximum number of credit hours the student can take
	 * @throws IllegalArgumentException if the number of credits is greater than 18 or less than 3
	 */
	public void setMaxCredits(int maxCredits) {
		if(maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	@Override
	/**
	 * Returns a string with all personal information for the given student.
	 * @return a string with the student's information
	 */
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + 
				maxCredits;
	}
	
	/**
	 * Compares the current Student object to the one passed in.
	 * Students are compared by last name, first name, and Unity ID, in that order.
	 * If the strings aren't the same, they'll be scanned for the first different character.
	 * The integer returned will be the int value of the character in this Student's particular 
	 * string, minus the int value of the passed-in Student's string.
	 * Therefore, a Student will be "greater than" another, and the method will return a positive 
	 * number, if the current Student's last name, first name, or ID comes before the given one's 
	 * in the alphabet.
	 * If all three strings are the same, the method returns zero.
	 * @param s the student object to be compared to the current one
	 * @return an integer representing how one Student comes before or after another; 0 if the two are the same
	 */
    @Override
    public int compareTo(Student s) {
        String otherLastName = s.getLastName();
        String thisLastName = this.getLastName();
        String otherFirstName = s.getFirstName();
        String thisFirstName = this.getFirstName();
        String otherId = s.getId();
        String thisId = this.getId();
        if(!otherLastName.equals(thisLastName)) {
            for(int i = 0; i < otherLastName.length() && i < thisLastName.length(); i++) {
                if(!(otherLastName.charAt(i) == thisLastName.charAt(i))) {
                    return (int)(thisLastName.charAt(i)) - (int)(otherLastName.charAt(i));
                }
            }
        }
        if(!otherFirstName.equals(thisFirstName)) {
            for(int i = 0; i < otherFirstName.length() && i < thisFirstName.length(); i++) {
                if(!(otherFirstName.charAt(i) == thisFirstName.charAt(i))) {
                    return (int)(thisFirstName.charAt(i)) - (int)(otherFirstName.charAt(i));
                }
            }
        }
        if(!otherId.equals(thisId)) {
            for(int i = 0; i < otherId.length() && i < thisId.length(); i++) {
                if(!(otherId.charAt(i) == thisId.charAt(i))) {
                    return (int)(thisId.charAt(i)) - (int)(otherId.charAt(i));
                }
            }
        }
        return 0;
    }
    
    /**
     * Method used to get and return the schedule variable associated with a Student
     * @return the Schedule associated with a Student
     */
    public Schedule getSchedule() {    	    	
    	return this.schedule;
    }
    
    
    /**
     * Creates hashcode for Student object
     * 
     * @return int the hashed Student object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + maxCredits;
        return result;
    }
    
    /**
     * Checks this object verses the object being compared
     * 
     * @param obj the object being compared
     * @return boolean whether or not Objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (maxCredits != other.maxCredits)
            return false;
        return true;
    }
    
    /**
     * Checks if a course can be added to the student's schedule.
     * A course can be added if it's not null, not a duplicate, and not conflicting with another class, and only if it would not exceed a student's
     * maximum credit hours.
     * @param c the course to check if it can be added
     * @return false if the course cannot be added, otherwise true
     */
    public boolean canAdd(Course c) {
        if(!schedule.canAdd(c)) {
            return false;
        }
        
        if(c.getCredits() + schedule.getScheduleCredits() > maxCredits) {
            return false;
        }
        
        return true;
    }
}
