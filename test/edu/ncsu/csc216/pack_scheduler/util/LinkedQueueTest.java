/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/** Test class to test LinkedQueue
 * @author Jeremy Ignatowitz
 *
 * Tests for the LinkedQueue class.
 */
public class LinkedQueueTest {

    /**
     * Tests for the LinkedQueue class.
     */
	@Test
	public void testQueue() {
		LinkedQueue<String> queue = new LinkedQueue<String>(10);
		
		queue.enqueue("Apple");
		assertEquals("Apple", queue.dequeue());
		assertEquals(0, queue.size());
		
		try {
            queue.dequeue();
            fail("Can't dequeue from empty queue");
        }
        catch(NoSuchElementException e) {
            assertEquals(0, queue.size());
        }
		
		queue.enqueue("Apple");
		queue.enqueue("Banana");
		queue.enqueue("Cherry");
		queue.enqueue("Durian");
		queue.enqueue("Eggplant");
		
		assertEquals("Apple", queue.dequeue());
		assertEquals("Banana", queue.dequeue());
		assertEquals(3, queue.size());
		assertEquals("Cherry", queue.dequeue());
		
		queue.enqueue("Fig");
		queue.dequeue();
        queue.enqueue("Grape");
        queue.dequeue();
        queue.enqueue("Honeydew");
        queue.dequeue();
        
        assertEquals(2, queue.size());
        
        try {
            queue.setCapacity(queue.size());
        }
        catch(IllegalArgumentException e) {
            fail(e.getMessage());
        }
        
        try {
            queue.enqueue("Imbe");
            fail("Can't enqueue past capacity");
        }
        catch(IllegalArgumentException e) {
            assertEquals(2, queue.size());
        }
        
        try {
            queue.setCapacity(queue.size() - 1);
            fail("Can't set capacity lower than size");
        }
        catch(IllegalArgumentException e) {
            assertEquals(2, queue.size());
        }
	}

}
