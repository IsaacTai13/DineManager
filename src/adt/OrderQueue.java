package adt;

import model.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Specialized Queue for Order objects
 * Wraps the generic MyQueue<Order> (FIFO) and provides order-specific operations
 * 
 * Note: Does not implement OrderQueueInterface - uses composition instead
 */
public class OrderQueue {
    
    // Internal generic FIFO queue (from scratch implementation)
    private MyQueue<Order> queue;
    
    /**
     * Constructor - Initialize empty order queue
     */
    public OrderQueue() {
        this.queue = new MyQueue<>();
    }
    
    
    // ============================================================
    // Basic Operations - Delegate to MyQueue
    // ============================================================
    
    public boolean enqueue(Order order) {
        boolean success = queue.enqueue(order);
        if (success) {
            System.out.println("Order enqueued: " + order.getSummary());
        }
        return success;
    }
    
    public Order dequeue() {
        Order order = queue.dequeue();
        if (order != null) {
            System.out.println("Order dequeued: " + order.getSummary());
        }
        return order;
    }
    
    public Order peek() {
        return queue.peek();
    }
    
    public int size() {
        return queue.size();
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public void clear() {
        queue.clear();
        System.out.println("Queue cleared");
    }
    
    public List<Order> getAllOrders() {
        return queue.getAllElements();
    }
    
    
    // ============================================================
    // Order-Specific Operations
    // ============================================================
    
    public List<Order> getOrdersByStatus(String status) {
        List<Order> result = new ArrayList<>();
        
        if (status == null) {
            return result;
        }
        
        for (Order order : queue.getAllElements()) {
            if (status.equalsIgnoreCase(order.getStatus())) {
                result.add(order);
            }
        }
        
        return result;
    }
    
    
    public List<Order> getOrdersByPriority(int priority) {
        List<Order> result = new ArrayList<>();
        
        for (Order order : queue.getAllElements()) {
            if (order.getPriority() == priority) {
                result.add(order);
            }
        }
        
        return result;
    }
    
    
    public Order findOrderByNumber(int orderNumber) {
        for (Order order : queue.getAllElements()) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }
    
    
    public boolean removeOrder(int orderNumber) {
        // For FIFO queue, we need to rebuild without the target order
        List<Order> allOrders = new ArrayList<>(queue.getAllElements());
        Order toRemove = null;
        
        for (Order order : allOrders) {
            if (order.getOrderNumber() == orderNumber) {
                toRemove = order;
                break;
            }
        }
        
        if (toRemove != null) {
            // Rebuild queue without the removed order
            queue.clear();
            for (Order order : allOrders) {
                if (order.getOrderNumber() != orderNumber) {
                    queue.enqueue(order);
                }
            }
            
            System.out.println("Order removed: #" + orderNumber);
            return true;
        }
        
        System.err.println("Error: Order #" + orderNumber + " not found");
        return false;
    }
    
    
    public boolean updateOrderStatus(int orderNumber, String newStatus) {
        Order order = findOrderByNumber(orderNumber);
        
        if (order != null) {
            String oldStatus = order.getStatus();
            order.setStatus(newStatus);
            System.out.println("Order #" + orderNumber + " status updated: " + 
                             oldStatus + " → " + newStatus);
            return true;
        }
        
        System.err.println("Error: Order #" + orderNumber + " not found");
        return false;
    }
    
    
    // ============================================================
    // Additional Helper Methods
    // ============================================================
    
    /**
     * Get count of orders by status
     */
    public int countOrdersByStatus(String status) {
        return getOrdersByStatus(status).size();
    }
    
    /**
     * Get count of orders by priority
     */
    public int countOrdersByPriority(int priority) {
        return getOrdersByPriority(priority).size();
    }
    
    /**
     * Check if an order exists
     */
    public boolean containsOrder(int orderNumber) {
        return findOrderByNumber(orderNumber) != null;
    }
    
    /**
     * Get the next order number that will be processed
     */
    public int getNextOrderNumber() {
        Order next = peek();
        return (next != null) ? next.getOrderNumber() : -1;
    }
    
    /**
     * Print all orders in the queue
     */
    public void printAllOrders() {
        System.out.println("\n=== Order Queue (FIFO) ===");
        System.out.println("Total orders: " + size());
        
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        
        List<Order> orders = queue.getAllElements();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println((i + 1) + ". " + order.getSummary() + 
                             " [" + order.getStatusText() + "]");
        }
        System.out.println("=======================\n");
    }
    
    /**
     * Get queue statistics
     */
    public String getQueueStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Queue Statistics (FIFO):\n");
        stats.append("Total orders: ").append(size()).append("\n");
        stats.append("Waiting: ").append(countOrdersByStatus(Order.STATUS_WAITING)).append("\n");
        stats.append("Cooking: ").append(countOrdersByStatus(Order.STATUS_COOKING)).append("\n");
        stats.append("Done: ").append(countOrdersByStatus(Order.STATUS_DONE)).append("\n");
        stats.append("\n");
        stats.append("Normal priority: ").append(countOrdersByPriority(Order.PRIORITY_NORMAL)).append("\n");
        stats.append("Delivery priority: ").append(countOrdersByPriority(Order.PRIORITY_DELIVERY)).append("\n");
        stats.append("VIP priority: ").append(countOrdersByPriority(Order.PRIORITY_VIP));
        
        return stats.toString();
    }
    
    /**
     * Visualize the queue structure
     */
    public void visualizeQueue() {
        queue.visualize();
    }
}