// ============================================================
// File: InitialDataLoader.java
// Location: src/service/InitialDataLoader.java
// Purpose: Load initial data (default menu) for the system
// Responsible: Member C
// ============================================================

package service;

import model.MenuItem;

/**
 * Initial Data Loader
 * Responsible for creating default menu data when system starts
 * Convenient for testing and demo
 */
public class InitialDataLoader {
    
    /**
     * Load initial menu
     * Create default menu items
     */
    public static void loadInitialMenu() {
        System.out.println("Loading initial menu...");
        
        // Load main dishes
        loadMainDishes();
        
        // Load beverages
        loadDrinks();
        
        // Load desserts
        loadDesserts();
        
        System.out.println("Initial menu loaded! Total " + MenuService.getMenuCount() + " items");
    }
    
    /**
     * Load main dishes
     */
    private static void loadMainDishes() {
        MenuService.addMenuItem(
            new MenuItem("M001", "Classic Beef Burger", 150, "Main Dish", "100% pure beef with fresh vegetables")
        );
        
        MenuService.addMenuItem(
            new MenuItem("M002", "Crispy Fried Chicken", 120, "Main Dish", "Crispy outside, tender inside")
        );
        
        MenuService.addMenuItem(
            new MenuItem("M003", "Italian Bolognese Pasta", 180, "Main Dish", "Classic Italian flavor")
        );
        
        MenuService.addMenuItem(
            new MenuItem("M004", "Caesar Salad", 100, "Main Dish", "Fresh and healthy choice")
        );
        
        MenuService.addMenuItem(
            new MenuItem("M005", "Premium Steak", 350, "Main Dish", "Australian imported beef")
        );
        
        MenuService.addMenuItem(
            new MenuItem("M006", "Teriyaki Chicken Rice", 160, "Main Dish", "Japanese style teriyaki sauce")
        );
        
        MenuService.addMenuItem(
            new MenuItem("M007", "Seafood Pizza", 280, "Main Dish", "Fresh seafood toppings")
        );
    }
    
    /**
     * Load beverages
     */
    private static void loadDrinks() {
        MenuService.addMenuItem(
            new MenuItem("D001", "Coca Cola", 30, "Beverage", "Cold and refreshing")
        );
        
        MenuService.addMenuItem(
            new MenuItem("D002", "Orange Juice", 40, "Beverage", "Freshly squeezed")
        );
        
        MenuService.addMenuItem(
            new MenuItem("D003", "Bubble Milk Tea", 60, "Beverage", "Signature drink")
        );
        
        MenuService.addMenuItem(
            new MenuItem("D004", "Americano", 50, "Beverage", "Rich and aromatic")
        );
        
        MenuService.addMenuItem(
            new MenuItem("D005", "Lemon Iced Tea", 45, "Beverage", "Fresh and thirst-quenching")
        );
        
        MenuService.addMenuItem(
            new MenuItem("D006", "Matcha Latte", 70, "Beverage", "Kyoto Uji matcha")
        );
    }
    
    /**
     * Load desserts
     */
    private static void loadDesserts() {
        MenuService.addMenuItem(
            new MenuItem("S001", "Chocolate Ice Cream", 60, "Dessert", "Belgian chocolate")
        );
        
        MenuService.addMenuItem(
            new MenuItem("S002", "Strawberry Cake", 80, "Dessert", "Fresh strawberries")
        );
        
        MenuService.addMenuItem(
            new MenuItem("S003", "Tiramisu", 90, "Dessert", "Italian classic dessert")
        );
        
        MenuService.addMenuItem(
            new MenuItem("S004", "Mango Smoothie", 75, "Dessert", "Seasonal limited")
        );
        
        MenuService.addMenuItem(
            new MenuItem("S005", "Caramel Pudding", 65, "Dessert", "Handmade")
        );
    }
    
    /**
     * Load simplified test menu (only a few items)
     * For quick testing
     */
    public static void loadTestMenu() {
        System.out.println("Loading test menu...");
        
        MenuService.addMenuItem(
            new MenuItem("M001", "Burger", 100, "Main Dish")
        );
        
        MenuService.addMenuItem(
            new MenuItem("D001", "Coke", 30, "Beverage")
        );
        
        MenuService.addMenuItem(
            new MenuItem("S001", "Ice Cream", 50, "Dessert")
        );
        
        System.out.println("Test menu loaded! Total " + MenuService.getMenuCount() + " items");
    }
}