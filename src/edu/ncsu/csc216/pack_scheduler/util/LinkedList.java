package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A custom LinkedList class to be used for faculty listings.
 * @author Jeremy Ignatowitz
 *
 * @param <E> generic type
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
    
    /** front element of the list */
    ListNode front;
    /** back element of the list */
    ListNode back;
    /** number of elements in the list */
    int size;
    
    /**
     * Constructs a new LinkedList.
     * Front is the first element in the list, and back is the last. This creates new ListNodes that point to each other, linking the list
     * from both ends. Size is set to 0.
     */
    public LinkedList() {
        front = new ListNode(null);
        back = new ListNode(null);
        front.next = back;
        back.prev = front;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in list
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * Replaces the element at the specified index with the one passed in.
     * No duplicate elements are allowed in the list.
     */
    @Override
    public E set(int index, E element) {
        if(contains(element)) {
            throw new IllegalArgumentException();
        }
        return super.set(index, element);
    }

    /**
     * Returns the iterator that the List uses to iterate itself.
     * @param index the index the iterator starts at
     * @return the iterator that the List uses to iterate itself
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index);
    }
    
    /**
     * Adds a new element at the specified index.
     * No duplicate elements are allowed in the list.
     */
    @Override
    public void add(int index, E element) {
        if(contains(element)) {
            throw new IllegalArgumentException();
        }
        super.add(index, element);
    }

    /**
     * A ListNode is an element of a linked list. It holds a piece of data, as well as references to the next element in the list and the previous
     * element in the list.
     * @author Jeremy Ignatowitz
     */
    private class ListNode {
        public E data;
        public ListNode next;
        public ListNode prev;
        
        public ListNode(E data) {
            this.data = data;
        }
        
        public ListNode(E data,  ListNode prev, ListNode next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * An iterator for LinkedLists. Capable of stepping through a list forward and backward, as well as manipulating the list itself.
     * @author Jeremy Ignatowitz
     */
    private class LinkedListIterator implements ListIterator<E> {
        ListNode previous;
        ListNode next;
        int previousIndex;
        int nextIndex;
        ListNode lastRetrieved;
        
        public LinkedListIterator(int index) {
            if(index < 0 || index > size()) {
                throw new IndexOutOfBoundsException();
            }
            else {
                next = front.next;
                previous = next.prev;
                while(index > nextIndex()) {
                    next();
                }
                while(index < previousIndex()) {
                    previous();
                }
                previousIndex = index - 1;
                nextIndex = index;
                lastRetrieved = null;
            }
        }
        
        public boolean hasNext() {
            return next.data != null;
        }
        
        public boolean hasPrevious() {
            return previous.data != null;
        }
        
        public int nextIndex() {
            return nextIndex;
        }
        
        public int previousIndex() {
            return previousIndex;
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            E toReturn = next.data;
            lastRetrieved = next;
            next = next.next;
            previous = previous.next;
            nextIndex++;
            previousIndex++;
            return toReturn;
        }

        @Override
        public E previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException();
            }
            E toReturn = previous.data;
            lastRetrieved = previous;
            next = next.prev;
            previous = previous.prev;
            nextIndex--;
            previousIndex--;
            return toReturn;
        }

        @Override
        public void remove() {
            if(lastRetrieved == null) { 
                throw new IllegalStateException();
            }
            
            lastRetrieved.prev.prev = lastRetrieved.next;
            size--;
        }

        @Override
        public void set(E e) {
            if(lastRetrieved == null) {
                throw new IllegalStateException();
            }
            if(e == null) {
                throw new NullPointerException();
            }
            
            lastRetrieved.data = e;
        }

        @Override
        public void add(E e) {
            if(e == null) {
                throw new NullPointerException("Element cannot be null");
            }
            ListNode toAdd = new ListNode(e, previous, next);
            next.prev = toAdd;
            previous.next = toAdd;
            size++;
            nextIndex++;
            previousIndex++;
            lastRetrieved = null;
        }
    }
}
