/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;

/** FacultyDirectory class that represents a list of Faculty in the system
 * 
 * @author Sumit Biswas
 * @author Jared Faulk
 * @author Jeremy Ignatowitz
 */
public class FacultyDirectory {
	
	/** LinkedList of Faculty members. */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/** Constructor for FacultyDirectory */
	public FacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/** Resets the list of faculty to an empty one */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/** Loads a list of Faculty from a given file
	 * 
	 * @param filename is the name of the given file
	 */
	public void loadFacultyFromFile(String filename) {
		try {
			
			facultyDirectory = FacultyRecordIO.readFacultyRecords(filename);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + filename);
		}
	}
	
	/** Constructs and adds a Faculty to the Faculty directory
	 * 
	 * @param firstName is the first name of the faculty
	 * @param lastName is the last name of the faculty
	 * @param id is the id of the faculty
	 * @param email is the email of the faculty
	 * @param password is the password 
	 * @param repeatPassword is the password of Faculty member, repeated to ensure accuracy
	 * @param maxCourses is the maximum number of courses a faculty member can teach
	 * @return returns if the add function was successful
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());
			
			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPassword.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
		
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		//If an IllegalArgumentException is thrown, it's passed up from Student
		//to the GUI
		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			if (f.getId().equals(faculty.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
//		return true;
	}
	
	/** Removes the Faculty with the given index from the directory
	 * 
	 * @param id is the given id
	 * @return returns if the operation was successful or not
	 */
	public boolean removeFaculty(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			if (f.getId().equals(id)) {
				facultyDirectory.remove(f);
				return true;
			}
		}
		return false;
	}
	
	/** Returns a 2d String array representation of the Faculty Directory
	 * 
	 * @return returns the string array representation
	 */
	public String[][] getFacultyDirectory() {
		String [][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			directory[i][0] = f.getFirstName();
			directory[i][1] = f.getLastName();
			directory[i][2] = f.getId();
		}
		return directory;
	}
	
	/** Writes the faculty directory list to a given file
	 * 
	 * @param filename is the name of the file to write to.
	 */
	public void saveFacultyDirectory(String filename) {
		try {
			FacultyRecordIO.writeFacultyRecords(filename, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + filename);
		}
	}
	
	/** Fetches the faculty member who has the given id
	 *  
	 * @param id is the specified id
	 * @return returns the Faculty object fetched
	 */
	public Faculty getFacultyById(String id) {
		Faculty f = null;
		for(int i = 0; i < facultyDirectory.size(); i++) {
			if(facultyDirectory.get(i).getId().equals(id)) {
				f = facultyDirectory.get(i);
			}
		}
		return f;
	}
}
