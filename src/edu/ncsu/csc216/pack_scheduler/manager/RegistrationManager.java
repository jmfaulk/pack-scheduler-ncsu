package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * A class that handles registration, and contains the CourseCatalog and StudentDirectory objects that will be used
 * as singletons throughout the PackScheduler process. Includes functionality for logging in and out, clearing the catalog
 * and directory, and returning instances of the catalog and directory.
 * @author CSC216 Teaching Staff
 * @author Gage Fringer
 * @author Jeremy Ignatowitz
 * @author Ethan Taylor
 */
public class RegistrationManager {
	
    /** the singleton instance of RegistrationManager */
	private static RegistrationManager instance;
	/** The catalog of courses used for scheduling */
	private CourseCatalog courseCatalog = new CourseCatalog();
	/** the directory of students used for scheduling */
	private StudentDirectory studentDirectory = new StudentDirectory();
	/** the directory of faculty used in the system */
	private FacultyDirectory facultyDirectory = new FacultyDirectory();
	/** the registrar; a user that can facilitate registration */
	private User registrar;
	/** the current user logged in to PackScheduler */
	private User currentUser = null;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** The properties file for Registration Manager */
	private static final String PROP_FILE = "registrar.properties";
	

	/**
	 * Constructs a new RegistrationManager object by creating a registrar to handle registration.
	 * Private constrcutor to match the Singleton pattern.
	 */
	private RegistrationManager() {
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
		createRegistrar();
	}
	
	/**
	 * Creates a registrar by reading in the properties file.
	 * This hashes the password in the properties file, then constructs a new Registrar object using it and the User details in the file.
	 * @throws IllegalArgumentException if the properties file cannot be read or does not exist
	 */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String hashPW = hashPW(prop.getProperty("pw"));
			
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	
	/**
	 * Generates a hashcode for a password, using the SHA-256 hashing algorithm.
	 * This will encrypt the password, so that it can't be easily stolen, and can be compared without directly reading in the password.
	 * @param pw the password to be hashed
	 * @return the hashed version of the password
	 * @throws IllegalArgumentException if the hashing algorithm cannot be found or used
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Returns the current instance of RegistrationManager, according to the Singleton pattern
	 * if the current instance is null, this will construct a new one
	 * @return the singleton instance of RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		  if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Returns the catalog of courses used by the manager.
	 * @return the catalog of courses
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	
	/**
	 * Returns the directory of students used by the manager.
	 * @return the directory of students
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
	 * Returns the directory of faculty used by the manager.
	 * @return the directory of students
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Attempts to log in to PackScheduler.
	 * This gets a student using the passed-in ID, then hashes the passed-in password.
	 * If the ID and password match the details of some Student, currentUser will be set to the student, and the method returns true.
	 * If the id and password match the Registrar password, currentUser will be set to registrar, and the method returns true.
	 * Otherwise, no one is logged into the system and the method returns false.
	 * @param id the user id to try to log in
	 * @param password the user password to try to log in
	 * @return true if the user logs in as a student or registrar, otherwise false
	 * @throws IllegalArgumentException thrown if unable to hash password
	 */
	public boolean login(String id, String password) {
	    if(getCurrentUser() != null) {
	        return false;
	    }
		
		if (registrar.getId().equals(id)) { 
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		} else {
		
			Student s = studentDirectory.getStudentById(id);
			Faculty f = facultyDirectory.getFacultyById(id);
			
			if(s != null) {
				try {
					MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String localHashPW = new String(digest.digest());
					if (s.getPassword().equals(localHashPW)) {
						currentUser = s;
						return true;
					}
				} catch (NoSuchAlgorithmException e) {
						throw new IllegalArgumentException();
				}
			}
			else if(f != null){
				try {
					MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String localHashPW = new String(digest.digest());
					if (f.getPassword().equals(localHashPW)) {
						currentUser = f;
						return true;
					}
				} catch (NoSuchAlgorithmException e) {
						throw new IllegalArgumentException();
				}
			}
			else { 
			    throw new IllegalArgumentException("User doesn't exist.");
			}
			
			
		}		
		return false;
	}

	/**
	 * Sets the current user to null.
	 * This will, in effect, log out the current user.
	 */
	public void logout() {
		currentUser = null; 
	}
	
	/**
	 * Gets the current user signed into the registration manager.
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Clears the data saved in the current catalog of courses and directory of students.
	 * This, in effect, just replaces them with brand new, empty SortedLists.
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}
	
	/** Method that assigns the given faculty to the given course
	 * 
	 * @param course is the given course
	 * @param faculty is the given faculty member
	 * @return returns if the operation was successful or not
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser instanceof Registrar) {
			boolean holder = false; 
			try {
				if(faculty.isOverloaded()) return false;				
				holder = faculty.getSchedule().addCourseToSchedule(course);
				return holder;
			} catch(IllegalArgumentException e){
				return false;
			} catch(NullPointerException e) {
			    return false;
			}
			
		}
		return false; 
		
	}
	
	/** Method that removes the faculty from the given course
	 * 
	 * @param course is the given course
	 * @param faculty is the given faculty member
	 * @return returns if the operation was successful or not
	 */
	public boolean  removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser instanceof Registrar) {
			return faculty.getSchedule().removeCourseFromSchedule(course);
		}
		throw new IllegalArgumentException();
	}
	
	/** Method that resets the given faculty's schedule
	 * 
	 * @param faculty is the given faculty
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser != null && currentUser instanceof Registrar) {
			faculty.getSchedule().resetSchedule();
		} else throw new IllegalArgumentException("invalid");
 	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * A class that represents a registrar.
	 * A registrar is a type of User that has advanced control over the Registration manager.
	 * @author CSC216 Teaching Staff
	 * @author Gage Fringer
	 * @author Jeremy Ignatowitz
	 * @author Ethan Taylor
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user with the user id and password 
		 * in the registrar.properties file.
		 * @param firstName registrar's first name
		 * @param lastName registrar's last name
		 * @param id registrar's Unity ID
		 * @param email registrar's email address
		 * @param hashPW registrar's hashed password
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}

}