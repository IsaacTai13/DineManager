// ============================================================
// File: Order.java
// Location: src/model/Order.java
// Purpose: Order class storing complete order information
// ============================================================

package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Order Class
 * Contains order number, item list, total price, timestamp, priority, and status
 */
public class Order implements Comparable<Order> {
    private int orderNumber;                    // Order number
    private List<OrderItem> items;              // Order item list
    private double totalPrice;                  // Total price
    private LocalDateTime timestamp;            // Order timestamp
    private int priority;                       // Priority (1=Normal, 2=Delivery, 3=VIP)
    private String status;                      // Status (waiting/cooking/done)
    
    // Priority constants
    public static final int PRIORITY_NORMAL = 1;      // Normal order
    public static final int PRIORITY_DELIVERY = 2;    // Delivery order
    public static final int PRIORITY_VIP = 3;         // VIP order
    
    // Status constants
    public static final String STATUS_WAITING = "waiting";    // Waiting
    public static final String STATUS_COOKING = "cooking";    // Cooking
    public static final String STATUS_DONE = "done";          // Done
    
    /**
     * Full constructor
     */
    public Order(int orderNumber, List<OrderItem> items, int priority) {
        this.orderNumber = orderNumber;
        this.items = new ArrayList<>(items);  // Create a copy to avoid external modification
        this.priority = priority;
        this.timestamp = LocalDateTime.now();
        this.status = STATUS_WAITING;
        this.totalPrice = calculateTotalPrice();
    }
    
    /**
     * Simplified constructor (default normal priority)
     */
    public Order(int orderNumber, List<OrderItem> items) {
        this(orderNumber, items, PRIORITY_NORMAL);
    }
    
    // ==================== Getters ====================
    
    public int getOrderNumber() {
        return orderNumber;
    }
    
    public List<OrderItem> getItems() {
        return new ArrayList<>(items);  // Return a copy to protect internal data
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public String getStatus() {
        return status;
    }
    
    // ==================== Setters ====================
    
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // ==================== Order Operation Methods ====================
    
    /**
     * Add item to order
     */
    public void addItem(OrderItem item) {
        items.add(item);
        totalPrice = calculateTotalPrice();
    }
    
    /**
     * Remove item from order
     */
    public void removeItem(OrderItem item) {
        items.remove(item);
        totalPrice = calculateTotalPrice();
    }
    
    /**
     * Calculate total price
     */
    private double calculateTotalPrice() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    /**
     * Recalculate total price (call when items change)
     */
    public void recalculateTotalPrice() {
        this.totalPrice = calculateTotalPrice();
    }
    
    /**
     * Get item count
     */
    public int getItemCount() {
        return items.size();
    }
    
    /**
     * Get total quantity (considering quantity)
     */
    public int getTotalQuantity() {
        int total = 0;
        for (OrderItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }
    
    // ==================== Formatting Methods ====================
    
    /**
     * Format total price for display
     */
    public String getFormattedTotalPrice() {
        return String.format("$%.0f", totalPrice);
    }
    
    /**
     * Format timestamp for display
     */
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    /**
     * Get short time (only hour:minute)
     */
    public String getShortTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timestamp.format(formatter);
    }
    
    /**
     * Get priority text
     */
    public String getPriorityText() {
        switch (priority) {
            case PRIORITY_VIP:
                return "VIP";
            case PRIORITY_DELIVERY:
                return "Delivery";
            case PRIORITY_NORMAL:
            default:
                return "Normal";
        }
    }
    
    /**
     * Get status text
     */
    public String getStatusText() {
        switch (status) {
            case STATUS_WAITING:
                return "Waiting";
            case STATUS_COOKING:
                return "Cooking";
            case STATUS_DONE:
                return "Done";
            default:
                return status;
        }
    }
    
    /**
     * Get order summary (for display in list)
     */
    public String getSummary() {
        return String.format("Order #%03d - %s - %d items - %s", 
            orderNumber, 
            getPriorityText(), 
            getItemCount(), 
            getFormattedTotalPrice());
    }
    
    /**
     * toString method (detailed information)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Order #%03d ===\n", orderNumber));
        sb.append(String.format("Priority: %s\n", getPriorityText()));
        sb.append(String.format("Status: %s\n", getStatusText()));
        sb.append(String.format("Time: %s\n", getFormattedTimestamp()));
        sb.append("\nItems:\n");
        for (OrderItem item : items) {
            sb.append("  - ").append(item.toString()).append("\n");
        }
        sb.append(String.format("\nTotal: %s", getFormattedTotalPrice()));
        return sb.toString();
    }
    
    // ==================== Comparable Interface Implementation ====================
    
    /**
     * Comparison method for PriorityQueue sorting
     * Sorting rules:
     * 1. Higher priority comes first (3 > 2 > 1)
     * 2. If same priority, earlier time comes first
     */
    @Override
    public int compareTo(Order other) {
        // First compare priority (descending, higher priority first)
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }
        
        // If same priority, compare time (ascending, earlier time first)
        return this.timestamp.compareTo(other.timestamp);
    }
}