package adt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Generic Binary Search Tree Implementation
 * Uses a Comparator for flexible comparison
 * 
 * @param <T> The type of elements stored in the BST
 */
public class BST<T> implements BSTInterface<T> {
    
    /**
     * Node class for BST
     */
    private class Node {
        T data;
        Node left;
        Node right;
        
        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    private Node root;
    private Comparator<T> comparator;
    private int size;
    
    /**
     * Constructor with Comparator
     * @param comparator The comparator to use for ordering elements
     */
    public BST(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
        this.size = 0;
    }
    
    // ============================================================
    // Public Methods (Interface Implementation)
    // ============================================================
    
    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert null item");
        }
        root = insertHelper(root, item);
        size++;
    }
    
    @Override
    public void delete(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot delete null item");
        }
        root = deleteHelper(root, item);
    }
    
    @Override
    public boolean search(T item) {
        if (item == null) {
            return false;
        }
        return searchHelper(root, item);
    }
    
    @Override
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    @Override
    public T findMin() {
        if (isEmpty()) {
            return null;
        }
        Node minNode = findMinNode(root);
        return minNode.data;
    }
    
    @Override
    public T findMax() {
        if (isEmpty()) {
            return null;
        }
        Node maxNode = findMaxNode(root);
        return maxNode.data;
    }
    
    @Override
    public boolean isEmpty() {
        return root == null;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    // ============================================================
    // Private Helper Methods
    // ============================================================
    
    /**
     * Recursive helper for insert
     */
    private Node insertHelper(Node node, T item) {
        // Base case: found the insertion point
        if (node == null) {
            return new Node(item);
        }
        
        // Compare and decide which subtree to insert into
        int cmp = comparator.compare(item, node.data);
        
        if (cmp < 0) {
            // Item is smaller, go left
            node.left = insertHelper(node.left, item);
        } else if (cmp > 0) {
            // Item is larger, go right
            node.right = insertHelper(node.right, item);
        } else {
            // Equal - update the node (replace with new item)
            node.data = item;
            size--; // Don't count as new insertion
        }
        
        return node;
    }
    
    /**
     * Recursive helper for delete
     */
    private Node deleteHelper(Node node, T item) {
        // Base case: item not found
        if (node == null) {
            return null;
        }
        
        int cmp = comparator.compare(item, node.data);
        
        if (cmp < 0) {
            // Item is in left subtree
            node.left = deleteHelper(node.left, item);
        } else if (cmp > 0) {
            // Item is in right subtree
            node.right = deleteHelper(node.right, item);
        } else {
            // Found the node to delete
            size--;
            
            // Case 1: Node has no children (leaf node)
            if (node.left == null && node.right == null) {
                return null;
            }
            
            // Case 2: Node has only right child
            if (node.left == null) {
                return node.right;
            }
            
            // Case 3: Node has only left child
            if (node.right == null) {
                return node.left;
            }
            
            // Case 4: Node has two children
            // Find the minimum node in the right subtree (inorder successor)
            Node minRight = findMinNode(node.right);
            
            // Replace current node's data with successor's data
            node.data = minRight.data;
            
            // Delete the successor node
            node.right = deleteHelper(node.right, minRight.data);
            size++; // Compensate for the decrement above
        }
        
        return node;
    }
    
    /**
     * Recursive helper for search
     */
    private boolean searchHelper(Node node, T item) {
        // Base case: not found
        if (node == null) {
            return false;
        }
        
        int cmp = comparator.compare(item, node.data);
        
        if (cmp < 0) {
            // Search in left subtree
            return searchHelper(node.left, item);
        } else if (cmp > 0) {
            // Search in right subtree
            return searchHelper(node.right, item);
        } else {
            // Found it
            return true;
        }
    }
    
    /**
     * Recursive helper for inorder traversal
     * Visits nodes in order: left -> root -> right
     */
    private void inorderHelper(Node node, List<T> result) {
        if (node == null) {
            return;
        }
        
        // Traverse left subtree
        inorderHelper(node.left, result);
        
        // Visit root
        result.add(node.data);
        
        // Traverse right subtree
        inorderHelper(node.right, result);
    }
    
    /**
     * Find the node with minimum value
     * (leftmost node)
     */
    private Node findMinNode(Node node) {
        if (node == null) {
            return null;
        }
        
        while (node.left != null) {
            node = node.left;
        }
        
        return node;
    }
    
    /**
     * Find the node with maximum value
     * (rightmost node)
     */
    private Node findMaxNode(Node node) {
        if (node == null) {
            return null;
        }
        
        while (node.right != null) {
            node = node.right;
        }
        
        return node;
    }
}