package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.ListIterator;

import org.junit.Test;

/**
 * A suite of tests for our custom LinkedList.
 * 
 * @author Jeremy Ignatowitz
 * @author Jared Faulk
 *
 */
public class LinkedListTest {

	/**
	 * Tests creating and using our custom ArrayList. An ArrayList is created and is
	 * empty; elements are added at particular indexes; up to 11 elements are added
	 * so as to extend the array; an element is set to a specific index.
	 */
	@Test
	public void testLinkedList() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(list.size(), 0);
	}

	/**
	 * Test the add method in Linked List
	 */
	@Test
	public void testAddLinkedList() { 
		LinkedList<String> list = new LinkedList<String>();
		int size = 0; //to test size
		//try adding one element to back
		try {
			
			list.add("test0"); 
			size = 1;
			assertEquals("test0", list.get(0));
			assertEquals(size, list.size());
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		//try adding two elements and then inserting one at specified element
		try {
			list.add(0, "test1"); //add another elements; replace index 0 and right shift original element
			size = 2; //size = 2 now
			assertEquals("test1", list.get(0));
			assertEquals("test0", list.get(1));
			assertEquals(size, list.size()); //holds two elements here
		} catch(IllegalArgumentException e) {
			fail();
		}
		
		try {
			//add 9 more, should hold 11 in total
			list.add("test2");
			list.add("test3");
			list.add("test4");
			list.add(2, "test5"); //at 2th index, move "test2" to index 3
			list.add(3, "test6"); //at 3rd index, move test2 to index 4 and right shift everything after that
			list.add("test7");
			list.add("test8");
			list.add("test9");
			list.add("test10");
			size = 11;
			assertEquals(list.size(), size);
			
			assertEquals(list.get(0), "test1");
			assertEquals(list.get(1), "test0");
			assertEquals(list.get(2), "test5");
			assertEquals(list.get(3), "test6");
			assertEquals(list.get(4), "test2");
			assertEquals(list.get(5), "test3");
			assertEquals(list.get(6), "test4");
			assertEquals(list.get(7), "test7");
			assertEquals(list.get(8), "test8");
			assertEquals(list.get(9), "test9");
			assertEquals(list.get(10), "test10");

			assertEquals(list.set(0, "test749"), "test1");
			assertEquals(list.get(0), "test749");
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	/**
	 * Tests the functionality of the ListIterator written in the custom LinkedList class.
	 */
	@Test
	public void testListIterator() {
	    LinkedList<String> list = new LinkedList<String>();
	    list.add("Acacia");
	    list.add("Birch");
	    list.add("Cedar");
	    list.add("Dogwood");
	    list.add("Elm");
	    
	    ListIterator<String> iterator = list.listIterator(0);
	    assertFalse(iterator.hasPrevious());
	    
	    assertEquals(iterator.next(), "Acacia");
	    assertEquals(iterator.next(), "Birch");
	    
	    iterator = list.listIterator(list.size);
	    assertFalse(iterator.hasNext());
	    assertEquals(iterator.previous(), "Elm");
	    assertEquals(iterator.previous(), "Dogwood");
	}
}
