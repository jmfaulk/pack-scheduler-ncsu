/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * An arrayQueue to hold elements for our scheduling purposes
 * @author Jared Faulk
 * @param <E> element
 *
 */
public class ArrayQueue<E> implements Queue<E> {

	private ArrayList<E> arrayQueue;

	private int size;

	private int capacity;

	/**
	 * Constructor, creates an empty arrayList for arrayQueue
	 * @param capacity is the capacity of the queue
	 */
	public ArrayQueue(int capacity) {

		this.setArrayQueue(new ArrayList<E>());
		setCapacity(capacity);
	}

	/**
	 * adds an element to the queue
	 */
	@Override
	public void enqueue(E element) {

		if (size < capacity) {
			arrayQueue.add(element);
			size++;
		} else
			throw new IllegalArgumentException("capacity is reached");

	}

	/**
	 * removes an element from the queue at the front
	 */
	@Override
	public E dequeue() {
		if(size == 0) {
			throw new NoSuchElementException();
		}
		E element = arrayQueue.get(0); //store
		arrayQueue.remove(0); //remove
		size--; 
		return element; 
	
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Capcity setter
	 * @param capacity the capacity int to set 
	 * @throws IllegalArgumentException if sent in int is less than 0 or less than size
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < size || capacity < 0) {
			throw new IllegalArgumentException("Invalid capacity");
		}
		this.capacity = capacity;

	}

	/**
	 * gets the capacity
	 * @return the capacity
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Getter for arrayQueue
	 * @return the arrayQueue
	 */
	public ArrayList<E> getArrayQueue() {
		return arrayQueue;
	}

	/**
	 * Setter for arrayQueue
	 * @param arrayQueue the arrayQueue to set
	 */
	public void setArrayQueue(ArrayList<E> arrayQueue) {
		this.arrayQueue = arrayQueue;
	}
	
	/** Checks if the queue contains the given element
	 * 
	 * @param element is the element being matched with other elements
	 * @return returns true if an equal element already exists in the queue.
	 */
	public boolean contains(E element) {
		for (int i = 0; i < size(); i++) {
			if (element.equals(arrayQueue.get(i))) {
				return true;
			}
		}
		return false;
	}



}
