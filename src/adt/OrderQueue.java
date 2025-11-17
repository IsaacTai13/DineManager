package adt;

import model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue; // TODO: Do not use built-in util or collections

/**
 * ADT Implementation for Order Queue
 * Uses PriorityQueue to automatically sort orders by priority and time
 * Higher priority orders are processed first
 */
public class OrderQueue implements OrderQueueInterface {
    
    // TODO: Do not use: Internal priority queue using Java's PriorityQueue
    // Orders are automatically sorted by priority (high to low) and time (early to late)
    private PriorityQueue<Order> queue;
    
    /**
     * Constructor - Initialize empty priority queue
     */
    public OrderQueue() {
        this.queue = new PriorityQueue<>();
    }
    
    /**
     * Constructor with initial capacity
     * @param initialCapacity - the initial capacity of the queue
     */
    public OrderQueue(int initialCapacity) {
        this.queue = new PriorityQueue<>(initialCapacity);
    }
    
    
    @Override
    public boolean enqueue(Order order) {
        if (order == null) {
            System.err.println("Error: Cannot enqueue null order");
            return false;
        }
        
        boolean success = queue.offer(order);
        
        if (success) {
            System.out.println("Order enqueued: " + order.getSummary());
        }
        
        return success;
    }
    
    
    @Override
    public Order dequeue() {
        Order order = queue.poll();
        
        if (order != null) {
            System.out.println("Order dequeued: " + order.getSummary());
        }
        
        return order;
    }
    
    
    @Override
    public Order peek() {
        return queue.peek();
    }
    
    
    @Override
    public int size() {
        return queue.size();
    }
    
    
    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    
    @Override
    public void clear() {
        queue.clear();
        System.out.println("Queue cleared");
    }
    
    
    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(queue);
    }
    
    
    @Override
    public List<Order> getOrdersByStatus(String status) {
        List<Order> result = new ArrayList<>();
        
        if (status == null) {
            return result;
        }
        
        for (Order order : queue) {
            if (status.equalsIgnoreCase(order.getStatus())) {
                result.add(order);
            }
        }
        
        return result;
    }
    
    
    @Override
    public List<Order> getOrdersByPriority(int priority) {
        List<Order> result = new ArrayList<>();
        
        for (Order order : queue) {
            if (order.getPriority() == priority) {
                result.add(order);
            }
        }
        
        return result;
    }
    
    
    @Override
    public Order findOrderByNumber(int orderNumber) {
        for (Order order : queue) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }
    
    
    @Override
    public boolean removeOrder(int orderNumber) {
        Order toRemove = findOrderByNumber(orderNumber);
        
        if (toRemove != null) {
            boolean success = queue.remove(toRemove);
            if (success) {
                System.out.println("Order removed: #" + orderNumber);
            }
            return success;
        }
        
        System.err.println("Error: Order #" + orderNumber + " not found");
        return false;
    }
    
    
    @Override
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
    // ADDITIONAL HELPER METHODS
    // ============================================================
    
    /**
     * Get orders sorted by priority and time
     * @return List of orders in priority order (high to low)
     */
    public List<Order> getOrdersInPriorityOrder() {
        List<Order> sorted = new ArrayList<>(queue);
        sorted.sort(null); // Use natural ordering (Order implements Comparable)
        return sorted;
    }
    
    /**
     * Get count of orders by status
     * @param status - the status to count
     * @return number of orders with the specified status
     */
    public int countOrdersByStatus(String status) {
        return getOrdersByStatus(status).size();
    }
    
    /**
     * Get count of orders by priority
     * @param priority - the priority level to count
     * @return number of orders with the specified priority
     */
    public int countOrdersByPriority(int priority) {
        return getOrdersByPriority(priority).size();
    }
    
    /**
     * Check if an order exists
     * @param orderNumber - the order number to check
     * @return true if order exists in queue
     */
    public boolean containsOrder(int orderNumber) {
        return findOrderByNumber(orderNumber) != null;
    }
    
    /**
     * Get the next order number that will be processed
     * @return order number of the next order, or -1 if queue is empty
     */
    public int getNextOrderNumber() {
        Order next = peek();
        return (next != null) ? next.getOrderNumber() : -1;
    }
    
    /**
     * Print all orders in the queue (for debugging)
     */
    public void printAllOrders() {
        System.out.println("\n=== Order Queue ===");
        System.out.println("Total orders: " + size());
        
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        
        List<Order> orders = getOrdersInPriorityOrder();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println((i + 1) + ". " + order.getSummary() + 
                             " [" + order.getStatusText() + "]");
        }
        System.out.println("===================\n");
    }
    
    /**
     * Get queue statistics
     * @return String containing queue statistics
     */
    public String getQueueStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Queue Statistics:\n");
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
}