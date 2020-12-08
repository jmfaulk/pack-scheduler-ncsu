package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests class for Sorted Lists
 * @author Ryan Hurlbut
 *
 */
public class SortedListTest {

	/**
	 * Tests That the list grows correctly
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
		//TODO Test that the list grows by adding at least 11 elements
		//Remember the list's initial capacity is 10
		
	}

	/**
	 * Tests adding an element to a sorted list
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		// adding to front
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		
		// end
		list.add("zuchcini");
		assertEquals(3, list.size());
		assertEquals("zuchcini", list.get(list.size() - 1));
		
		// middle 
		list.add("pineapple");
		assertEquals(4, list.size());
		assertEquals("pineapple", list.get(list.size() - 2));
		
		// test adding null throws exception
		try
		{
			list.add(null);
			fail();
		}
		catch (NullPointerException e)
		{
			assertEquals(4, list.size());
			assertEquals("apple", list.get(0));
		}
		
		// Test adding a duplicate element
		try 
		{
			list.add("apple");
			fail();
		}
		catch (IllegalArgumentException e)
		{
			assertEquals(4, list.size());
			assertEquals("banana", list.get(1));
		}
	}
	
	
	/**
	 * Tests getting a specific element from the Sorted List
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		// empty array
		try 
		{
			
			list.get(0);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			assertEquals(0, list.size());
		}

		list.add("John");
		list.add("Hugo");
		list.add("Obama");
		list.add("Lit Romney");

		try 
		{
			list.get(-1);
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			assertEquals(4, list.size());
			assertEquals("Hugo", list.get(0));
		}
		
		try 
		{
			list.get(list.size());
			fail();
		}
		catch (IndexOutOfBoundsException e)
		{
			assertEquals(4, list.size());
			assertEquals("Obama", list.get(list.size() - 1));
		}	
	}
	
	/**
	 * Tests removing an element from the List
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		//removing from an empty list
		try {
			list.remove(0);
			fail();
		}
		catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		//Add some elements to the list - at least 4
		list.add("Amaryllis");
		list.add("Bertrand");
		list.add("Catalina");
		list.add("Daelen");
		
		//Test removing an element at an index < 0
		try {
		    list.remove(-1);
		    fail();
		}
		catch (IndexOutOfBoundsException e) {
		    assertEquals(list.size(), 4);
		}
		
		//Test removing an element at size
		try {
		    list.remove(list.size());
		    fail();
		}
		catch (IndexOutOfBoundsException e) {
		    assertEquals(list.size(), 4);
		}
		
		//Test removing a middle element
		list.remove(2);
		assertEquals(list.size(), 3);
		assertEquals(list.get(2), "Daelen");
		assertFalse(list.contains("Catalina"));
		
		// Test removing the last element
		list.remove(list.size() - 1);
		assertEquals(list.size(), 2);
		assertEquals(list.get(list.size() - 1), "Bertrand");
		assertFalse(list.contains("Daelen"));
		
		// Test removing the first element
		list.remove(0);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), "Bertrand");
        assertFalse(list.contains("Amaryllis"));
		
		// Test removing the last element
        list.remove(list.size() - 1);
        assertEquals(list.size(), 0);
	}
	
	/**
	 * Tests getting the index of an element
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		assertEquals(-1, list.indexOf("Empty List"));
		list.add("Johnney");
		list.add("apple");
		list.add("seed");

		assertEquals(2, list.indexOf("seed"));
		assertEquals(-1, list.indexOf("Johney"));
		assertEquals(1, list.indexOf("apple"));

		try
		{
			list.indexOf(null);
			fail();
		}
		catch (NullPointerException e ) {
			// if we get here we caught the right exception 
		}
	}
	
	/**
	 * Tests clearing the list of all elements
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		list.add("csc116");
		list.add("csc216");
		list.add("csc316");
		list.add("csc416");

		list.clear();
		assertTrue(list.isEmpty());
	}

	/**
	 * Tests if the sorted list is empty 
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		assertTrue(list.isEmpty());
		list.add("csc116");
		list.add("csc216");
		list.add("csc316");
		list.add("csc416");
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests if the list can show that it contains an element
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		assertFalse(list.contains("csc116"));
		list.add("csc116");
		list.add("csc216");
		list.add("csc316");
		list.add("csc416");
		assertTrue(list.contains("csc116"));
		assertTrue(list.contains("csc416"));
		assertFalse(list.contains(""));
	}
	
	/**
	 * Tests to see if two different lists will return correct equals boolean values
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("csc116");
		list1.add("csc216");
		list1.add("csc316");
		list1.add("csc416");
		
		list2.add("csc116");
		list2.add("csc216");
		list2.add("csc316");
		list2.add("csc416");
		
		list3.add("John");
		list3.add("Hugo");
		list3.add("Obama");
		list3.add("Lit Romney");
		
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		assertFalse(list3.equals(list1));
		assertFalse(list3.equals(list2));
	}
	
	/**
	 * Tests that the hash code of a list returns correctly
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("Johnney");
		list1.add("apple");
		list1.add("seed");
		
		list2.add("Amaryllis");
		list2.add("Bertrand");
		list2.add("Catalina");
		
		list3.add("Johnney");
		list3.add("apple");
		list3.add("seed");
				
		assertEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list1.hashCode(), list2.hashCode());
	}

}