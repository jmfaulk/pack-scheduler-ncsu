package edu.ncsu.csc216.pack_scheduler.util;

/**
 * A stack is a data structure that models a stack of some objects. The last one added is on top, and it's the first one to leave the data
 * structure.
 * @author Jeremy Ignatowitz
 * @param <E> generic type
 */
public interface Stack<E> {

    /**
     * Adds a new element to the stack.
     * Cannot add an element if the stack is at capacity.
     * @param element the element to be added to the stack
     */
    void push(E element);
    
    /**
     * Get the element from the top of the stack, and remove it from the stack.
     * Cannot get an element from an empty stack.
     * @return top element of the stack
     */
    E pop();
    
    /**
     * Tells whether or not the stack is currently empty.
     * @return true if stack is empty
     */
    boolean isEmpty();
    
    /**
     * Gets the number of elements currently in the stack.
     * @return the size of the stack
     */
    int size();
    
    /**
     * Sets the capacity of the stack.
     * The new capacity cannot be negative, or less than the number of items in the stack already
     * @param capacity the new capacity for the stack
     */
    void setCapacity(int capacity);
}
