// ============================================================
// File: MenuItem.java
// Location: src/model/MenuItem.java
// Purpose: Menu item class storing information of a single dish
// ============================================================

package model;

import java.util.Objects;

/**
 * MenuItem Class
 * Stores basic information of a menu item: ID, name, price, category, description
 */
public class MenuItem {
    private String id;              // Unique identifier for the menu item
    private String name;            // Name of the menu item
    private double price;           // Price
    private String category;        // Category (Main Dish/Beverage/Dessert)
    private String description;     // Description (optional)
    private boolean isAvailable;    // Availability status
    
    /**
     * Full constructor
     */
    public MenuItem(String id, String name, double price, String category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.isAvailable = true;  // Default is available
    }
    
    /**
     * Simplified constructor (without description)
     */
    public MenuItem(String id, String name, double price, String category) {
        this(id, name, price, category, "");
    }
    
    // ==================== Getters ====================
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    // ==================== Setters ====================
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    // ==================== Other Methods ====================
    
    /**
     * Format price for display
     */
    public String getFormattedPrice() {
        return String.format("$%.0f", price);
    }
    
    /**
     * toString method for display and debugging
     */
    @Override
    public String toString() {
        return String.format("%s - %s (%s)", name, getFormattedPrice(), category);
    }
    
    /**
     * equals method to compare if two MenuItems are the same
     * Primarily compares ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MenuItem menuItem = (MenuItem) obj;
        return Objects.equals(id, menuItem.id);
    }
    
    /**
     * hashCode method for HashMap usage
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}