package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A suite of JUnit tests for ArrayList.
 * @author Jeremy Ignatowitz
 *
 */
public class ArrayListTest {

    
    /**
     * Tests creating and using our custom ArrayList.
     * An ArrayList is created and is empty; elements are added at particular indexes; up to 11 elements are added so as to extend the array;
     * an element is set to a specific index.
     */
    @Test
    public void testArrayList() {
        ArrayList<String> list = new ArrayList<String>();
        assertEquals(list.size(), 0);
        
        list.add("test");
        list.add(0, "test2");
        assertEquals(list.get(0), "test2");
        assertEquals(list.get(1), "test");
        list.add("test3");
        list.add("test4");
        list.add("test5");
        list.add(4, "test6");
        list.add(3, "test7");
        list.add("test8");
        list.add("test9");
        list.add("test10");
        list.add("test11");
        assertEquals(list.size(), 11);
        assertEquals(list.get(0), "test2");
        assertEquals(list.get(1), "test");
        
        assertEquals(list.set(0, "test749"), "test2");
        assertEquals(list.get(0), "test749");
        
        list.add(0, "test413");
        
    }

}
