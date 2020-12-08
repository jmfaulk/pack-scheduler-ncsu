package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;



import org.junit.Test;

/**
 * A suite of tests for our custom LinkedListRecursive
 * 
 * @author Jeremy Ignatowitz
 * @author Jared Faulk
 *
 */
public class LinkedListRecursiveTest {

	/**
	 *
	 * Tests creating and using our custom LinkedListRecursive
	 */
	@Test
	public void testLinkedListRecursive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(list.size(), 0);
		try {
		    list.get(0);
		    fail("List is empty");
		}
		catch(IndexOutOfBoundsException e) {
		    assertEquals(list.size(), 0);
		}
	}

	/**
	 * Test the contains method in Linked List recursive
	 */
	@Test
	public void testContainsLinkedListRecursive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		list.add("test1");
		list.add("test2");
		list.add("test3");
		list.add("test4");
		
		
		assertTrue(list.contains("test1"));
		assertTrue(list.contains("test2"));
		assertTrue(list.contains("test3"));
		assertTrue(list.contains("test4"));
		
		assertFalse(list.contains("jonjon"));
		
//
	}

	/**
	 * Test the add method in Linked List Recursive adds at element (also test get
	 * method)
	 */
	@Test
	public void testAddLinkedListRecursive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		int size = 0; // to test size
		// try adding one element to back
		try {

			list.add(0, "test0");
			size = 1;
			assertEquals("test0", list.get(0));
			assertEquals(size, list.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// try adding two elements and then inserting one at specified element
		try {
			list.add(0, "test1"); // add another elements; replace index 0 and right shift original element
			size = 2; // size = 2 now
			assertEquals("test1", list.get(0));
			assertEquals("test0", list.get(1));
			assertEquals(size, list.size()); // holds two elements here
		} catch (IllegalArgumentException e) {
			fail();
		}

		try {
			// add 9 more, should hold 11 in total
			list.add("test2");
			list.add("test3");
			list.add("test4");
			list.add(2, "test5"); // at 2th index, move "test2" to index 3
			list.add(3, "test6"); // at 3rd index, move test2 to index 4 and right shift everything after that
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

		} catch (IllegalArgumentException e) {
			fail();
		}

		// check adding element that already ecists
		try {
			list.add(1, "test2"); // already in list, shouldnt add!
			fail();
		} catch (IllegalArgumentException e) {
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
			size = 11;
			assertEquals(list.size(), size);

		}

		// test adding at index out of bounds
		try {
			list.add(100, "test100"); // index out of bounds!
			fail();
		} catch (IndexOutOfBoundsException e) {
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
			size = 11;
			assertEquals(list.size(), size);

		}

		// check null element
		try {
			list.add(10, null); // already in list, shouldnt add!
			fail();
		} catch (NullPointerException e) {
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
			size = 11;
			assertEquals(list.size(), size);

		}
	}

	/**
	 * Test the two remove method in Linked List Recursive adds at element (also
	 * test get method)
	 */
	@Test
	public void testRemoveLinkedListRecursive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		int size = 0; // to test size
		list.add("test1");
		list.add("test2");
		list.add("test3");
		list.add("test4");
		size = 4;

		// check removing at index first (front of list)
		try {
			list.remove(0);
			size = 3; // size = 3 now
			assertEquals("test2", list.get(0));
			assertEquals("test3", list.get(1));
			assertEquals("test4", list.get(2));
			assertEquals(size, list.size()); // holds two elements here
		} catch (IllegalArgumentException e) {
			fail();
		}

		// check removing end of list
		try {
			list.remove(2);
			size = 2; // size = 2 now
			assertEquals("test2", list.get(0));
			assertEquals("test3", list.get(1));
			assertEquals(size, list.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// add more to list
		list.add("test1");
		list.add("test4");
		size = 4;
		// check removing middle of list
		try {
			list.remove(1); // remove in middle of list
			size = 3; // size = 2 now
			assertEquals("test2", list.get(0));
			assertEquals("test1", list.get(1));
			assertEquals("test4", list.get(2));
			assertEquals(size, list.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// remove until empty
		try {
			list.remove(0); // remove in middle of list
			list.remove(0);
			list.remove(0);
			assertEquals(0, list.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// try remove from empty
		try {
			list.remove(0); // remove empty list
			fail();

		} catch (IndexOutOfBoundsException e) {
			size = 0;
			assertEquals(size, list.size());
		}

		// refill list
		list.add("test1");
		list.add("test2");
		list.add("test3");
		list.add("test4");

		// now check removing element
		try {
			list.remove("test1"); // remove in middle of list
			size = 3; // size = 2 now
			assertEquals("test2", list.get(0));
			assertEquals("test3", list.get(1));
			assertEquals("test4", list.get(2));
			assertEquals(size, list.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// try removing element in middle
		try {
			list.remove("test3"); // remove in middle of list
			size = 2; // size = 2 now
			assertEquals("test2", list.get(0));
			assertEquals("test4", list.get(1));
			assertEquals(size, list.size());
		} catch (IllegalArgumentException e) {
			fail();
		}

		// try removing null element
		assertFalse(list.remove(null)); 
		
		//removing from empty list
		list.remove(0); 
		list.remove(0); 
		size = 2; // size = 2 now
		//now try to remove from empty list 
		assertFalse(list.remove("test1"));
		
		//try removing element rhat doesnt exist
		list.add("test1");
		assertFalse(list.remove("test2"));
		size = 1;
		assertEquals(size, list.size());
		assertEquals("test1", list.get(0));
		

	}
	
	/**
	 * Tests set method
	 */
	@Test
	public void testSet() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		
		list.add("test1");
		list.add("test2");
		list.add("test3");
		list.add("test4");
		
		
		assertEquals(list.set(0, "test749"), "test1");
		assertEquals(list.get(0), "test749");
		
	}
}
