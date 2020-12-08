package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A suite of tests for the Activity class. Specifically, the checkConflict method.
 * Contains a method that tests different conflicts for activities.
 * Activities are considered conflicting if they occur at the same time on the same day,
 * or one starts before (or just as) the other ends.
 * @author Jeremy Ignatowitz
 */
public class ActivityTest {

    /**
     * Tests the checkConflict method in Activity.
     * Two activities have a conflict if they happen at the same time on the same day, or one 
     * starts before (or just as) the other ends.
     */
    @Test
    public void testCheckConflict() {
        Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "MW", 1330, 1445);
        Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "TH", 1330, 1445);
        try {
            a1.checkConflict(a2);
            assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
            assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM", a2.getMeetingString());
        } catch (ConflictException e) {
            fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
        }
        
        try {
            a2.checkConflict(a1);
            assertEquals("Incorrect meeting string for this Activity.", "TH 1:30PM-2:45PM", a2.getMeetingString());
            assertEquals("Incorrect meeting string for possibleConflictingActivity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
        }
        catch(ConflictException e) {
            fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
        }
        
      //Update a1 with the same meeting days and a start time that overlaps the end time of a2
        a1.setMeetingDays("TH");
        a1.setActivityTime(1445, 1530);
        try {
            a1.checkConflict(a2);
            fail(); //ConflictException should have been thrown, but was not.
        } catch (ConflictException e) {
            //Check that the internal state didn't change during method call.
            assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
            assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
        }
        
        a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "M", 1330, 1445);
        a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "M", 1400, 1500);
        
        try {
            a1.checkConflict(a2);
            fail();
        }
        catch (ConflictException e) {
            assertEquals("M 1:30PM-2:45PM", a1.getMeetingString());
            assertEquals("M 2:00PM-3:00PM", a2.getMeetingString());
        }
        
        try {
            a2.checkConflict(a1);
            fail();
        }
        catch (ConflictException e) {
            assertEquals("M 1:30PM-2:45PM", a1.getMeetingString());
            assertEquals("M 2:00PM-3:00PM", a2.getMeetingString());
        }

    }


}
