package adt;

import java.util.List;

/**
 * Generic ADT Interface for Queue
 * This interface defines the operations for managing elements using a queue structure
 * Follows FIFO (First In First Out) principle
 * 
 * @param <T> The type of elements held in this queue
 */
public interface QueueInterface<T> {
    
    /**
     * Add an element to the rear of the queue
     * @param element - the element to add
     * @return true if successfully added, false otherwise
     */
    boolean enqueue(T element);
    
    /**
     * Remove and return the element at the front of the queue
     * FIFO: First In First Out - removes the oldest element
     * @return the front element, or null if queue is empty
     */
    T dequeue();
    
    /**
     * View the element at the front without removing it
     * @return the front element, or null if queue is empty
     */
    T peek();
    
    /**
     * Get the number of elements in the queue
     * @return the size of the queue
     */
    int size();
    
    /**
     * Check if the queue is empty
     * @return true if empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Clear all elements from the queue
     */
    void clear();
    
    /**
     * Get all elements in the queue (without removing them)
     * Returns elements in order from front to rear
     * @return a List of all elements in FIFO order
     */
    List<T> getAllElements();
    
    /**
     * Check if an element exists in the queue
     * @param element - the element to check
     * @return true if element exists in queue
     */
    boolean contains(T element);
}