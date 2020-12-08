/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * An interface used to check conflicts between two activities.
 * Two activities are considered conflicting if they occur at the same time on the same day, even 
 * if the overlap is as small as a minute.
 * @author Jeremy Ignatowitz
 */
public interface Conflict {

    /**
     * Checks to see if the passed-in activity is conflicting with the one calling this method.
     * Two activities conflict if they occur at the same time on the same day, meaning
     * the start time of one is before the end time of the other or vice versa.
     * @param possibleConflictingActivity an activity that might have a time conflict with this one
     * @throws ConflictException if the two activities have a time conflict
     */
    void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
