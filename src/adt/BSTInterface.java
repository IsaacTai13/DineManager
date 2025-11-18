package adt;

import java.util.List;

/**
* Binary Search Tree Interface
* Defines standard BST operations for any data type
* 
* @param <T> The type of elements stored in the BST
*/
public interface BSTInterface<T> {
 
 /**
  * Insert an item into the BST
  * @param item The item to insert
  */
 void insert(T item);
 
 /**
  * Delete an item from the BST
  * @param item The item to delete
  */
 void delete(T item);
 
 /**
  * Search for an item in the BST
  * @param item The item to search for
  * @return true if found, false otherwise
  */
 boolean search(T item);
 
 /**
  * Perform inorder traversal of the BST
  * Returns elements in sorted order
  * @return List of elements in sorted order
  */
 List<T> inorderTraversal();
 
 /**
  * Find the minimum element in the BST
  * @return The minimum element, or null if BST is empty
  */
 T findMin();
 
 /**
  * Find the maximum element in the BST
  * @return The maximum element, or null if BST is empty
  */
 T findMax();
 
 /**
  * Check if the BST is empty
  * @return true if empty, false otherwise
  */
 boolean isEmpty();
 
 /**
  * Get the number of elements in the BST
  * @return The size of the BST
  */
 int size();
}
