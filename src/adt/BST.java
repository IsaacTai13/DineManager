package adt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BST<T> implements BSTInterface<T> {
    
    //Node class for BST
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
    
    public BST(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
        this.size = 0;
    }
    
    // ============================================================
    // Interface Implementation
    // ============================================================
    
    @Override
    public void insert(T item) {
        if (item == null) throw new IllegalArgumentException("Cannot insert null item");
        root = insertHelper(root, item);
        size++;
    }
    
    @Override
    public void delete(T item) {
        if (item == null) throw new IllegalArgumentException("Cannot delete null item");
        root = deleteHelper(root, item);
    }
    
    @Override
    public boolean search(T item) {
        if (item == null) return false;
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
        if (isEmpty()) return null;
        Node minNode = findMinNode(root);
        return minNode.data;
    }
    
    @Override
    public T findMax() {
        if (isEmpty()) return null;
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
    // Helper Methods
    // ============================================================
    
    private Node insertHelper(Node node, T item) {
        if (node == null) {
            return new Node(item);
        }
        
        // Compare and decide which subtree to insert into
        int cmp = comparator.compare(item, node.data);
        
        if (cmp < 0) {
            node.left = insertHelper(node.left, item);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, item);
        } else {
            // Equal - update the node (replace with new item)
            node.data = item;
            size--;
        }
        
        return node;
    }
    
    private Node deleteHelper(Node node, T item) {
        if (node == null) return null;
        
        int cmp = comparator.compare(item, node.data);
        
        if (cmp < 0) {
        	node.left = deleteHelper(node.left, item);
        } else if (cmp > 0) {
        	node.right = deleteHelper(node.right, item);
        } else {  
            size--; // Found the node to delete
            
            if (node.left == null && node.right == null) return null;
            if (node.left == null) return node.right;            
            if (node.right == null) return node.left;
            
            // Node has two children, find the minimum node in the right, replace and delete
            Node minRight = findMinNode(node.right);
            node.data = minRight.data;
            node.right = deleteHelper(node.right, minRight.data);
            size++; // compensate due to recursion
        }
        
        return node;
    }
    
    private boolean searchHelper(Node node, T item) {
        if (node == null) return false;
        
        int cmp = comparator.compare(item, node.data);
        
        if (cmp < 0) {
            return searchHelper(node.left, item);
        } else if (cmp > 0) {
            return searchHelper(node.right, item);
        } else {
            return true;
        }
    }
    
    private void inorderHelper(Node node, List<T> result) {
        if (node == null) return;
        
        inorderHelper(node.left, result);
        result.add(node.data);
        inorderHelper(node.right, result);
    }

    private Node findMinNode(Node node) {
        if (node == null) return null;     
        while (node.left != null) node = node.left;        
        return node;
    }

    private Node findMaxNode(Node node) {
        if (node == null) return null;
        while (node.right != null) node = node.right;       
        return node;
    }
}