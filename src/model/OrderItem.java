// ============================================================
// File: OrderItem.java
// Location: src/model/OrderItem.java
// Purpose: Order item class representing a single dish and quantity in an order
// ============================================================

package model;

/**
 * OrderItem Class
 * Contains a MenuItem and its quantity
 */
public class OrderItem {
    private MenuItem menuItem;  // Menu item
    private int quantity;       // Quantity 
    
    /**
     * Constructor
     */
    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }
    
    // ==================== Getters ====================
    
    public MenuItem getMenuItem() {
        return menuItem;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    // ==================== Setters ====================
    
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    // ==================== Calculation Methods ====================
    
    /**
     * Calculate subtotal (price × quantity)
     */
    public double getSubtotal() {
        return menuItem.getPrice() * quantity;
    }
    
    /**
     * Format subtotal for display
     */
    public String getFormattedSubtotal() {
        return String.format("$%.0f", getSubtotal());
    }
    
    /**
     * toString method
     */
    @Override
    public String toString() {
        return String.format("%s x%d = %s", 
            menuItem.getName(), 
            quantity, 
            getFormattedSubtotal());
    }
}