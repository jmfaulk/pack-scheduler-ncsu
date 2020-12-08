package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * A class that represents a course that a student can take. Provides
 * information about course name and title, section, instructor, credit hour
 * value, and meeting days and times.
 * 
 * @author Jeremy Ignatowitz
 * @author Ethan Taylor
 */

public class Course extends Activity implements Comparable<Course> {
    
    /** maximum length of the Section string */
	private static final int SECTION_LENGTH = 3;
	/** max number of credit hours for a course */
	private static final int MAX_CREDITS = 5;
	/** min number of credit hours for a course */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** CourseRoll object for a course */
	private CourseRoll roll;
	/**
	 * Constructs a course object with the given name, title, section number, number of credits, 
	 * instructor's unity ID, meeting days, and start and end time.
	 * 
	 * @param name         name of the course
	 * @param title        title of the course
	 * @param section      section of the course
	 * @param credits      credits for the course
	 * @param instructorId ID of the instructor
	 * @param meetingDays  days on which the course meets
	 * @param startTime    time at which the course starts
	 * @param endTime      time at which the course ends
	 * @param classSize    maximum number of students for a section
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int classSize, String meetingDays,
			int startTime, int endTime) {
	    super(title, meetingDays, startTime, endTime);
	    setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, classSize);
	}

    /**
	 * Creates a course with the given name, title, section, number of credits,
	 * instructor, and meeting days, for courses that are arranged.
	 * 
	 * @param name         name of the course
	 * @param title        title of the course
	 * @param section      section for the course
	 * @param credits      number of credits provided
	 * @param instructorId ID for the instructor
	 * @param meetingDays  days on which the course meets
	 * @param classSize    maximum number of students in a course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int classSize, String meetingDays) {
		this(name, title, section, credits, instructorId, classSize, meetingDays, 0, 0);
	}

	/**
	 * Returns the name of the course
	 * 
	 * @return the name of the course
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the course
	 * 
	 * @param name name of the course
	 * @throws IllegalArgumentException if course name is invalid
	 */
	private void setName(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		
	    CourseNameValidator validator = new CourseNameValidator();
		try {
            if (validator.isValid(name)) {
            	this.name = name;
            } else {
            	throw new IllegalArgumentException("Invalid course name");
            }
        } catch (InvalidTransitionException e) {
            throw new IllegalArgumentException("Invalid course name");
        }
	}

	/**
	 * Returns the section of the course
	 * 
	 * @return the section of the course
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Set the section number of the course
	 * @param section the section number of the course
	 * @throws IllegalArgumentException if course section number is invalid
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException("Invalid section number");
		}
		if (section.length() != 3) {
			throw new IllegalArgumentException("Invalid section number");
		}
		for (int i = 0; i < SECTION_LENGTH; i++) {
			if (!Character.isDigit(section.charAt(0))) {
				throw new IllegalArgumentException("Invalid section number");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the number of credits the course provides
	 * @return the number of credits the course provides
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Set the number of credits the course provides
	 * 
	 * @param credits the number of credits the course provides
	 * @throws IllegalArgumentException thrown if course credits is out of range of valid credits
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}

	/**
	 * Return the ID of the instructor
	 * 
	 * @return the ID of the instructor
	 */
	public String getInstructorId() {
	    if(instructorId != null && instructorId.equals("null")) {
	        return null;
	    }
		return instructorId;
	}

	/**
	 * Set the ID of the instructor
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException thrown if instructorId is invalid
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null) {
		    this.instructorId = null;
		}
		else {
		    if(instructorId.length() == 0) {
		        throw new IllegalArgumentException("Invalid instructor unity id");
		    }
		    else if(instructorId.equals("null")) {
		        this.instructorId = null;
		    }
		    this.instructorId = instructorId;
		}
		
	}

	/**
	 * Method which retrieves the CourseRoll associated with a Course object
	 * @return the CourseRoll associated with a Course
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
	
	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (getMeetingDays().equals("A")) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + 
			        getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() 
		    + "," + getStartTime() + "," + getEndTime();
	}
	
	/**
     * Generates a hashcode for this object, based on the values of its fields.
     * @return an integer hashcode for Course objects
     */
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + credits;
        result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((section == null) ? 0 : section.hashCode());
        return result;
    }

	/**
     * Determines whether two objects of type Activity are equal.
     * Objects are equal if their fields have the same value.
     * @return true if the two Activities are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        if (credits != other.credits)
            return false;
        if (instructorId == null) {
            if (other.instructorId != null)
                return false;
        } else if (!instructorId.equals(other.instructorId))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (section == null) {
            if (other.section != null)
                return false;
        } else if (!section.equals(other.section))
            return false;
        return true;
    }

    /**
     * Generates a short array of data about an activity, to use for display purposes.
     * @return array of activity data
     */
    @Override
    public String[] getShortDisplayArray() {
        String[] display = new String[5];
        display[0] = getName();
        display[1] = getSection();
        display[2] = getTitle();
        display[3] = getMeetingString();
        display[4] = "" + roll.getOpenSeats();
        return display;
    }

    /**
     * Generates a long array of data about an activity, to use for display purposes.
     * @return array of activity data
     */
    @Override
    public String[] getLongDisplayArray() {
        String[] display = new String[7];
        display[0] = getName();
        display[1] = getSection();
        display[2] = getTitle();
        display[3] = "" + getCredits();
        display[4] = getInstructorId();
        display[5] = getMeetingString();
        display[6] = "";
        return display;
    }
    
    /**
     * Set the days on which the course meets
     * 
     * @param meetingDays the meetingDays to set
     * @throws IllegalArgumentException thrown if meeting days are invalid
     */
    public void setMeetingDays(String meetingDays) {
        if (meetingDays == null) {
            throw new IllegalArgumentException("Invalid meeting days");
        }
        if (meetingDays.length() == 0) {
            throw new IllegalArgumentException("Invalid meeting days");
        }
        for (int i = 0; i < meetingDays.length(); i++) {
            if (!(meetingDays.charAt(i) == 'M' || meetingDays.charAt(i) == 'T' || meetingDays.charAt(i) == 'W'
                    || meetingDays.charAt(i) == 'H' || meetingDays.charAt(i) == 'F' || meetingDays.charAt(i) == 'A')) {
                throw new IllegalArgumentException("Invalid meeting days");
            }
        }
        if (meetingDays.contains("A") && meetingDays.length() > 1) {
            throw new IllegalArgumentException("Invalid meeting days");
        }
        super.setMeetingDays(meetingDays);
    }
    
    /**
     * Checks if the entered activity is a course, and if so, if it's a duplicate of the current course.
     * @param activity the activity to be checked for duplicity
     * @return true if the given activity is a duplicate of the current course
     */
    public boolean isDuplicate(Activity activity) {
        if(getClass() != activity.getClass()) {
            return false;
        }
        Course other = (Course)activity;
        if(other.getName().equals(getName())) {
        		return true;
        }
        
        return false;
    }

    /**
     * Compares self to another Course object
     * 
	 * @param c the object being compared
     * @return int char difference at index where course name or section doesn't match
     */
    @Override
    public int compareTo(Course c) {
        String thisName = this.getName();
        String thisSection = this.getSection();
        String otherName = c.getName();
        String otherSection = c.getSection();
        if(!thisName.equals(otherName)) {
            for(int i = 0; i < thisName.length() && i < otherName.length(); i++) {
                if(thisName.charAt(i) != otherName.charAt(i)) {
                    return (int)(thisName.charAt(i) - otherName.charAt(i));
                }
            }
        }
        
        if(!thisSection.equals(otherSection)) {
            for(int i = 0; i < thisSection.length() && i < otherSection.length(); i++) {
                if(thisSection.charAt(i) != otherSection.charAt(i)) {
                    return (int)(thisSection.charAt(i) - otherSection.charAt(i));
                }
            }
        }
        
        return 0;
    }
}
