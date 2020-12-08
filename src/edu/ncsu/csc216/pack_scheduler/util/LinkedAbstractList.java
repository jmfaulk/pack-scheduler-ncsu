package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Custom implementation of a Linked List class extended from the AbstractList
 * class, with extended methods to add a ListNode at an index, remove a
 * ListNode, overwrite the data of a ListNode, and retrieve the data of a
 * ListNode. There is also a method to retrieve the current value for the max
 * number of nodes which can be added.
 * 
 * @author gwfringe
 *
 * @param <E> the type of variable which is being incorporated with the
 *            LinkedList
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** The first node of the associated list */
	private ListNode front;

	/** The last node of the associated list */
	private ListNode back;

	/** The current number of nodes in the list */
	private int size;

	/** The maximum number of nodes for the list */
	private int capacity;

	/**
	 * Constructor for a LinkedList, which sets values to their defaults (0 for
	 * size, and null for front), and checks to see if the capacity is valid before
	 * setting that to the passed value, if possible.
	 * 
	 * @param capacity the target capacity of the LinkedList
	 * @throws IllegalArgumentException if the target capacity is less than the size
	 *                                  of the list or 0
	 */
	public LinkedAbstractList(int capacity) {
		setCapacity(capacity);
		size = 0;
		front = null;
		back = front;
	}

	/**
	 * Method which ensures that a new target list capacity is feasible, and if it
	 * is, mutates the current capacity to the target value
	 * 
	 * @param cap the list capacity to be mutated to
	 */
	public void setCapacity(int cap) {
		if (cap < 0 || cap < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = cap;
	}

	/**
	 * Method which changes the data of a ListNode at the specified index, if
	 * possible, and returns the information which was overwritten
	 * 
	 * @param index   the index of the ListNode to be altered
	 * @param element the element to override the current ListNode data
	 * @return the data of the ListNode which was overridden
	 * @throws NullPointerException      if the element to add is null
	 * @throws IndexOutOfBoundsException if the target index is outside of
	 *                                   acceptable values
	 * @throws IllegalArgumentException  if the data being added is a duplicate of
	 *                                   another node in the list
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size() || front == null) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size(); i++) {
			if (element.equals(get(i))) {
				throw new IllegalArgumentException();
			}
		}
		E toReturn = null;
		ListNode current = front;
		if (index == 0) {
			toReturn = this.get(0);
			current.data = element;
			return toReturn;
		}
		for (int i = 0; i < index; i++) { // on index
			current = current.next;
		}
		toReturn = this.get(index);
		current.next.data = element;
		return toReturn;
	}

	/**
	 * Method which creates a new ListNode at the specified index, if possible.
	 * 
	 * @param index   the index of the ListNode to be altered
	 * @param element the element to override the current ListNode data
	 * @throws NullPointerException      if the element to add is null
	 * @throws IndexOutOfBoundsException if the target index is outside of
	 *                                   acceptable values
	 * @throws IllegalArgumentException  if the data being added is a duplicate of
	 *                                   another node in the list, or if the size of
	 *                                   the list is at capacity
	 */
	@Override
	public void add(int index, E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size(); i++) {
			if (element.equals(get(i))) {
				throw new IllegalArgumentException();
			}
		}
		if (index == 0) { // front of node; add here; first node
			front = new ListNode(element, front);
			// front.next =
//			back = front; // ?? back shouldnt change??
			size++;
			return;
		} else if (index == size() && size() == 1) {// add second element to the list
		    front.next = new ListNode(element);
		    back = front.next;
//			back.next = new ListNode(element, null);
//			back = back.next;
			size++;
			return;
		} else if(index == size()) { //add to the end of the list
		    back.next = new ListNode(element);
		    back = back.next;
            size++;
            return;
		} else{ //in the middle
			
			ListNode current = front; // temp pointer
			for (int i = 0; i < index - 1; i++) { // move pointer (current) to one before index
				current = current.next;
			}
			ListNode addition = new ListNode(element, current.next); // set node to the next spot (at index
			//we have to set node behind it to point to it
			current.next = addition; 
			size++;
			current = front; // move current back to front
			for (int i = 0; i < size() - 1; i++) { //move back to end of list
				current = current.next;
			}
			back = current.next;
		}
	}

	/**
	 * Method which removes the ListNode at a specified index from the LinkedList,
	 * if possible, and returns the data of the ListNode which has been removed.
	 * 
	 * @param index the index of the ListNode to be removed
	 * @return the data element associated with the removed ListNode
	 * @throws IndexOutOfBoundsException if the intended index is outside of
	 *                                   acceptable values
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		E removed = null;
		if (index == 0) {
			removed = front.data;
			front = front.next;
			size--;
			return removed;
		} else {
			ListNode current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next; // go to one before index
			}
			back = current;
			removed = current.next.data;
			current.next = current.next.next;
			size--;
//			back = front;
//			if (size > 1) {
//				for (int i = 0; i < size() - 1; i++) {
//					back = back.next; //go tolast element
//				}
//			}
			return removed;
		}
	}

//	/** Method that returns data in the back.
//	 * Only for testing , only uncomment if needed for testing
//	 * 
//	 * @return returns data in the back
//	 */
//	public E getBack() {
//		return back.data;
//	}

	/**
	 * Method which finds a ListNode at a specified index, if possible, and
	 * retrieves the element associated with the ListNode, and returns it
	 * 
	 * @param index the index of the ListNode to be observed
	 * @return the data element associated with the observed ListNode
	 * @throws IndexOutOfBoundsException if the intended index is outside of
	 *                                   acceptable values
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		if (front == null) {
			return null;
		}
		if (index == 0) {
			return current.data;
		}
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Method which returns the current value of the size variable
	 * 
	 * @return the current size of the LinkedList
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Inner Class of ListNodes, which are what a LinkedList is comprised of.
	 * Contains constructors for both next specific and next non-specific ListNodes
	 * 
	 * @author gwfringe
	 */
	private class ListNode {
		private E data;
		private ListNode next;

		/**
		 * Constructor to create a ListNode with a subsequent node which is not null
		 * 
		 * @param data the element of data associated with the node
		 * @param next the next node in the list
		 */
		private ListNode(E data, ListNode next) {
			this.data = (E) data;
			this.next = next;
		}

		/**
		 * Constructor to create a ListNode without a subsequent node (null)
		 * 
		 * @param data the element of data associated with the node
		 */
		private ListNode(E data) {
			this.data = (E) data;
			this.next = null;
		}

	}

}
