package adt;

/**
 * ADT Interface for Linked List
 * This interface defines the operations for a linked list data structure
 * 
 * @param <T> The type of elements held in this list
 */
public interface LinkedListInterface<T> {
    
    /**
     * Add an element to the end of the list
     * @param element - the element to add
     * @return true if successfully added
     */
    boolean add(T element);
    
    /**
     * Add an element at specific index
     * @param index - position to insert
     * @param element - the element to add
     * @throws IndexOutOfBoundsException if index is out of range
     */
    void add(int index, T element);
    
    /**
     * Get element at specific index
     * @param index - position to retrieve
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if index is out of range
     */
    T get(int index);
    
    /**
     * Remove element at specific index
     * @param index - position to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    T remove(int index);
    
    /**
     * Remove specific element (first occurrence)
     * @param element - the element to remove
     * @return true if element was found and removed
     */
    boolean remove(T element);
    
    /**
     * Check if list contains an element
     * @param element - the element to search for
     * @return true if element exists in list
     */
    boolean contains(T element);
    
    /**
     * Get the size of the list
     * @return number of elements
     */
    int size();
    
    /**
     * Check if list is empty
     * @return true if list has no elements
     */
    boolean isEmpty();
    
    /**
     * Clear all elements from the list
     */
    void clear();
    
    /**
     * Get first element
     * @return first element, or null if list is empty
     */
    T getFirst();
    
    /**
     * Get last element
     * @return last element, or null if list is empty
     */
    T getLast();
    
    /**
     * Add element at the beginning
     * @param element - the element to add
     */
    void addFirst(T element);
    
    /**
     * Add element at the end
     * @param element - the element to add
     */
    void addLast(T element);
    
    /**
     * Remove first element
     * @return the removed element, or null if list is empty
     */
    T removeFirst();
    
    /**
     * Remove last element
     * @return the removed element, or null if list is empty
     */
    T removeLast();
}