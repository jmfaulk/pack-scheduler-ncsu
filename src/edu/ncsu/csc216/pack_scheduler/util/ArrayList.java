/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A custom ArrayList of generic objects. Does not allow null objects or duplicate objects to be added to the list.
 * @author Jeremy Ignatowitz
 * @author Gage Fringer
 * @author Ethan Taylor
 * @param <E> generic type of object
 */
public class ArrayList<E> extends AbstractList<E> {
    
    /** The initial size of the array */
    private static final int INIT_SIZE = 10;
    /** the array to use for the list */
    private E[] list;
    /** the size of the ArrayList */
    private int size;
    
    /**
     * Constructs a new ArrayList.
     * Creates an array of E with INIT_SIZE elements, and sets size to 0.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        list = (E[]) new Object[INIT_SIZE];
        size = 0;
    }

    /**
     * Adds an element to the ArrayList at the specified index.
     * The object cannot be null or a duplicate of another object in the list, and the index must be between 0 and the size of the ArrayList.
     * @param index the index to add the new object to
     * @param element the object to be added to the list
     * @throws NullPointerException if the element to be added is null
     * @throws IllegalArgumentException if the element to be added matches an element in the array already
     * @throws IndexOutOfBoundsException if the element is attempted to be added at an index outside of the
     * 			acceptable range
     */
    public void add(int index, E element) {
        if(element == null) {
            throw new NullPointerException();
        }
        
        for(int i = 0; i < size(); i++) {
            if(element.equals(get(i))) {
                throw new IllegalArgumentException();
            }
        }
        
        if(index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        
        if(size() == list.length) {
            growArray();
        }
        
        for(int i = list.length - 2; i >= index; i--) {
            list[i + 1] = list[i];
        }
        list[index] = element;
        size++;
    }
    
    /**
     * Grows the length of the array.
     * This will double the length of the array by making a new array of double length, adding each element of the original array to it,
     * then setting list equal to the temporary array.
     */
    @SuppressWarnings("unchecked")
    private void growArray() {
        E[] tempList = (E[]) new Object[2 * list.length];
        for(int i = 0; i < list.length; i++) {
            tempList[i] = list[i];
        }
        
        list = tempList;
    }
    
    /**
     * Sets the element at the particular index to the object contained in element.
     * This will replace the object at the index with the given one, and return the original object.
     * @param index the index to replace
     * @param element the new element to be put in the list
     * @return the original element of the list at the index
     * @throws NullPointerException if the element to be added is null
     * @throws IllegalArgumentException if the element to be added matches an element in the array already
     * @throws IndexOutOfBoundsException if the element is attempted to be added at an index outside of the
     * 			acceptable range
     */
    public E set(int index, E element) { 
        if(element == null) {
            throw new NullPointerException();
        } else if(index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        
        for(int i = 0; i < size(); i++) {
            if(get(i).equals(element)) {
                throw new IllegalArgumentException();
            }
        }
        
        E oldValue = get(index);
        list[index] = element;
        
        return oldValue;
    }
    
    /**
     * Method to remove an element from the ArrayList
     * @param index the index of the element in the ArrayList to be removed
     * @return the element which has been removed from the ArrayList
     * @throws IndexOutOfBoundsException if the element is attempted to be added at an index outside of the
     * 			acceptable range
     */
    @Override
    public E remove(int index) {
    	if(index < 0 || index >= size()) {
    		throw new IndexOutOfBoundsException();
    	}
    	
    	E removed = list[index];
    	
    	for(int i = index + 1; i < size(); i++) {
    		list[i - 1] = list[i];
    	}
    	
    	list[size() - 1] = null;
    	
    	size--;
    	
    	return removed;
    }
    
    /**
     * Gets the element of the list at the specified index.
     * The index cannot be less than zero, or greater than or equal to the list's size.
     * @param index the index of the element to get from the list
     * @return the element of the list at the specified index
     * @throws IndexOutOfBoundsException if the element is attempted to be added at an index outside of the
     * 			acceptable range
     */
    @Override
    public E get(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return list[index];
    }

    /**
     * Get the current size of the list.
     * @return the number of objects curently in the list
     */
    @Override
    public int size() {
        return size;
    }

}
