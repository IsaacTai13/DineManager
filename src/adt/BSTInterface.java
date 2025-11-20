package adt;

import java.util.List;

// Binary Search Tree Interface
public interface BSTInterface<T> {

	void insert(T item);
	 
	void delete(T item);
	 
	boolean search(T item);
	 
	List<T> inorderTraversal();
	 
	T findMin();
	 
	T findMax();
	 
	boolean isEmpty();
	 
	int size();  	
}
