package edu.ncsu.csc216.pack_scheduler.io;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * A class that handles input and output of student records, to and from files.
 * Reads files to generate a SortedList of students, or writes the contents of a student ArrayList to a file.
 * Files should have each student on a different line, with their records in a comma-separated list.
 * @author Jeremy Ignatowitz
 * @author Ryan Hurlbut
 */
public class StudentRecordIO {
    /**
     * Reads in student records from a file, one set of records per line.
     * @param fileName name of the file to be read
     * @return SortedList a List of students with the given records
     * @throws FileNotFoundException if the specified file doesn't exist
     */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>();
		boolean fencePostFirstTime = true;
		while(fileReader.hasNextLine()) {
		    try {
		    	boolean duplicate = false;
		        Student student = processStudent(fileReader.nextLine());
		        if (fencePostFirstTime) // first run through
		        {
		        	students.add(student);
		        	fencePostFirstTime = false;
		        }
		        else // not the first time running through
		        {
			        for(int i = 0; i < students.size(); i++) {
			            User s = students.get(i);
			            if(s.getId().equals(student.getId())) {
			                //same ID is a duplicate student
			                duplicate = true;
			            }
			        }
			        if(!duplicate) {
			            students.add(student);
			        }
			        else {
			            throw new IllegalArgumentException("Student already in system.");
			        }
		        }
		        
		    }
		    catch(IllegalArgumentException e) {
		        //if an IllegalArgumentException is thrown, skip that line of the file
		    }
		}
		fileReader.close();
	    return students;
	}
	
	/**
	 * Reads input line and interprets as a Student object
	 * 
	 * @param line line that contains Student information
	 * @return Student the Studnet created from the line
	 * @throws IllegalArgumentException thrown if unable to read Student from line
	 */
	private static Student processStudent(String line) {
	    Scanner tokenizer = new Scanner(line);
	    tokenizer.useDelimiter(",");
	    String firstName;
	    String lastName;
	    String id;
	    String email;
	    String password;
	    int maxCredits;
	    try {
	        firstName = tokenizer.next();
	        lastName = tokenizer.next();
	        id = tokenizer.next();
	        email = tokenizer.next();
	        password = tokenizer.next();
	        maxCredits = Integer.parseInt(tokenizer.next());
	        
	    }
	    catch(NoSuchElementException e) {
	        tokenizer.close();
	        throw new IllegalArgumentException();
	    }
	    tokenizer.close();
	    return new Student(firstName, lastName, id, email, password, maxCredits);
	    
	}

	/**
	 * Writes a set of student records to a file.
	 * @param fileName the file to write the records to
	 * @param studentDirectory a SortedList of students to write to the file
	 * @throws IOException if the given file cannot be written to
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
	    PrintStream fileWriter = new PrintStream(new File(fileName));
		for(int i = 0; i < studentDirectory.size(); i++) {
		    fileWriter.println(studentDirectory.get(i).toString());
		}
		
		fileWriter.close();
	}

}
