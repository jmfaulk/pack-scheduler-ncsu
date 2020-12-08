/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Queue interface
 * 
 * @author Jared Faulk
 * @param <E> generic element type
 *
 */
public interface Queue<E> {

	/**
	 * adds the element to the back of the Queue If there is no room (capacity has
	 * been reached), an IllegalArgumentException is thrown
	 * 
	 * @param element a generic element
	 */
	void enqueue(E element);

	/**
	 * Removes and returns the element at the front of the Queue , an
	 * NoSuchElementException is thrown
	 * 
	 * @return element generic type
	 */
	E dequeue();

	/**
	 * Returns true if the Queue is empty
	 * 
	 * @return boolean true if the Queue is empty
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return int Returns the number of elements in the Queue
	 */
	int size();

	/**
	 * Sets the Queue's capacity
	 * 
	 * an 1IllegalArgumentException is thrown
	 * 
	 * @param capacity capacity to be set
	 */
	void setCapacity(int capacity);

}
