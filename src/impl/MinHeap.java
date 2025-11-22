package impl;

import adt.HeapADT;

import java.util.ArrayList;
import java.util.List;

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
public class MinHeap<T extends Comparable<T>> implements HeapADT<T> {
    private T[] elements; // I named it elements instead of heap because heap is entire class, arraylist is just the storage
    private int DEFAULT_CAPACITY = 3;
    private int numberOfEntries = 0;

    /**
     * Creates the internal array used by the heap.
     *
     * <p>We must allocate the array as {@code new Comparable[capacity]} instead of
     * {@code new Object[capacity]} because the generic type {@code T} is bounded as
     * {@code T extends Comparable<T>}. That means every element stored in the heap
     * must be a Comparable.</p>
     *
     * <p>If we allocate {@code new Object[]}, the JVM will treat it as an
     * {@code Object[]} at runtime. Casting an {@code Object[]} to {@code T[]}
     * (which is really a {@code Comparable[]} because of the type bound) will fail
     * with a {@link ClassCastException}:</p>
     *
     * <pre>
     * java.lang.ClassCastException:
     *   Object[] cannot be cast to Comparable[]
     * </pre>
     *
     * <p>This happens because array types in Java enforce runtime type checking.
     * A {@code Comparable[]} can only store elements that implement
     * {@link Comparable}, so it cannot be backed by an {@code Object[]} array.</p>
     *
     * <p>Therefore, we allocate the array like this:</p>
     *
     * <pre>
     * this.elements = (T[]) new Comparable[capacity];
     * </pre>
     *
     * <p>This is allowed because {@code Comparable[]} is a valid runtime type for
     * {@code T[]} when {@code T extends Comparable<T>}.</p>
     *
     * <p>Why using an interface type (Comparable) works:</p>
     * <p>
     *  Although {@code Comparable} is an interface and cannot be instantiated,
     *  creating {@code new Comparable[capacity]} is allowed because Java only
     *  creates an array that is *typed* to hold objects implementing that interface.
     *  The array stores references — not actual Comparable instances — so this is valid.</p>
     */
    @SuppressWarnings("unchecked")
    public MinHeap() {
        this.elements = (T[]) new Comparable[DEFAULT_CAPACITY];
    }

    /**
     * Insert item into the heap
     * add to the end of the heap to maintain complete tree (CBT) property
     * then heapify up from the newly added element to restore heap property
     * @param item
     */
    @Override
    public void insert(T item) {
        if (isFull()) {
            doubleCapacity();
        }
        elements[numberOfEntries] = item;
        numberOfEntries++;
        heapifyUp(numberOfEntries - 1);
    }

    /**
     * Remove and return the minimum element from the heap
     * after removing, move the last element to the root to maintain complete tree (CBT) property
     * then heapify down from the root to restore heap property
     * @return
     */
    @Override
    public T removeMin() {
        if (numberOfEntries == 0) return null;

        // using get first is mandatory, if you use remove first, ArrayList will shift elements left
        // and mess up the order, so we first get the root element do the swap, then remove last element
        T root = elements[0];
        elements[0] = elements[numberOfEntries - 1]; // move last element to root
        elements[numberOfEntries - 1] = null; // avoid loitering
        numberOfEntries--;
        heapifyDown(0);
        return root;
    }

    /**
     * Peek at the minimum element without removing it
     * @return
     */
    @Override
    public T peek() {
        if (numberOfEntries == 0) return null;
        return elements[0];
    }

    @Override
    public int size() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    /**
     * Heapify up from given index
     * is the index of the newly added element, which is at the end of the heap
     * @param index - the index to heapify up from
     */
    private void heapifyUp(int index) {
        // find parent index with (index - 1) / 2 formula
        int parentIndex = (index - 1) / 2;

        while (index > 0 && elements[index].compareTo(elements[parentIndex]) < 0) {
            // swap
            T temp = elements[index];
            elements[index] = elements[parentIndex];
            elements[parentIndex] = temp;

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
        while (leftChildIndex < numberOfEntries) {

            // find smallest among index, left child, right child (left first)
            if (elements[leftChildIndex].compareTo(elements[smallestIndex]) < 0) {
                smallestIndex = leftChildIndex;
            }

            // then check right child if exists and compare to current smallest
            // cause when performing heapify down, root will be swapped with smallest child
            if (rightChildIndex < numberOfEntries && elements[rightChildIndex].compareTo(elements[smallestIndex]) < 0) {
                smallestIndex = rightChildIndex;
            }

            if (smallestIndex == index) return; // heap property satisfied

            // swap
            T temp = elements[index];
            elements[index] = elements[smallestIndex];
            elements[smallestIndex] = temp;

            // move current index to the smallest index and continue heapifying down
            index = smallestIndex;
            leftChildIndex = 2 * index + 1;
        }
    }

    private boolean isFull() {
        return numberOfEntries >= elements.length;
    }

    private void doubleCapacity() {
        @SuppressWarnings("unchecked")
        T[] newArr = (T[]) new Comparable[elements.length * 2];

        // copy elements to new array
        for (int i = 0; i < elements.length; i++) {
            newArr[i] = elements[i];
        }

        elements = newArr; // point to the new Array, so old one can be garbage collected
    }

    /**
     * Returns a list view of the elements currently stored in the heap.
     * <p>
     * This method returns only the valid entries (0..numberOfEntries-1),
     * avoiding internal null padding from the underlying array.
     * Returning a List instead of a raw array prevents exposing the
     * heap's internal storage structure and keeps the implementation
     * properly encapsulated.
     *
     * @return a new List containing all active heap elements
     */
    public List<T> toList() {
        List<T> list = new ArrayList<>(numberOfEntries);

        for (int i = 0; i < numberOfEntries; i++) {
            list.add(elements[i]);
        }
        return list;
    }
}
