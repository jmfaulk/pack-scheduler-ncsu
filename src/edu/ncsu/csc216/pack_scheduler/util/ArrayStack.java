/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Implements the Stack interface using an ArrayList.
 * @author Jeremy Ignatowitz
 * @param <E> generic type
 */
public class ArrayStack<E> implements Stack<E> {
    
    /** ArrayList to keep track of the data in the stack */
    private ArrayList<E> list = new ArrayList<E>();
    /** Capacity of the stack */
    private int capacity;
    
    /**
     * Constructs a new ArrayStack with the given capacity.
     * @param capacity the capacity of the stack
     */
    public ArrayStack(int capacity) {
        setCapacity(capacity);
    }

    /**
     * Adds a new element to the stack.
     * Cannot add an element if the stack is at capacity.
     * @param element the element to be added to the stack
     */
    @Override
    public void push(E element) {
        if(size() < capacity) {
            list.add(element);
        }
        else
            throw new IllegalArgumentException("Stack is at capacity");
    }

    /**
     * Get the element from the top of the stack, and remove it from the stack.
     * Cannot get an element from an empty stack.
     * @return top element of the stack
     */
    @Override
    public E pop() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        E result = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return result;
    }

    /**
     * Tells whether or not the stack is currently empty.
     * @return true if stack is empty
     */
    @Override
    public boolean isEmpty() {
        if(size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Gets the number of elements currently in the stack.
     * @return the size of the stack
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Sets the capacity of the stack.
     * The new capacity cannot be negative, or less than the number of items in the stack already
     * @param capacity the new capacity for the stack
     */
    @Override
    public void setCapacity(int capacity) {
        if(capacity < 0 || capacity < size()) {
            throw new IllegalArgumentException("Invalid capacity");
        }
        this.capacity = capacity;
    }

}
