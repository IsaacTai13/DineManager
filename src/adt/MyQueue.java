package adt;

import java.util.ArrayList;
import java.util.List;

/**
 * My Queue Implementation from Scratch (FIFO - First In First Out)
 * Uses a custom Node-based linked list structure
 * Does NOT use Java's LinkedList or Queue - implements from the ground up
 * 
 * @param <T> The type of elements held in this queue
 */
public class MyQueue<T> implements QueueInterface<T> {
    
    /**
     * Node class for linked list structure
     */
    private class Node {
        T data;
        Node next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    // Front and rear pointers for the queue
    private Node front;  // Points to the first element (dequeue from here)
    private Node rear;   // Points to the last element (enqueue here)
    private int size;    // Number of elements in the queue
    
    
    /**
     * Constructor - Initialize empty queue
     */
    public MyQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }
    
    
    @Override
    public boolean enqueue(T element) {
        if (element == null) {
            System.err.println("Error: Cannot enqueue null element");
            return false;
        }
        
        // Create new node
        Node newNode = new Node(element);
        
        // If queue is empty, new node is both front and rear
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            // Add new node at the rear
            rear.next = newNode;
            rear = newNode;
        }
        
        size++;
        return true;
    }
    
    
    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        
        // Get data from front node
        T data = front.data;
        
        // Move front pointer to next node
        front = front.next;
        
        // If queue becomes empty, rear should also be null
        if (front == null) {
            rear = null;
        }
        
        size--;
        return data;
    }
    
    
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }
    
    
    @Override
    public int size() {
        return size;
    }
    
    
    @Override
    public boolean isEmpty() {
        return front == null;
    }
    
    
    @Override
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
    
    
    @Override
    public List<T> getAllElements() {
        List<T> elements = new ArrayList<>();
        
        // Traverse from front to rear
        Node current = front;
        while (current != null) {
            elements.add(current.data);
            current = current.next;
        }
        
        return elements;
    }
    
    
    @Override
    public boolean contains(T element) {
        Node current = front;
        
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    
    /**
     * Print all elements in the queue (for debugging)
     */
    public void printAll() {
        System.out.println("\n=== Queue (FIFO) ===");
        System.out.println("Total elements: " + size);
        
        if (isEmpty()) {
            System.out.println("Queue is empty");
        } else {
            System.out.print("Front → ");
            Node current = front;
            while (current != null) {
                System.out.print("[" + current.data + "]");
                if (current.next != null) {
                    System.out.print(" → ");
                }
                current = current.next;
            }
            System.out.println(" ← Rear");
        }
        
        System.out.println("====================\n");
    }
    
    
    /**
     * Visualize queue structure
     */
    public void visualize() {
        System.out.println("\n=== Queue Visualization ===");
        
        if (isEmpty()) {
            System.out.println("[ EMPTY ]");
        } else {
            System.out.println("Front → Rear:");
            Node current = front;
            int position = 0;
            
            while (current != null) {
                System.out.println("  Position " + position + ": " + current.data);
                current = current.next;
                position++;
            }
        }
        
        System.out.println("==========================\n");
    }
}