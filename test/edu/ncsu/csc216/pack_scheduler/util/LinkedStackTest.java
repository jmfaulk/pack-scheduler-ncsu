package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/** Test class to test LinkedStack
 * @author Jared Faulk
 * @author Jeremy Ignatowitz
 * @author Sumit Biswas
 *
 */
public class LinkedStackTest {

	/** Contains tests for all LinkedStack methods. */
    @Test
    public void testStack() {
        LinkedStack<String> stack = new LinkedStack<String>(10);
        stack.push("Apple");
        
        assertEquals("Apple", stack.pop());
        assertEquals(0, stack.size());
        
        try {
            stack.pop();
            fail("Can't pop from empty stack");
        }
        catch(EmptyStackException e) {
            assertEquals(0, stack.size());
        }
        
        stack.push("Apple");
        stack.push("Banana");
        stack.push("Cherry");
        stack.push("Durian");
        stack.push("Eggplant");
        
        assertEquals("Eggplant", stack.pop());
        assertEquals("Durian", stack.pop());
        assertEquals(3, stack.size());
        
        stack.push("Fig");
        stack.pop();
        stack.push("Grape");
        stack.pop();
        stack.push("Honeydew");
        stack.pop();
        
        assertEquals(3, stack.size());
        
        try {
            stack.setCapacity(stack.size());
        }
        catch(IllegalArgumentException e) {
            fail(e.getMessage());
        }
        
        try {
            stack.push("Imbe");
            fail("Can't push past capacity");
        }
        catch(IllegalArgumentException e) {
            assertEquals(3, stack.size());
        }
        
        try {
            stack.setCapacity(stack.size() - 1);
            fail("Can't set capacity lower than size");
        }
        catch(IllegalArgumentException e) {
            assertEquals(3, stack.size());
        }
    }

}
