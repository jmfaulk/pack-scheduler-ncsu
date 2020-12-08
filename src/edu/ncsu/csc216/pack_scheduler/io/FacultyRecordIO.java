/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Reads and writes Faculty records from and to a file
 * @author Jared Faulk
 *
 */
public class FacultyRecordIO {

	/**
	 * Reads in Faculty records from a file. Reads the file and processes it, turning a text file into a LinkedList of Faculty objects.
	 * 
	 * @param filename the filename passed in
	 * @return LinkedList of Faculty
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String filename) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(filename));
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		boolean fencePostFirstTime = true;
		while(fileReader.hasNextLine()) {
		    try {
		    	boolean duplicate = false;
		        Faculty teacher = processFacultyString(fileReader.nextLine());
		        if (fencePostFirstTime) // first run through
		        {
		        	faculty.add(teacher);
		        	fencePostFirstTime = false;
		        }
		        else // not the first time running through
		        {
			        for(int i = 0; i < faculty.size(); i++) {
			            User s = faculty.get(i);
			            if(s.getId().equals(teacher.getId())) {
			                //same ID is a duplicate student
			                duplicate = true;
			            }
			        }
			        if(!duplicate) {
			            faculty.add(teacher);
			        }
			        else {
			            throw new IllegalArgumentException("Faculty already in system.");
			        }
		        }
		        
		    }
		    catch(IllegalArgumentException e) {
		        //if an IllegalArgumentException is thrown, skip that line of the file
		    }
		}
		fileReader.close();
	    return faculty;
	}
	
	/**
	 * Reads input line and interprets as a Student object
	 * 
	 * @param line line that contains Student information
	 * @return Student the Studnet created from the line
	 * @throws IllegalArgumentException thrown if unable to read Student from line
	 */
	private static Faculty processFacultyString(String line) {
	    Scanner tokenizer = new Scanner(line);
	    tokenizer.useDelimiter(",");
	    String firstName;
	    String lastName;
	    String id;
	    String email;
	    String password;
	    int maxCourses;
	    try {
	        firstName = tokenizer.next();
	        lastName = tokenizer.next();
	        id = tokenizer.next();
	        email = tokenizer.next();
	        password = tokenizer.next();
	        maxCourses = Integer.parseInt(tokenizer.next());
	        
	    }
	    catch(NoSuchElementException e) {
	        tokenizer.close();
	        throw new IllegalArgumentException();
	    }
	    tokenizer.close();
	    return new Faculty(firstName, lastName, id, email, password, maxCourses);
	    
	}



	/**
	 * Writes faculty records to a file
	 * @param fileName filename to write out to
	 * @param facultyDirectory the linked list of faculty
	 * @throws FileNotFoundException if the file to write to cannot be found
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws FileNotFoundException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for(int i = 0; i < facultyDirectory.size(); i++) {
		    fileWriter.println(facultyDirectory.get(i).toString());
		}
		
		fileWriter.close();
	}

	

}
