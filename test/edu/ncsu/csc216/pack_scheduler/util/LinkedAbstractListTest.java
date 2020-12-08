package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class with a suite of tests to test the LinkedAbstractList class
 * @author gwfringe
 *
 */
public class LinkedAbstractListTest {
	
//    /** Test back */
//	@Test
//	public void testBack() {
//		LinkedAbstractList<Integer> list = new LinkedAbstractList<Integer>(5);
//		list.add(0, 1);		
//		list.add(1, 2);
//		list.add(2, 3);
//		list.set(2, 4);
//		
//		assertEquals(Integer.valueOf(4), list.getBack());
//		
//		list = new LinkedAbstractList<Integer>(5);
//		list.add(1);
//		assertEquals(Integer.valueOf(1), list.getBack());
//	}

	/**
	 * Method to test proper construction of the LinkedList
	 */
	@Test
	public void testConstruction() {
		LinkedAbstractList<Integer> list = new LinkedAbstractList<Integer>(3);
		assertEquals(list.size(), 0);
		
		try {
			list = new LinkedAbstractList<Integer>(-2);
		} catch(IllegalArgumentException e) {
			assertEquals(0, list.size()); 
		}
	}
	
	/** Method that tests LinkedAbstractList.setCapacity() */
	@Test
	public void testSetCapacity() {
		LinkedAbstractList<Integer> list = new LinkedAbstractList<Integer>(3);
		assertEquals(list.size(), 0);
		
		list.add(1);
		list.add(2);
		list.add(3);
		
		try {
			list.add(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(list.size(), 3);
		}
		
		try {
			list.setCapacity(-1);
		} catch (IllegalArgumentException e2) {
			assertEquals(list.size(), 3);
		}
		
		try {
			list.setCapacity(5);
		} catch (Exception e) {
			fail();
		}
		
		try {
			list.add(4);
			assertEquals(list.size(), 4);
		} catch (Exception e) {
			fail();
		}
		
		try {
			list.add(2, 6);
			assertEquals(list.size(), 5);
		} catch (Exception e) {
			fail();
		}
	}
	
	/**
	 * Method to test the set method
	 */
	@Test 
	public void testSet() {
		LinkedAbstractList<Integer> list = new LinkedAbstractList<Integer>(3);
		assertEquals(list.size(), 0);
		
		list.add(0, 2);
		assertEquals(list.size(), 1);
		assertEquals((int)list.get(0), 2);
		
		list.set(0, 5);
		assertEquals(list.size(), 1);
		assertEquals((int)list.get(0), 5);
		
		try {
			list.set(4, 5);
			fail();
		} catch(IndexOutOfBoundsException e) {
			assertEquals(list.size(), 1);
		}
		
		try {
			list.set(1, null);
			fail();
		} catch(NullPointerException e) {
			assertEquals(list.size(), 1);
		}
		
		try {
			list.set(1, 5);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(list.size(), 1);
		}
	}
	
	/**
	 * Method to set the get method
	 */
	@Test
	public void testGet() {
		LinkedAbstractList<Integer> list = new LinkedAbstractList<Integer>(3);
		assertEquals(list.size(), 0);
		
		list.add(0, 2);
		assertEquals(list.size(), 1);
		assertEquals((int)list.get(0), 2);
	}
	
	/**
	 * Method to test the remove method
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<Integer> list = new LinkedAbstractList<Integer>(3);
		assertEquals(list.size(), 0);
		
		list.add(0, 2);
		assertEquals(list.size(), 1);
		assertEquals((int)list.get(0), 2);
		
		list.set(0, 5);
		assertEquals(list.size(), 1);
		assertEquals((int)list.get(0), 5);
		
		try {
			list.remove(4);
		} catch(IndexOutOfBoundsException e) {
			assertEquals(list.size(), 1);
		}
		
		list.add(1, 3);
		list.add(2, 6);
		list.remove(1);
		assertEquals(list.size(), 2);
	}
	
	/**
	 * Method to test the add method
	 */
	@Test
	public void testAdd() {
		LinkedAbstractList<Integer> list = new LinkedAbstractList<Integer>(3);
		assertEquals(list.size(), 0);
		
		list.add(0, 2);
		assertEquals(list.size(), 1);
		assertEquals((int)list.get(0), 2);
		
		list.set(0, 5);
		assertEquals(list.size(), 1);
		assertEquals((int)list.get(0), 5);

		list.add(1, 3);
		assertEquals(list.size(), 2);
		assertEquals((int)list.get(1), 3);

		try {
			list.add(2, null);
			fail();
		} catch(NullPointerException e) {
			assertEquals(list.size(), 2);
		}
		
		
		list.add(2, 6);
		assertEquals(list.size(), 3);
		assertEquals((int)list.get(2), 6);

		
		try {
			list.add(3, 5);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals(list.size(), 3);
		}
		
	}
	
}
