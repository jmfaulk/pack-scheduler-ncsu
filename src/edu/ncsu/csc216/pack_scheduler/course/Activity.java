package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A class representing an activity that a student can put onto their schedule.
 * @author Jeremy Ignatowitz
 */
public abstract class Activity implements Conflict {

    /** upper limit to the time for an activity; an activity can't start or end after 2359 */
    private static final int UPPER_TIME = 2400;
    /** upper limit for an hour; times can't have '60' in the minutes slots */
    private static final int UPPER_HOUR = 60;
    /** title for the activity */
    private String title;
    /** days on which the activity meets or occurs */
    private String meetingDays;
    /** time at which the activity starts */
    private int startTime;
    /** time at which the activity ends */
    private int endTime;

    /**
     * Constructs an Activity object, with the given title, days, and start and end times.
     * @param title title of the activity
     * @param meetingDays days on which the activity meets or occurs
     * @param startTime the time at which the activity starts
     * @param endTime the time at which the activity ends
     */
    public Activity(String title, String meetingDays, int startTime, int endTime) {
        setTitle(title);
        setMeetingDays(meetingDays);
        setActivityTime(startTime, endTime);
    }
    
    /**
     * Generates a short array of data about an activity, to use for display purposes.
     * @return array of activity data
     */
    public abstract String[] getShortDisplayArray();

    /**
     * Generates a long array of data about an activity, to use for display purposes.
     * @return array of activity data
     */
    public abstract String[] getLongDisplayArray();

    /**
     * Returns the title of the activity
     * @return the title of the activity
     */
    public String getTitle() {
    	return title;
    }

    /**
     * Set the title of the activity.
     * @param title the title of the activity
     * @throws IllegalArgumentException thrown if course title is invalid
     */
    public void setTitle(String title) {
    	if (title == null || title.length() == 0) {
    		throw new IllegalArgumentException("Invalid course title");
    	} else {
    		this.title = title;
    	}
    }

    /**
     * Returns the days on which the activity meets
     * @return the days on which the activity meets
     */
    public String getMeetingDays() {
    	return meetingDays;
    }

    /**
     * Sets the days on which the activity meets
     * @param meetingDays the days on which the activity meets
     */
    public void setMeetingDays(String meetingDays) {
        this.meetingDays = meetingDays;
    }

    /**
     * Returns the time at which the activity starts
     * @return the time at which the activity starts
     */
    public int getStartTime() {
    	return startTime;
    }

    /**
     * Returns the time at which the activity ends
     * @return the time at which the activity ends
     */
    public int getEndTime() {
    	return endTime;
    }

    /**
     * Sets the start and end time for an activity.
     * @param startTime time at which the activity starts
     * @param endTime   time at which the activity ends
     * @throws IllegalArgumentException thrown if course times are invalid
     */
    public void setActivityTime(int startTime, int endTime) {
    	if (startTime < 0 || startTime >= UPPER_TIME || (startTime % 100) >= UPPER_HOUR) {
    		throw new IllegalArgumentException("Invalid course times");
    	}
    	if (endTime < 0 || endTime >= UPPER_TIME || (endTime % 100) >= UPPER_HOUR) {
    		throw new IllegalArgumentException("Invalid course times");
    	}
    	if (endTime < startTime) {
    		throw new IllegalArgumentException("Invalid course times");
    	}
    	if (meetingDays.equals("A") && (startTime != 0 || endTime != 0)) {
    		throw new IllegalArgumentException("Invalid course times");
    	}
    	this.startTime = startTime;
    	this.endTime = endTime;
    }

    /**
     * Generates a hashcode for this object, based on the values of its fields.
     * Uses a prime number and multiplies it by some number, starting with 1 and adding either the 
     * values of numerical fields, or the hashcodes of strings or other objects.
     * @return an integer hashcode for Activity objects
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + endTime;
        result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
        result = prime * result + startTime;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Activity other = (Activity) obj;
        if (endTime != other.endTime)
            return false;
        if (meetingDays == null) {
            if (other.meetingDays != null)
                return false;
        } else if (!meetingDays.equals(other.meetingDays))
            return false;
        if (startTime != other.startTime)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    /**
     * Returns a String that gives the details about when an activity meets. The days are followed
     * by the time, using single letters for days and 12-hour-based timestamps for times.
     * @return class meeting days and times
     */
    public String getMeetingString() {
    	String meet = "";
    	if (getMeetingDays().equals("A")) {
    		return "Arranged";
    	}
    	int start = getStartTime();
    	int end = getEndTime();
    	String startTimeString;
    	String endTimeString;
    	if (start < 1200) {
    		startTimeString = "" + (start / 100) + ":" + (start % 100);
    		if (start % 100 == 0)
    			startTimeString += "0";
    		startTimeString += "AM";
    	} else if(start >= 1200 && start < 1300) {
    		startTimeString = "" + (start / 100) + ":" + start % 100;
    		if (start % 100 == 0)
    			startTimeString += "0";
    		startTimeString += "PM";
    	}
    	else {
            startTimeString = "" + (start / 100 - 12) + ":" + start % 100;
            if (start % 100 == 0)
                startTimeString += "0";
            startTimeString += "PM";
    	}
    	if (end < 1200) {
    		endTimeString = "" + (end / 100) + ":" + (end % 100);
    		if (end % 100 == 0)
    			endTimeString += "0";
    		endTimeString += "AM";
    	} else if(end >= 1200 && end < 1300) {
    		endTimeString = "" + (end / 100) + ":" + end % 100;
    		if (end % 100 == 0)
    			endTimeString += "0";
    		endTimeString += "PM";
    	}
    	else {
    	    endTimeString = "" + (end / 100 - 12) + ":" + end % 100;
            if (end % 100 == 0)
                endTimeString += "0";
            endTimeString += "PM";
    	}
    	meet += getMeetingDays() + " ";
    	meet += startTimeString + "-";
    	meet += endTimeString;
    	return meet;
    }
    
    /**
     * Determines whether or not a passed-in activity is a duplicate of another.
     * @param activity the activity to check for duplicity
     * @return true if the passed-in activity is a duplicate of the calling activity
     */
    public abstract boolean isDuplicate(Activity activity);

    /**
     * Checks to see if two activities have a time conflict.
     * Activities are considered to have a conflict if they occur on the same day, and they're at
     * the same time, or there is some overlap in their times. This includes if the ending time for 
     * one is the start time for the other.
     * @param possibleConflictingActivity the activity that might conflict with this current activity
     * @throws ConflictException if the two activities are conflicting (happen at overlapping times on a day)
     */
    @Override
    public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
        for(int i = 0; i < this.getMeetingDays().length(); i++) {
            char thisDay = this.getMeetingDays().charAt(i);
            for(int j = 0; j < possibleConflictingActivity.getMeetingDays().length(); j++) {
                char conflictDay = possibleConflictingActivity.getMeetingString().charAt(j);
                if(thisDay != 'A' && conflictDay != 'A' && thisDay == conflictDay && 
                        /* PMD keeps saying that there's a useless set of parentheses here, but 
                         * without them the && statement will short circuit without properly 
                         * evaluating the big || statement below this comment. */
                            ((this.getEndTime() >= possibleConflictingActivity.getStartTime() && 
                            this.getStartTime() <= possibleConflictingActivity.getStartTime()) ||
                            possibleConflictingActivity.getEndTime() >= this.getStartTime() &&
                            possibleConflictingActivity.getStartTime() <= this.getStartTime())) {
                    throw new ConflictException();
                }
            }
        }
    }

}