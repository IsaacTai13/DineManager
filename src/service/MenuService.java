// ============================================================
// File: MenuService.java
// Location: src/service/MenuService.java
// Purpose: Handle all menu-related business logic
// Responsible: Member C
// ============================================================

package service;

import model.MenuItem;
import java.util.*;

/**
 * Menu Service Class
 * Provides all menu-related operations
 * Includes: add, delete, update, query, search, categorize functions
 */
public class MenuService {
    
    // ==================== Basic CRUD Operations ====================
    
    /**
     * Add menu item
     * Updates both HashMap and BST
     * 
     * @param item Menu item to add
     * @return Whether addition was successful
     */
    public static boolean addMenuItem(MenuItem item) {
        if (item == null) {
            System.err.println("Error: Menu item cannot be null");
            return false;
        }
        
        // Check for duplicate ID
        if (DataManager.menuHashMap.containsKey(item.getId())) {
            System.err.println("Error: Menu item ID already exists: " + item.getId());
            return false;
        }
        
        // Add to both HashMap and BST
        DataManager.menuHashMap.put(item.getId(), item);
        DataManager.menuBST.put(item.getPrice(), item);
        
        System.out.println("Successfully added menu item: " + item.getName());
        return true;
    }
    
    /**
     * Remove menu item
     * Removes from both HashMap and BST
     * 
     * @param id Menu item ID to remove
     * @return Whether removal was successful
     */
    public static boolean removeMenuItem(String id) {
        MenuItem item = DataManager.menuHashMap.get(id);
        
        if (item == null) {
            System.err.println("Error: Menu item ID not found: " + id);
            return false;
        }
        
        // Remove from both data structures
        DataManager.menuHashMap.remove(id);
        DataManager.menuBST.remove(item.getPrice());
        
        System.out.println("Successfully removed menu item: " + item.getName());
        return true;
    }
    
    /**
     * Update menu item
     * Removes old item and adds new one
     * 
     * @param id Menu item ID to update
     * @param newItem New menu item data
     * @return Whether update was successful
     */
    public static boolean updateMenuItem(String id, MenuItem newItem) {
        if (!DataManager.menuHashMap.containsKey(id)) {
            System.err.println("Error: Menu item ID not found: " + id);
            return false;
        }
        
        // Remove old item
        removeMenuItem(id);
        
        // Add new item (with new ID)
        return addMenuItem(newItem);
    }
    
    // ==================== Query Methods ====================
    
    /**
     * Get menu item by ID
     * 
     * @param id Menu item ID
     * @return Found menu item, null if not found
     */
    public static MenuItem getMenuItemById(String id) {
        return DataManager.menuHashMap.get(id);
    }
    
    /**
     * Get all menu items (no specific order)
     * 
     * @return List of all menu items
     */
    public static List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(DataManager.menuHashMap.values());
    }
    
    /**
     * Get menu sorted by price (using BST)
     * 
     * @return List of menu items sorted by price
     */
    public static List<MenuItem> getMenuByPrice() {
        return new ArrayList<>(DataManager.menuBST.values());
    }
    
    /**
     * Get menu sorted by price (descending)
     * 
     * @return List of menu items sorted by price in descending order
     */
    public static List<MenuItem> getMenuByPriceDescending() {
        List<MenuItem> items = new ArrayList<>(DataManager.menuBST.descendingMap().values());
        return items;
    }
    
    // ==================== 搜尋方法 ====================
    
    /**
     * 根據分類取得菜品
     * 
     * @param category 分類名稱（主餐/飲料/甜點）
     * @return 該分類的所有菜品
     */
    public static List<MenuItem> getMenuByCategory(String category) {
        List<MenuItem> result = new ArrayList<>();
        
        for (MenuItem item : DataManager.menuHashMap.values()) {
            if (item.getCategory().equals(category)) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * 搜尋菜品（根據名稱，支援部分比對）
     * 
     * @param keyword 搜尋關鍵字
     * @return 符合的菜品列表
     */
    public static List<MenuItem> searchMenuByName(String keyword) {
        List<MenuItem> result = new ArrayList<>();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return result;
        }
        
        String lowerKeyword = keyword.toLowerCase().trim();
        
        for (MenuItem item : DataManager.menuHashMap.values()) {
            if (item.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(item);
            }
        }
        
        return result;
    }
    
    /**
     * 根據價格範圍查詢菜品
     * 
     * @param minPrice 最低價格
     * @param maxPrice 最高價格
     * @return 價格範圍內的菜品列表
     */
    public static List<MenuItem> getMenuByPriceRange(double minPrice, double maxPrice) {
        List<MenuItem> result = new ArrayList<>();
        
        // 使用 BST 的 subMap 功能（高效）
        NavigableMap<Double, MenuItem> subMap = 
            DataManager.menuBST.subMap(minPrice, true, maxPrice, true);
        
        result.addAll(subMap.values());
        
        return result;
    }
    
    // ==================== 統計方法 ====================
    
    /**
     * 取得菜單總數
     * 
     * @return 菜品總數量
     */
    public static int getMenuCount() {
        return DataManager.menuHashMap.size();
    }
    
    /**
     * 取得某分類的菜品數量
     * 
     * @param category 分類名稱
     * @return 該分類的菜品數量
     */
    public static int getMenuCountByCategory(String category) {
        return getMenuByCategory(category).size();
    }
    
    /**
     * 取得最便宜的菜品
     * 
     * @return 最便宜的菜品，如果沒有菜品則回傳 null
     */
    public static MenuItem getCheapestItem() {
        if (DataManager.menuBST.isEmpty()) {
            return null;
        }
        return DataManager.menuBST.firstEntry().getValue();
    }
    
    /**
     * 取得最貴的菜品
     * 
     * @return 最貴的菜品，如果沒有菜品則回傳 null
     */
    public static MenuItem getMostExpensiveItem() {
        if (DataManager.menuBST.isEmpty()) {
            return null;
        }
        return DataManager.menuBST.lastEntry().getValue();
    }
    
    /**
     * 計算平均價格
     * 
     * @return 所有菜品的平均價格
     */
    public static double getAveragePrice() {
        if (DataManager.menuHashMap.isEmpty()) {
            return 0;
        }
        
        double total = 0;
        for (MenuItem item : DataManager.menuHashMap.values()) {
            total += item.getPrice();
        }
        
        return total / DataManager.menuHashMap.size();
    }
    
    // ==================== 驗證方法 ====================
    
    /**
     * 檢查菜品名稱是否重複
     * 
     * @param name 要檢查的菜品名稱
     * @return 如果名稱已存在回傳 true
     */
    public static boolean isNameDuplicate(String name) {
        for (MenuItem item : DataManager.menuHashMap.values()) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 檢查菜品名稱是否重複（排除特定 ID）
     * 用於更新時的驗證
     * 
     * @param name 要檢查的菜品名稱
     * @param excludeId 要排除的菜品 ID
     * @return 如果名稱已存在（排除指定ID後）回傳 true
     */
    public static boolean isNameDuplicate(String name, String excludeId) {
        for (MenuItem item : DataManager.menuHashMap.values()) {
            if (!item.getId().equals(excludeId) && item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 驗證菜品資料是否有效
     * 
     * @param item 要驗證的菜品
     * @return 驗證結果訊息（空字串表示驗證通過）
     */
    public static String validateMenuItem(MenuItem item) {
        if (item == null) {
            return "菜品不能為空";
        }
        
        if (item.getId() == null || item.getId().trim().isEmpty()) {
            return "菜品 ID 不能為空";
        }
        
        if (item.getName() == null || item.getName().trim().isEmpty()) {
            return "菜品名稱不能為空";
        }
        
        if (item.getPrice() <= 0) {
            return "價格必須大於 0";
        }
        
        if (item.getCategory() == null || item.getCategory().trim().isEmpty()) {
            return "分類不能為空";
        }
        
        return "";  // 驗證通過
    }
    
    // ==================== 輔助方法 ====================
    
    /**
     * 取得所有分類
     * 
     * @return 所有不重複的分類列表
     */
    public static List<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
        
        for (MenuItem item : DataManager.menuHashMap.values()) {
            categories.add(item.getCategory());
        }
        
        return new ArrayList<>(categories);
    }
    
    /**
     * 檢查菜單是否為空
     * 
     * @return 如果沒有任何菜品回傳 true
     */
    public static boolean isEmpty() {
        return DataManager.menuHashMap.isEmpty();
    }
    
    // ==================== 測試/除錯方法 ====================
    
    /**
     * 顯示所有菜單（測試用）
     */
    public static void printAllMenu() {
        System.out.println("\n=== 所有菜單 ===");
        if (DataManager.menuHashMap.isEmpty()) {
            System.out.println("目前沒有菜品");
            return;
        }
        
        for (MenuItem item : DataManager.menuHashMap.values()) {
            System.out.println(item.toString());
        }
        System.out.println("總計: " + getMenuCount() + " 項");
    }
    
    /**
     * 顯示按價格排序的菜單（測試用）
     */
    public static void printMenuByPrice() {
        System.out.println("\n=== 菜單（按價格排序）===");
        if (DataManager.menuBST.isEmpty()) {
            System.out.println("目前沒有菜品");
            return;
        }
        
        for (MenuItem item : DataManager.menuBST.values()) {
            System.out.println(item.toString());
        }
    }
}