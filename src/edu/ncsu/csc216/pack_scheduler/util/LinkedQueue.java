/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/** A LinkedQueue to hold elements for our scheduling purposes
 * @author Jared Faulk
 * @author Jeremy Ignatowitz
 * @param <E> generic element type
 *
 */
public class LinkedQueue<E> implements Queue<E> {
	
	private LinkedAbstractList<E> list;
	
	/** Constructor
	 * 
	 * @param capacity is the capacity of the queue
	 */
	public LinkedQueue(int capacity) {
	    list = new LinkedAbstractList<E>(capacity);
	}

	@Override
	public void enqueue(E element) {
		list.add(size(), element);
	}

	@Override
	public E dequeue() {
	    if(isEmpty()) {
	        throw new NoSuchElementException();
	    }
	    
		E result = list.get(0);
		list.remove(0);
		return result;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}
	
	


}
