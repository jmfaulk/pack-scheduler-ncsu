/**
 * 
 */
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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * A set of JUnit test cases for FacultyRecordIO.
 * @author jarodhowls
 *
 */
public class FacultyRecordIOTest {


		/** Path for a valid test file */
		String validTestFile = "test-files/faculty_records.txt";

		/** Path for an invalid test file */
		String invalidTestFile = "test-files/invalid_faculty_records.txt";

		/** A string representing a validly-constructed student */
		private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
		/** A string representing a validly-constructed faculty */
		private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
		/** A string representing a validly-constructed faculty */
		private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
		/** A string representing a validly-constructed faculty */
		private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
		/** A string representing a validly-constructed faculty */
		private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
		/** A string representing a validly-constructed faculty */
		private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
		/** A string representing a validly-constructed faculty */
		private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
		/** A string representing a validly-constructed faculty */
		private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";
	

		/** An array of strings representing validly-constructed students */
		private String[] validFaculty = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4, validFaculty5,
				validFaculty6, validFaculty7};

		/** a string representing a hashed version of a password */
		private String hashPW;
		/** The algorithm used to hash passwords */
		private static final String HASH_ALGORITHM = "SHA-256";

		/**
		 * Sets up the StudentRecordIO tests. Attempts to hash a password, and replace
		 * any password in the validFaculty array with the hashed version.
		 * 
		 * @throws Exception If there is a problem with creation of the hash
		 */
		@Before
		public void setUp() throws Exception {
			try {
				String password = "pw";
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				hashPW = new String(digest.digest());

				for (int i = 0; i < validFaculty.length; i++) {
					validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
				}
			} catch (NoSuchAlgorithmException e) {
				fail("Unable to create hash during setup");
			}
		}

		/**
		 * Tests readValidCourseRecords(). Reads information from a file, test fails if
		 * the array returned does not have the correct information test fails if file
		 * can not be read
		 */
		@Test
		public void testReadValidFacultyRecords() {
			try {
				LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(validTestFile);
				assertEquals(8, faculty.size());

				for (int i = 0; i < validFaculty.length; i++) {
					assertEquals(validFaculty[i], faculty.get(i).toString());
				}
			} catch (FileNotFoundException e) {
				fail("Unexpected error reading " + validTestFile);
			}
		}

		/**
		 * Tests reading faculty records from an invalid file. After the invalid file is
		 * read in, the studentRecords SortedList should be empty. If the file can't be
		 * found, the test should fail.
		 */
		@Test
		public void testReadInvalidStudentRecords() {
			LinkedList<Faculty> faculty;
			try {
				faculty = FacultyRecordIO.readFacultyRecords(invalidTestFile);
				assertEquals(0, faculty.size());
			} catch (FileNotFoundException e) {
				fail("Unexpected FileNotFoundException");
			}
		}

		/**
		 * Tests the output of writeStudentRecords() fails if the output of the method
		 * is not a file with the correct contents fails if file is not created or
		 * method is unable to write to a file.
		 */
		@Test
		public void testWriteFacultyRecords() {

			LinkedList<Faculty> faculty = new LinkedList<Faculty>();
			faculty.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW,  2));
			faculty.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", hashPW, 3));
			faculty.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", hashPW, 1));
			try {
				FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculty);
			} catch (IOException e) {
				fail("Cannot write to faculty records file");
			}

			checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
		}

		/**
		 * Tests writing student records without the proper permissions. If one doesn't
		 * have the correct permissions, the write should not happen. If it does, the
		 * test should fail. This will fail in local testing! But it will pass in
		 * Jenkins.
		 */
		@Test
		public void testWriteStudentRecordsNoPermissions() {
			LinkedList<Faculty> faculty = new LinkedList<Faculty>();
			faculty.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 1)); //this info shouldnt matter
			// Assumption that you are using a hash of "pw" stored in hashPW

			try {
				FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", faculty);
				fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
			} catch (IOException e) {
				assertEquals("/home/sesmith5/actual_faculty_records.txt (Permission denied)", e.getMessage());
				// The actual error message on Jenkins!
			}

		}

		/**
		 * Checks to see if two files have the same contents fails if files can not be
		 * read or if contents are not the same
		 * 
		 * @param expFile file that contains the expected contents
		 * @param actFile file that contains what the actual contents are
		 */
		private void checkFiles(String expFile, String actFile) {
			try {
				Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));

				while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
					String exp = expScanner.nextLine();
					String act = actScanner.nextLine();
					assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
				}
				if (expScanner.hasNextLine()) {
					fail("The expected results expect another line " + expScanner.nextLine());
				}
				if (actScanner.hasNextLine()) {
					fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
				}

				expScanner.close();
				actScanner.close();
			} catch (IOException e) {
				fail("Error reading files.");
			}
		}
}


