package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * A class containing the logic to build a Graphical User Interface for display a Faculty's schedule.
 * The panel displays the schedule, the details about a selected course, and the students in the selected course's roll.
 * @author Jeremy Ignatowitz
 */
public class FacultySchedulePanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    /** table for the schedule */
    private JTable tableSchedule;
    /** table for the course roll */
    private JTable tableRoll;
    /** model for the schedule table */
    private CourseTableModel scheduleTableModel;
    /** model for the roll table */
    private StudentTableModel rollTableModel;
    /** a lower-etched border */
    private Border lowerEtched;
    /** scroll pane for the schedule */
    private JScrollPane scrollSchedule;
    /** scroll pane for the roll */
    private JScrollPane scrollRoll;
    /** panel for course details */
    private JPanel pnlCourseDetails;
    /** title for course name */
    private JLabel lblNameTitle;
    /** title for course section */
    private JLabel lblSectionTitle;
    /** title for course title */
    private JLabel lblTitleTitle;
    /** title for course instructor */
    private JLabel lblInstructorTitle;
    /** title for course credit hours */
    private JLabel lblCreditsTitle;
    /** title for course meeting string */
    private JLabel lblMeetingTitle;
    /** title for course enrollment cap */
    private JLabel lblEnrollmentCapTitle;
    /** title for course open seats */
    private JLabel lblOpenSeatsTitle;
    /** title for course waitlist */
    private JLabel lblWaitListTitle;
    /** displays selected course name */
    private JLabel lblName;
    /** displays selected course section */
    private JLabel lblSection;
    /** displays selected course title */
    private JLabel lblTitle;
    /** displays selected course instructor */
    private JLabel lblInstructor;
    /** displays selected course credit hours */
    private JLabel lblCredits;
    /** displays selected course meeting string */
    private JLabel lblMeeting;
    /** displays selected course enrollment cap */
    private JLabel lblEnrollmentCap;
    /** displays selected course open seats */
    private JLabel lblOpenSeats;
    /** displays selected course waitlist */
    private JLabel lblWaitList;
    
    /** 
     * Constructs a FacultySchedulePanel by creating each component of the layout, then adding them to the layout
     */
    public FacultySchedulePanel() {
        super(new GridBagLayout());
        
        CourseCatalog catalog = RegistrationManager.getInstance().getCourseCatalog();
        
        rollTableModel = new StudentTableModel();
        tableRoll = new JTable(rollTableModel);
        tableRoll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableRoll.setPreferredScrollableViewportSize(new Dimension(250, 250));
        tableRoll.setFillsViewportHeight(true);
        
        //we wanna set things up in order... let's start with, say, the schedule table?
        scheduleTableModel = new CourseTableModel();
        tableSchedule = new JTable(scheduleTableModel);
        tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSchedule.setPreferredScrollableViewportSize(new Dimension(250, 250));
        tableSchedule.setFillsViewportHeight(true);
        tableSchedule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                String name = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 0).toString();
                String section = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 1).toString();
                Course c = catalog.getCourseFromCatalog(name, section);
                updateCourseDetails(c);
                
                rollTableModel.updateData(tableSchedule.getSelectedRow());
            }
            
        });
        
        scrollRoll = new JScrollPane(tableRoll, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        scrollSchedule = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder borderSchedule = BorderFactory.createTitledBorder(lowerEtched, "Faculty Schedule");
        scrollSchedule.setBorder(borderSchedule);
        TitledBorder borderRoll = BorderFactory.createTitledBorder(lowerEtched, "Course Roll");
        scrollRoll.setBorder(borderRoll);
        
        updateTables();
        
        //next let's build up the course details panel
        pnlCourseDetails = new JPanel(new GridLayout(5, 1));
        lblNameTitle = new JLabel("Name:");
        lblSectionTitle = new JLabel("Section:");
        lblTitleTitle = new JLabel("Title:");
        lblInstructorTitle = new JLabel("Instructor:");
        lblCreditsTitle = new JLabel("Credits:");
        lblMeetingTitle = new JLabel("Meeting:");
        lblEnrollmentCapTitle = new JLabel("Enrollment Cap:");
        lblOpenSeatsTitle = new JLabel("Open Seats:");
        lblWaitListTitle = new JLabel("Waitlist:");
        
        initCourseDetails();
        
        //This section of code was snipped and borrowed from StudentRegistrationPanel, because it served the same purpose.
        JPanel pnlName = new JPanel(new GridLayout(1, 4));
        pnlName.add(lblNameTitle);
        pnlName.add(lblName);
        pnlName.add(lblSectionTitle);
        pnlName.add(lblSection);
        
        JPanel pnlTitle = new JPanel(new GridLayout(1, 1));
        pnlTitle.add(lblTitleTitle);
        pnlTitle.add(lblTitle);
        
        JPanel pnlInstructor = new JPanel(new GridLayout(1, 4));
        pnlInstructor.add(lblInstructorTitle);
        pnlInstructor.add(lblInstructor);
        pnlInstructor.add(lblCreditsTitle);
        pnlInstructor.add(lblCredits);
        
        JPanel pnlMeeting = new JPanel(new GridLayout(1, 1));
        pnlMeeting.add(lblMeetingTitle);
        pnlMeeting.add(lblMeeting);
        
        JPanel pnlEnrollment = new JPanel(new GridLayout(1, 6));
        pnlEnrollment.add(lblEnrollmentCapTitle);
        pnlEnrollment.add(lblEnrollmentCap);
        pnlEnrollment.add(lblOpenSeatsTitle);
        pnlEnrollment.add(lblOpenSeats);
        pnlEnrollment.add(lblWaitListTitle);
        pnlEnrollment.add(lblWaitList);
        
        pnlCourseDetails.add(pnlName);
        pnlCourseDetails.add(pnlTitle);
        pnlCourseDetails.add(pnlInstructor);
        pnlCourseDetails.add(pnlMeeting);
        pnlCourseDetails.add(pnlEnrollment);
        
        TitledBorder borderCourseDetails = BorderFactory.createTitledBorder(lowerEtched, "Course Details");
        pnlCourseDetails.setBorder(borderCourseDetails);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        add(scrollSchedule, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        add(pnlCourseDetails, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.BOTH;
        add(scrollRoll, c);
    }
    
    /**
     * Initializes the label strings for a Course, by constructing new, blank JLabels 
     */
    private void initCourseDetails() {
        lblName = new JLabel("");
        lblSection = new JLabel("");
        lblTitle = new JLabel("");
        lblInstructor = new JLabel("");
        lblCredits = new JLabel("");
        lblMeeting = new JLabel("");
        lblEnrollmentCap = new JLabel("");
        lblOpenSeats = new JLabel("");
        lblWaitList = new JLabel("");
    }
    
    /**
     * Updates the values in the schedule table, and clears the selection so the same course doesn't remain selected when
     * logging in a different faculty
     */
    public void updateTables() {
        scheduleTableModel.updateData();
        tableSchedule.clearSelection();
    }
    
    /**
     * Updates the values in the course details panel with information from the given course
     * @param c the course to update values with
     */
    private void updateCourseDetails(Course c) {
        if(c != null) {
            lblName.setText(c.getName());
            lblSection.setText(c.getSection());
            lblTitle.setText(c.getTitle());
            lblInstructor.setText(c.getInstructorId());
            lblCredits.setText(Integer.toString(c.getCredits()));
            lblMeeting.setText(c.getMeetingString());
            lblEnrollmentCap.setText(Integer.toString(c.getCourseRoll().getEnrollmentCap()));
            lblOpenSeats.setText(Integer.toString(c.getCourseRoll().getOpenSeats()));
            lblWaitList.setText(Integer.toString(c.getCourseRoll().getNumberOnWaitlist()));
        }
        else {
            initCourseDetails();
        }
    }
    
    /**
     * this is only here because it needs to be 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * The object underlying the faculty's Course schedule JTable.
     * Heavily inspired by existing code in StudentRegistrationPanel, attributed to Dr. Heckman.
     * @author Sarah Heckman
     * @author Jeremy Ignatowitz
     */
    private class CourseTableModel extends AbstractTableModel {

        /** ID for serializing the class */
        private static final long serialVersionUID = 1L;
        /** an array of strings with which to title the columns */
        private String[] columnNames = { "Name", "Section", "Title", "Meeting Days", "Open Seats" };
        /** a 2D array of the data in the tables */
        private Object[][] data;
        
        /** 
         * Constructs a new CourseTableModel by updating the data in the table
         */
        public CourseTableModel() {
            updateData();
        }
        
        /**
         * Updates the data in the table. basically just pulls in the scheduled courses for the given instructor and applies 
         * the array to the table.
         */
        public void updateData() {
            Faculty currentUser = (Faculty)RegistrationManager.getInstance().getCurrentUser();
            if(currentUser != null) {
                data = currentUser.getSchedule().getScheduledCourses();
                FacultySchedulePanel.this.repaint();
                FacultySchedulePanel.this.validate();
            }
        }
        
        /**
         * returns the number of rows in the table
         * @return the number of rows in the table
         */
        public int getRowCount() {
            if(data == null) {
                return 0;
            }
            return data.length;
        }
        
        /**
         * returns the number of columns in the table
         * @return the number of columns in the table
         */
        public int getColumnCount() {
            return columnNames.length;
        }
        
        /**
         * returns the value of the data at the given row and column
         * @param row the row
         * @param col the column
         * @return object at the given row and column of the table
         */
        public Object getValueAt(int row, int col) {
            if(data == null) {
                return null;
            }
            return data[row][col];
        }
        
        /**
         * returns the name of the given column
         * @param col the column
         * @return the name of the given column
         */
        public String getColumnName(int col) {
            return columnNames[col];
        }
        
    }
    
    /**
     * The object underlying the Course roll JTable.
     * Heavily inspired by existing code in StudentRegistrationPanel, attributed to Dr. Heckman.
     * @author Sarah Heckman
     * @author Jeremy Ignatowitz
     */
    private class StudentTableModel extends AbstractTableModel {
        
        /** ID for serializing the class */
        private static final long serialVersionUID = 1L;
        /** an array of strings with which to title the columns */
        private String[] columnNames = { "First Name", "Last Name", "Student ID" };
        /** a 2D array of the data in the tables */
        private Object[][] data;
        
        /** 
         * returns the number of rows in the table
         * @return the number of rows in the table
         */
        public int getRowCount() {
            if(data == null)
                return 0;
            return data.length;
        }
        
        /**
         * returns the number of columns in the table
         * @return the number of columns in the table
         */
        public int getColumnCount() {
            return columnNames.length;
        }
        
        public Object getValueAt(int row, int col) {
            if(data == null) return null;
            return data[row][col];
        }
        
        /**
         * Updates the data in the table, by getting the students for the selected class.
         * This class is selected by the index of the classes in the faculty's schedule, passed in as i
         * @param index of the selected class
         */
        public void updateData(int i) {
            Faculty currentUser = (Faculty)RegistrationManager.getInstance().getCurrentUser();
            if(currentUser != null) {
                data = currentUser.getSchedule().getStudents(i);
                FacultySchedulePanel.this.repaint();
                FacultySchedulePanel.this.validate();
            }
        }
        
        /**
         * Returns the name of the given column
         * @return the name of the given column
         * @param col the column to get the name of
         */
        public String getColumnName(int col) {
            return columnNames[col];
        }
    }

}
