package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * A class that handles unit testing for StudentRecordIO.
 * This class declares several students, both validly and invalidly constructed, for use in 
 * testing the functionality of StudentRecordIO.
 * @author jcignato
 * @author rdhurlbu
 */
public class StudentRecordIOTest {
	
    /** Path for a valid test file */
	String validTestFile = "test-files/student_records.txt";
	
	/** Path for an invalid test file */
	String invalidTestFile = "test-files/invalid_student_records.txt";
	
	/** A string representing a validly-constructed student */
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** A string representing a validly-constructed student */
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** A string representing a validly-constructed student */
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** A string representing a validly-constructed student */
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** A string representing a validly-constructed student */
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** A string representing a validly-constructed student */
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** A string representing a validly-constructed student */
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** A string representing a validly-constructed student */
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	/** A string representing a validly-constructed student */
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** A string representing a validly-constructed student */
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";

	/** An array of strings representing validly-constructed students */
	private String [] validStudents = {validStudent0, validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
	        validStudent6, validStudent7, validStudent8, validStudent9};

	/** a string representing a hashed version of a password */
	private String hashPW;
	/** The algorithm used to hash passwords */
	private static final String HASH_ALGORITHM = "SHA-256";


	/**
	 * Sets up the StudentRecordIO tests.
	 * Attempts to hash a password, and replace any password in the validStudents array with the 
	 * hashed version.
	 * @throws Exception If there is a problem with creation of the hash
	 */
	@Before
	public void setUp() throws Exception 
	{
		try 
		{
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = new String(digest.digest());
	        
	        for (int i = 0; i < validStudents.length; i++) 
	        {
	            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) 
		{
	        fail("Unable to create hash during setup");
	    }
	}

	/**
	 * Tests readValidCourseRecords().
	 * Reads information from a file, test fails if the array returned does not have the correct information
	 * test fails if file can not be read
	 */
	@Test
	public void testReadValidCourseRecords() {
		try {
			SortedList<Student> courses = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, courses.size());
			
			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], courses.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
	}
	
	/**
	 * Tests reading student records from an invalid file.
	 * After the invalid file is read in, the studentRecords SortedList should be empty.
	 * If the file can't be found, the test should fail.
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		SortedList<Student> students;
		try {
			students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * Tests the output of writeStudentRecords() fails if the output of the method is not a file with the correct contents
	 * fails if file is not created or method is unable to write to a file. 
	 */
	@Test
	public void testWriteStudentRecords() {
		
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to course records file");
		}
		
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	
	/**
	 * Tests writing student records without the proper permissions.
	 * If one doesn't have the correct permissions, the write should not happen. If it does,
	 * the test should fail.
	 * This will fail in local testing! But it will pass in Jenkins.
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
	    SortedList<Student> students = new SortedList<Student>();
	    students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
	    //Assumption that you are using a hash of "pw" stored in hashPW
	    
	    try {
	        StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
	        fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
	    } catch (IOException e) {
	        assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
	        //The actual error message on Jenkins!
	    }
	    
	}
	
	/**
	 * Checks to see if two files have the same contents fails if files can not be read or if contents are not the same 
	 * @param expFile file that contains the expected contents 
	 * @param actFile file that contains what the actual contents are
	 */
	private void checkFiles(String expFile, String actFile) 
	{
	    try 
	    {
	        Scanner expScanner = new Scanner(new FileInputStream(expFile));
	        Scanner actScanner = new Scanner(new FileInputStream(actFile));
	        
	        while (expScanner.hasNextLine()  && actScanner.hasNextLine()) 
	        {
	            String exp = expScanner.nextLine();
	            String act = actScanner.nextLine();
	            assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
	        }
	        if (expScanner.hasNextLine()) 
	        {
	            fail("The expected results expect another line " + expScanner.nextLine());
	        }
	        if (actScanner.hasNextLine()) 
	        {
	            fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
	        }
	        
	        expScanner.close();
	        actScanner.close();
	    } catch (IOException e) 
	    {
	        fail("Error reading files.");
	    }
	}
}
