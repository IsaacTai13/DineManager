package adt;

import model.Order;
import java.util.List;

/**
 * ADT Interface for Order Queue
 * This interface defines the operations for managing orders using a queue structure
 * Supports both FIFO (regular queue) and Priority Queue operations
 */
public interface OrderQueueInterface {
    
    /**
     * Add an order to the queue
     * @param order - the Order object to add
     * @return true if successfully added, false otherwise
     */
    boolean enqueue(Order order);
    
    /**
     * Remove and return the next order from the queue
     * For regular queue: FIFO (First In First Out)
     * For priority queue: highest priority first
     * @return the next Order object, or null if queue is empty
     */
    Order dequeue();
    
    /**
     * View the next order without removing it
     * @return the next Order object, or null if queue is empty
     */
    Order peek();
    
    /**
     * Get the number of orders in the queue
     * @return the size of the queue
     */
    int size();
    
    /**
     * Check if the queue is empty
     * @return true if empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Clear all orders from the queue
     */
    void clear();
    
    /**
     * Get all orders in the queue (without removing them)
     * @return a List of all Order objects
     */
    List<Order> getAllOrders();
    
    /**
     * Get orders by status
     * @param status - the status to filter by (waiting/cooking/done)
     * @return a List of Order objects with the specified status
     */
    List<Order> getOrdersByStatus(String status);
    
    /**
     * Get orders by priority
     * @param priority - the priority level to filter by
     * @return a List of Order objects with the specified priority
     */
    List<Order> getOrdersByPriority(int priority);
    
    /**
     * Find an order by order number
     * @param orderNumber - the order number to search for
     * @return the Order object if found, null otherwise
     */
    Order findOrderByNumber(int orderNumber);
    
    /**
     * Remove a specific order from the queue
     * @param orderNumber - the order number to remove
     * @return true if successfully removed, false if not found
     */
    boolean removeOrder(int orderNumber);
    
    /**
     * Update order status
     * @param orderNumber - the order number to update
     * @param newStatus - the new status
     * @return true if successfully updated, false if not found
     */
    boolean updateOrderStatus(int orderNumber, String newStatus);
}