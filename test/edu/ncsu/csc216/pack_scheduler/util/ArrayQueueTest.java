package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests the ArrayQueue
 * 
 * @author Jared Faulk
 *
 */
public class ArrayQueueTest {

	/** String for testing */
	public static final String ELE0 = "test";

	/** String for testing */
	public static final String ELE1 = "test1";

	/** String for testing */
	public static final String ELE2 = "test2";

	/** String for testing */
	public static final String ELE3 = "test3";

	/**
	 * Tests the constructor of an ArrayQueue
	 */
	@Test
	public void testArrayQueue() { // tests contructor and isEmpty method

		ArrayQueue<Object> testArray = new ArrayQueue<Object>(10); // create a generic arrayQueue

		assertEquals(0, testArray.size());
		assertTrue(testArray.isEmpty());

	}

	/**
	 * Tests adding elements
	 */
	@Test
	public void testAddElements() {

		ArrayQueue<Object> testArray = new ArrayQueue<Object>(5); // create a generic arrayQueue

		int size = 0;

		// test adding a single element
		testArray.setCapacity(10);
		testArray.enqueue(ELE0); // add an element
		size++;
		try {
			assertEquals(ELE0, testArray.getArrayQueue().get(0));
			assertEquals(size, testArray.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// tests adding multiple elements
		testArray.enqueue(ELE1); // add another element
		testArray.enqueue(ELE2); // add another element
		size += 2; // size = 3 now
		try {
			assertEquals(ELE0, testArray.getArrayQueue().get(0));
			assertEquals(ELE1, testArray.getArrayQueue().get(1));
			assertEquals(ELE2, testArray.getArrayQueue().get(2));
			assertEquals(size, testArray.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	/**
	 * Tests removing an element
	 */
	@Test
	public void testRemoveElements() {

		ArrayQueue<Object> testArray = new ArrayQueue<Object>(5); // create a generic arrayQueue
		int size = 0;

		// have four elements

		// test removing a single element at front
		try {
			testArray.setCapacity(10);
			testArray.enqueue(ELE0); // add another element
			testArray.enqueue(ELE1); // add another element
			testArray.enqueue(ELE2); // add another element
			testArray.enqueue(ELE3); // add another element
			size += 4; //size = 3

			testArray.dequeue();
			size--; //size=3
			assertEquals(ELE1, testArray.getArrayQueue().get(0)); // left shift
			assertEquals(ELE2, testArray.getArrayQueue().get(1));
			assertEquals(ELE3, testArray.getArrayQueue().get(2)); 
			assertEquals(size, testArray.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// tests removing multiple elements
		try {
			testArray.dequeue(); // remove another element
			testArray.dequeue(); // add another element
			size -= 2; // size = 1 now
			assertEquals(ELE3, testArray.getArrayQueue().get(0)); // left shift
			assertEquals(size, testArray.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// remove last element
		try {
			testArray.dequeue(); // remove last  element
			size--; // size = 0 now
			assertTrue(testArray.isEmpty());
			assertEquals(size, testArray.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// remove from empty list
		try {
			testArray.dequeue(); // remove non-existant element
			fail();

		} catch (NoSuchElementException e) {
			assertEquals(size, testArray.size());
			assertTrue(testArray.isEmpty());

		}

	}

	/**
	 * Tests removing an element
	 */
	@Test
	public void testAddRemoveElements() {

		ArrayQueue<Object> testArray = new ArrayQueue<Object>(5); // create a generic arrayQueue
		int size = 0;
		testArray.setCapacity(10);
		
		try {
			testArray.enqueue(ELE0); // add another element
			testArray.enqueue(ELE1); // add another element
			size += 2; //size=2
			testArray.dequeue(); // remove ELE0 another element 
			size--;	//size=1
			testArray.enqueue(ELE2); // add another element
			size++; // should equal 2
			assertEquals(ELE1, testArray.getArrayQueue().get(0)); // left shift
			assertEquals(ELE2, testArray.getArrayQueue().get(1)); // left shift
			assertEquals(size, testArray.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

	}

	/**
	 * Tests removing an element
	 */
	@Test
	public void testSetCapacity() {

		ArrayQueue<Object> testArray = new ArrayQueue<Object>(5); // create a generic arrayQueue
		int size = 1;

		// setting capacity to size
		try {
			testArray.setCapacity(size);
			assertEquals(size, testArray.getCapacity());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// setting capacity to less than size
		try {
			testArray.enqueue(ELE0); //size =1 
			testArray.setCapacity(size - 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(size, testArray.size());
			assertEquals(size, testArray.getCapacity());
			
		}

	}

}
