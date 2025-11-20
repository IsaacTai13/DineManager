package controller;

import model.MenuItem;
import service.MenuService;
import service.MenuSortingService;
import service.MenuSortingService.SortResult;
import view.MenuManagementPage;
import java.util.List;

public class MenuManagementController {
    
    private MenuManagementPage page;
    
    public MenuManagementController(MenuManagementPage page) {
        this.page = page;
    }
    
    // ============================================================
    // CRUD OPERATIONS
    // ============================================================
    
    public void handleAdd(String id, String name, String priceText, String category, String description) {
        // Validate input
        String validation = validateInput(id, name, priceText);
        if (!validation.isEmpty()) {
            page.showAlert("Validation Error", validation, "ERROR");
            return;
        }
        
        double price = Double.parseDouble(priceText);
        
        // Add new menu item
        MenuItem newItem = new MenuItem(id, name, price, category, description);
        boolean success = MenuService.addMenuItem(newItem);
        
        if (success) {
            page.showAlert("Success", "Menu item added successfully!", "INFORMATION");
            page.refreshTable(MenuService.getAllMenuItems());
            page.clearFields();
        } else {
            page.showAlert("Error", "Failed to add menu item. ID may already exist.", "ERROR");
        }
    }
    
    public void handleUpdate(MenuItem selectedItem, String id, String name, String priceText, String category, String description) {
        // Check if item is selected
        if (selectedItem == null) {
            page.showAlert("Error", "Please select an item to update", "WARNING");
            return;
        }
        
        // Validate input
        String validation = validateInput(id, name, priceText);
        if (!validation.isEmpty()) {
            page.showAlert("Validation Error", validation, "ERROR");
            return;
        }
        
        double price = Double.parseDouble(priceText);
        
        // Create new item to update
        MenuItem updatedItem = new MenuItem(id, name, price, category, description);
        boolean success = MenuService.updateMenuItem(selectedItem.getId(), updatedItem);
        
        if (success) {
            page.showAlert("Success", "Menu item updated successfully!", "INFORMATION");
            page.refreshTable(MenuService.getAllMenuItems());
            page.clearFields();
        } else {
            page.showAlert("Error", "Failed to update menu item.", "ERROR");
        }
    }
    
    public void handleDelete(MenuItem selectedItem) {
        // Check if item is selected
        if (selectedItem == null) {
            page.showAlert("Error", "Please select an item to delete", "WARNING");
            return;
        }
        
        // Show confirmation dialog
        boolean confirmed = page.showConfirmation(
            "Confirm Delete",
            "Are you sure you want to delete: " + selectedItem.getName() + "?"
        );
        
        if (confirmed) {
            boolean success = MenuService.removeMenuItem(selectedItem.getId());
            
            if (success) {
                page.showAlert("Success", "Menu item deleted successfully!", "INFORMATION");
                page.refreshTable(MenuService.getAllMenuItems());
                page.clearFields();
            } else {
                page.showAlert("Error", "Failed to delete menu item.", "ERROR");
            }
        }
    }
    
    // ============================================================
    // SORTING
    // ============================================================
    
    public void handleSort(String sortOption) {
        SortResult result;
        
        switch (sortOption) {
            case "Price (Low → High)":
                result = MenuSortingService.sortByPrice(MenuService.getAllMenuItems(), true);
                break;
                
            case "Price (High → Low)":
                result = MenuSortingService.sortByPrice(MenuService.getAllMenuItems(), false);
                break;
                
            case "Name (A → Z)":
                result = MenuSortingService.sortByName(MenuService.getAllMenuItems(), true);
                break;
                
            case "Name (Z → A)":
                result = MenuSortingService.sortByName(MenuService.getAllMenuItems(), false);
                break;
                
            case "Category":
                result = MenuSortingService.sortByCategory(MenuService.getAllMenuItems());
                break;
                
            default:
                result = MenuSortingService.sortByPrice(MenuService.getAllMenuItems(), true);
        }
        
        // Update page with sorted results
        page.updateTableWithSortedData(result.getSortedList(), result.getFormattedTime());
    }
    
    // ============================================================
    // VALIDATION
    // ============================================================
    
    private String validateInput(String id, String name, String priceText) {
        
    	if (id == null || id.trim().isEmpty()) return "ID cannot be empty";
        if (name == null || name.trim().isEmpty()) return "Name cannot be empty";       
        if (priceText == null || priceText.trim().isEmpty()) return "Price cannot be empty";
        
        try {
            double price = Double.parseDouble(priceText.trim());
            if (price <= 0) return "Price must be greater than 0";
        } catch (NumberFormatException e) {
            return "Price must be a valid number";
        }
        
        return "";
    }
    
    // ============================================================
    // DATA RETRIEVAL
    // ============================================================
    
    public List<MenuItem> getAllMenuItems() {
    	return MenuService.getMenuByPrice();
    }
}