/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;


/**
 * An implementation of LinkedLists that uses recursive design patterns.
 * @author Jeremy Ignatowitz
 * @param <E> generic type parameter for list
 */
public class LinkedListRecursive<E> {

    /** number of elements in the list*/
    private int size;
    /** front node of the LinkedList; first element in the list */
    private ListNode front;
    
    /**
     * Constructs a new recursive LinkedList. Front is set to null, and the size is 0, as the list is empty.
     */
    public LinkedListRecursive() {
        front = null;
        size = 0;
    }
    
    /**
     * Tells whether or not the list is empty. A list is empty if its size is zero.
     * @return true if list is empty, otherwise false
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Returns the size of the list
     * @return the size of the list
     */
    public int size() {
        return size;
    }
   
    /**
     * Attempts to add a new element to the list. This will just add the element to the end, in a new ListNode.
     * If the list is empty, it's trivial to just construct front as a new ListNode. Otherwise, the work is passed on
     * to the add method in the ListNode private class.
     * Returns true if the element can be added. Doesn't return false; instead throws an IllegalArgumentException if the
     * passed-in element is a duplicate.
     * @param element the new element to be added to the list
     * @return true if the element is added at the end of the list
     */
    public boolean add(E element) {
        if(contains(element)) {
            throw new IllegalArgumentException();
        }
        else if(isEmpty()) {
            front = new ListNode(element, null);
            size++;
            return true;
        }
        else {
            front.add(element);
            return true;
        }
    }
    
    /**
     * Attempts to add a new element to the list, as the specified index.
     * It's trivial to add an element to the front of the list, by just constructing a new ListNode with the data, and setting its next
     * node to the front node of the list. Otherwise, work is passed onto the add(int, E) method of the private class.
     * The new element cannot be a duplicate, and the index cannot be outside of the list's bounds.
     * @param index the index to add the element to
     * @param element the new data to put into a list node
     */
    public void add(int index, E element) {
        if(contains(element)) {
            throw new IllegalArgumentException();
        }
        if(index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }
        if(element == null) {
            throw new NullPointerException();
        }
        if(index == 0) {
            ListNode temp = front;
            front = new ListNode(element, temp);
            size++;
        }
        else {
            front.add(index, element);
        }
        
    }
    
    /**
     * Gets the data situated at the passed-in index. This will return whatever object is stored in the index specified.
     * The index cannot be outside of the bounds of the list.
     * This method delegates all work to the get method of the private class; the public form only does error checking.
     * @param index index to get data from
     * @return the data stored at the specified index
     */
    public E get(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        else return front.get(index);
    }
    
    /**
     * Attempts to remove the specified index from the list. Because there are no duplicates allowed, the element will not exist in the list
     * after this method is called.
     * Element cannot be null, and the list cannot be empty, for this method to run.
     * It is trivial to remove an element from the front. If the element isn't at the front, the work is delegated to the remove(E) method
     * in the private class.
     * @param element element to remove from the list
     * @return true if the element is removed, otherwise false
     */
    public boolean remove(E element) {
        if(element == null) {
            return false;
        }
        if(isEmpty()) {
            return false;
        }
        if(front.data.equals(element)) {
            front = front.next;
            size--;
            return true;
        }
        else return front.remove(element);
    }
    
    /**
     * Attempts to remove the element at the specified index.
     * The index cannot be outside of the bounds of the list.
     * It is trivial to remove an element from the front of the list. If the index isn't the first index in the list, work is delegated to 
     * the private class's remove(int) method.
     * @param index index to remove from
     * @return the data from the node removed from the list
     */
    public E remove(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if(index == 0) {
            ListNode temp = front;
            front = front.next;
            size--;
            return temp.data;
        }
        else return front.remove(index);
    }
    
    /**
     * Sets the element at the specified index to the specified element
     * @param index index sent in 
     * @param element the value to set to
     * @return returns the Element set 
     */
    public E set(int index, E element) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        else return front.set(index, element);
    }
    
    /**
     * Checks if contains an element
     * @param element element value we are looking for
     * @return boolean true if it does contain
     */
    public boolean contains(E element) {
        if(isEmpty()) {
            return false;
        }
        else return front.contains(element);
    }
 
    /**
     * Node class used for out linked list
     * @author Jared Faulk
     *
     */
    private class ListNode {
        public E data;
        public ListNode next;
        
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }
        
        public void add(int index, E element) {
            if(next == null) {
                next = new ListNode(element, null);
                size++;
            }
            if(index == 1) {
                next = new ListNode(element, next);
                size++;
            }
            else {
                index--;
                next.add(index, element);
            }
            
        }
        
        public void add(E element) {
            if(next == null) {
                next = new ListNode(element, null);
                size++;
            }
            else {
                next.add(element);
            }
        }
        
        public E get(int index) {
            if(index == 0) {
                return data;
            }
            else {
                return next.get(index - 1);
            }
        }
        
        public E remove(int index) {
            if(next.next == null) {
                ListNode temp = next;
                next = null;
                size--;
                return temp.data;
            }
            else if(index == 1) {
                ListNode temp = next.next;
                next = next.next;
                size--;
                return temp.data;
            } 
            else {
                return next.remove(index - 1);
            }
        }
        public boolean remove(E element) {
            if(next == null) {
                return false;
            }
            else if(next.data.equals(element)) {
                next = next.next;
                size--;
                return true;
            }
            else return next.remove(element);
        }
        
        public E set(int index, E element) {
            if(index == 0) {
                E temp = data;
                data = element;
                return temp;
            } 
            else return next.set(index - 1, element);
        }
        
        public boolean contains(E element) {
            if(next == null) {
                if(data == element) {
                    return true;
                }
                return false;
            }
            else if(data == element) {
                return true;
            }
            else {
                return next.contains(element);
            }
        }
    }
}
