package adt;

import model.Order;

import java.util.ArrayList;

/**
 * Implementation of the MenuHeap interface using an array-based data structure.
 *
 * <p>The generic type parameter {@code T} is already declared in the {@code MenuHeap}
 * interface, so when implementing the interface, we can only provide a type argument
 * (e.g., {@code <T>}). We cannot declare a new type bound such as
 * {@code <T extends Comparable<T>>} in the {@code implements} clause, because Java
 * does not allow type bounds to appear there.</p>
 *
 * <p>However, since {@code ArrayMenuHeap} is the concrete data structure that stores
 * elements, it must ensure that any element inserted into the heap can be compared.
 * That requirement belongs to the class itself, so we declare the type bound in the
 * class definition:</p>
 *
 * <pre>
 * public class ArrayMenuHeap&lt;T extends Comparable&lt;T&gt;&gt;
 * </pre>
 *
 * <p>When implementing the interface, we must also specify which type is used for
 * the interface's generic parameter. Therefore, the correct declaration is:</p>
 *
 * <pre>
 * public class ArrayMenuHeap&lt;T extends Comparable&lt;T&gt;&gt;
 *         implements MenuHeap&lt;T&gt;
 * </pre>
 *
 * <p>This way, {@code ArrayMenuHeap} enforces the comparable constraint on its own
 * generic type while correctly supplying that type to the {@code MenuHeap} interface.</p>
 *
 * @param <T> the type of elements stored in this heap, which must implement
 *            {@link Comparable} to allow heap ordering
 *
 * @author tisaac
 */
public class ArrayMenuHeap<T extends Comparable<T>> implements MenuHeap<T> {
    private ArrayList<T> heap;
    private int size;

    /**
     * Constructor - Initialize empty heap
     */
    public ArrayMenuHeap() {
        this.heap = new ArrayList<>();
        this.size = 0;
    }

    /**
     * Insert item into the heap
     * add to the end of the heap to maintain complete tree (CBT) property
     * then heapify up from the newly added element to restore heap property
     * @param item
     */
    @Override
    public void insert(T item) {
        heap.add(item);
        size++;
        heapifyUp(size - 1);
    }

    /**
     * Remove and return the minimum element from the heap
     * after removing, move the last element to the root to maintain complete tree (CBT) property
     * then heapify down from the root to restore heap property
     * @return
     */
    @Override
    public T removeMin() {
        if (size == 0) return null;

        // using get first is mandatory, if you use remove first, ArrayList will shift elements left
        // and mess up the order, so we first get the root element do the swap, then remove last element
        T root = heap.get(0);
        heap.set(0, heap.get(size - 1)); // move last element to root
        heap.remove(size - 1); // remove last element
        size--;
        heapifyDown(0);
        return root;
    }

    /**
     * Peek at the minimum element without removing it
     * @return
     */
    @Override
    public T peek() {
        if (size == 0) return null;
        return heap.get(0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Heapify up from given index
     * is the index of the newly added element, which is at the end of the heap
     * @param index - the index to heapify up from
     */
    private void heapifyUp(int index) {
        // find parent index with (index - 1) / 2 formula
        int parentIndex = (index - 1) / 2;

        while (index > 0 && heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
            // swap
            T temp = heap.get(index);
            heap.set(index, heap.get(parentIndex));
            heap.set(parentIndex, temp);

            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    /**
     * Heapify down from given index
     * is the index of the root element, which is at the top of the heap
     * @param index - the index to heapify down from
     */
    private void heapifyDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallestIndex = index;

        // in CBT, if there is another level, left child must exist
        while (leftChildIndex < size) {

            // find smallest among index, left child, right child (left first)
            if (heap.get(leftChildIndex).compareTo(heap.get(smallestIndex)) < 0) {
                smallestIndex = leftChildIndex;
            }

            // then check right child if exists and compare to current smallest
            // cause when performing heapify down, root will be swapped with smallest child
            if (rightChildIndex < size && heap.get(rightChildIndex).compareTo(heap.get(smallestIndex)) < 0) {
                smallestIndex = rightChildIndex;
            }

            if (smallestIndex == index) return; // heap property satisfied

            // swap
            T temp = heap.get(index);
            heap.set(index, heap.get(smallestIndex));
            heap.set(smallestIndex, temp);

            // move current index to smallest index and continue heapifying down
            index = smallestIndex;
            leftChildIndex = 2 * index + 1;
        }
    }
}
