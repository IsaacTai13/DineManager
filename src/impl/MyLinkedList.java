package impl;

import adt.LinkedListInterface;


/**
* My LinkedList Implementation from Scratch
* Does NOT use Java's LinkedList - implements linked list from the ground up
* 
* This is a singly linked list with basic operations
* 
* @param <T> The type of elements held in this list
*/
public class MyLinkedList<T> implements LinkedListInterface<T>{

	/**
	 * Node class to store data and link to next node
	 */
	private class Node {
	    T data;
	    Node next;
	    
	    Node(T data) {
	        this.data = data;
	        this.next = null;
	    }
	}
	
	private Node head;   // First node in the list
	private Node tail;   // Last node in the list
	private int size;    // Number of elements
	
	
	/**
	 * Constructor - Initialize empty linked list
	 */
	public MyLinkedList() {
	    this.head = null;
	    this.tail = null;
	    this.size = 0;
	}
	
	
	/**
	 * Add element to the end of the list
	 * @param element - the element to add
	 * @return true if successfully added
	 */
	@Override
	public boolean add(T element) {
	    Node newNode = new Node(element);
	    
	    if (isEmpty()) {
	        // First element
	        head = newNode;
	        tail = newNode;
	    } else {
	        // Add to the end
	        tail.next = newNode;
	        tail = newNode;
	    }
	    
	    size++;
	    return true;
	}
	
	
	/**
	 * Add element at specific index
	 * @param index - position to insert
	 * @param element - the element to add
	 */
	@Override
	public void add(int index, T element) {
	    if (index < 0 || index > size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }
	    
	    if (index == size) {
	        add(element);  // Add at end
	        return;
	    }
	    
	    Node newNode = new Node(element);
	    
	    if (index == 0) {
	        // Add at beginning
	        newNode.next = head;
	        head = newNode;
	    } else {
	        // Add in middle
	        Node prev = getNodeAt(index - 1);
	        newNode.next = prev.next;
	        prev.next = newNode;
	    }
	    
	    size++;
	}
	
	
	/**
	 * Get element at specific index
	 * @param index - position to retrieve
	 * @return the element at the specified position
	 */
	@Override
	public T get(int index) {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }
	    
	    return getNodeAt(index).data;
	}
	
	
	/**
	 * Remove element at specific index
	 * @param index - position to remove
	 * @return the removed element
	 */
	@Override
	public T remove(int index) {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }
	    
	    T data;
	    
	    if (index == 0) {
	        // Remove first element
	        data = head.data;
	        head = head.next;
	        
	        if (head == null) {
	            tail = null;  // List is now empty
	        }
	    } else {
	        // Remove from middle or end
	        Node prev = getNodeAt(index - 1);
	        data = prev.next.data;
	        prev.next = prev.next.next;
	        
	        if (prev.next == null) {
	            tail = prev;  // Updated tail if removed last element
	        }
	    }
	    
	    size--;
	    return data;
	}
	
	
	/**
	 * Remove specific element (first occurrence)
	 * @param element - the element to remove
	 * @return true if element was found and removed
	 */
	public boolean remove(T element) {
	    if (isEmpty()) {
	        return false;
	    }
	    
	    // Special case: remove head
	    if (head.data.equals(element)) {
	        head = head.next;
	        if (head == null) {
	            tail = null;
	        }
	        size--;
	        return true;
	    }
	    
	    // Search for element
	    Node current = head;
	    while (current.next != null) {
	        if (current.next.data.equals(element)) {
	            current.next = current.next.next;
	            
	            if (current.next == null) {
	                tail = current;  // Updated tail
	            }
	            
	            size--;
	            return true;
	        }
	        current = current.next;
	    }
	    
	    return false;  // Element not found
	}
	
	
	/**
	 * Check if list contains an element
	 * @param element - the element to search for
	 * @return true if element exists in list
	 */
	@Override
	public boolean contains(T element) {
	    Node current = head;
	    
	    while (current != null) {
	        if (current.data.equals(element)) {
	            return true;
	        }
	        current = current.next;
	    }
	    
	    return false;
	}
	
	
	/**
	 * Get the size of the list
	 * @return number of elements
	 */
	public int size() {
	    return size;
	}
	
	
	/**
	 * Check if list is empty
	 * @return true if list has no elements
	 */
	public boolean isEmpty() {
	    return head == null;
	}
	
	
	/**
	 * Clear all elements from the list
	 */
	public void clear() {
	    head = null;
	    tail = null;
	    size = 0;
	}
	
	
	/**
	 * Get node at specific index (helper method)
	 * @param index - position of node
	 * @return the node at specified index
	 */
	private Node getNodeAt(int index) {
	    Node current = head;
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }
	    return current;
	}
	
	
	/**
	 * Get first element
	 * @return first element, or null if list is empty
	 */
	public T getFirst() {
	    if (isEmpty()) {
	        return null;
	    }
	    return head.data;
	}
	
	
	/**
	 * Get last element
	 * @return last element, or null if list is empty
	 */
	public T getLast() {
	    if (isEmpty()) {
	        return null;
	    }
	    return tail.data;
	}
	
	
	/**
	 * Add element at the beginning
	 * @param element - the element to add
	 */
	public void addFirst(T element) {
	    add(0, element);
	}
	
	
	/**
	 * Add element at the end (same as add)
	 * @param element - the element to add
	 */
	public void addLast(T element) {
	    add(element);
	}
	
	
	/**
	 * Remove first element
	 * @return the removed element
	 */
	public T removeFirst() {
	    if (isEmpty()) {
	        return null;
	    }
	    return remove(0);
	}
	
	
	/**
	 * Remove last element
	 * @return the removed element
	 */
	public T removeLast() {
	    if (isEmpty()) {
	        return null;
	    }
	    return remove(size - 1);
	}
	
	
	/**
	 * Convert list to array
	 * @return array containing all elements
	 */
	@SuppressWarnings("unchecked")
	public T[] toArray() {
	    T[] array = (T[]) new Object[size];
	    Node current = head;
	    int index = 0;
	    
	    while (current != null) {
	        array[index++] = current.data;
	        current = current.next;
	    }
	    
	    return array;
	}
	
	
	/**
	 * Print all elements (for debugging)
	 */
	public void printAll() {
	    System.out.print("LinkedList: [");
	    Node current = head;
	    
	    while (current != null) {
	        System.out.print(current.data);
	        if (current.next != null) {
	            System.out.print(" -> ");
	        }
	        current = current.next;
	    }
	    
	    System.out.println("]");
	}
	
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder("[");
	    Node current = head;
	    
	    while (current != null) {
	        sb.append(current.data);
	        if (current.next != null) {
	            sb.append(", ");
	        }
	        current = current.next;
	    }
	    
	    sb.append("]");
	    return sb.toString();
	}
}