package dataStructure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic linked list implementation that supports various operations such as adding and removing elements. 
 * It also implements the Iterable interface to enable for-each loops to be used with MyLinkedList objects.
 * 
 * @param <T> The type of elements in the linked list.
 */
public class MyLinkedList<T> implements Iterable<T>, Collection<T> {
    private Node<T> head;
    private int size; // track the length of linked list
    
    /**
     * A private inner class that represents a node of the linked list.
     * Each node has one data item and a reference to the next node in the list.
     * 
     * @param <T> The type of data stored in the node.
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        /**
         * Constructs a new node with the specified data.
         * 
         * @param data The data to be stored in this node.
         */
        public Node (T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * A private inner class that represents an iterator for a MyLinkedList object.
     * 
     * @param <T> The type of elements in the linked list.
     */
    private class MyLinkedListIterator<T> implements Iterator<T> {
        Node<T> current;

        /**
         * Constructs a new MyLinkedListIterator object with the specified MyLinkedList object.
         * 
         * @param myLinkedList The MyLinkedList object for this iterator.
         */
        public MyLinkedListIterator(MyLinkedList myLinkedList) {
            this.current = myLinkedList.head;
        }

        /**
         * Returns true if there is at least one more element to be returned by the iterator, false otherwise.
         * 
         * @return True if there is at least one more element to be returned by the iterator, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         * 
         * @return The next element in the iteration.
         */
        @Override
        public T next() {
            T data = current.data;
            current = current.next;
            return data;
        }   
    }

    /**
     * Constructs a new MyLinkedList object with an empty list.
     */
    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Returns the number of elements in the linked list.
     * 
     * @return The number of elements in the linked list.
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns true if the linked list is empty, false otherwise.
     * 
     * @return True if the linked list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Adds an element to the beginning of the linked list.
     * 
     * @param data The data to be added to the beginning of the linked list.
     */
    public void addFirst(T data) {
        Node firstNode = this.head;
        Node newNode = new Node<T>(data);
        newNode.next = firstNode;
        this.head = newNode;
        this.size++;
    }

    /**
     * Adds an element to the end of the linked list.
     * 
     * @param data The data to be added to the end of the linked list.
     * @return 
     */
    public boolean add(T data) {
        if (this.head == null) {
            this.head = new Node<T>(data);
        } else {
            Node<T> currentNode = this.head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = new Node<T>(data);
        }
        this.size++;
        return true;
    }

    /**
     * Adds an element to the linked list at the specified index.
     * 
     * @param data The data to be added to the linked list.
     * @param index The index at which to add the element.
     */
    public void add(T data, int index) {
        Node<T> newNode = new Node<T>(data);
        if (index == 0) {
            addFirst(data);
        } else if (index >= size) {
            add(data);
        } else {
            Node<T> previousNode = getNode(index - 1);
            Node<T> currentNode = getNode(index);
            newNode.next = currentNode;
            previousNode.next = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the first element of the linked list.
     * 
     * @return The first element of the linked list.
     */
    public T removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T data = this.head.data;
        this.head = this.head.next;
        this.size--;
        return data;
    }

    /**
     * Removes and returns the last element of the linked list.
     * 
     * @return The last element of the linked list.
     */
    public T removeLast() {
        T data;
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (this.size == 1) {
            data = removeFirst();
        } else {
            data = get(size - 1);
            Node<T> secondLastNode = getNode(size - 2);
            secondLastNode.next = null;
            this.size--;
        }
        return data;
    }

    /**
     * Removes and returns the element at the specified index in the linked list.
     * 
     * @param index The index of the element to be removed.
     * @return The element at the specified index that was removed.
     */
    public T remove(int index) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T data;
        // remove first element
        if (index == 0) {
            data = removeFirst();
        } 
        // remove last element
        else if (index == size()) {
            data = removeLast();
        }
        // remove element in middle 
        else {
            data = get(index);
            Node<T> previousNode = getNode(index - 1);
            Node<T> nextNode = getNode(index + 1);
            previousNode.next = nextNode;
            this.size--;
        }
        return data;
    }

    /**
     * Removes the first occurrence of the specified data element from the linked list.
     * 
     * @param data The data element to be removed from the linked list.
     * @return The index of the removed data element.
     */
    public int removeByData(T data) {
        int index = 0;

        Node<T> previousNode = this.head;
        Node<T> currentNode = this.head;
        while (currentNode != null && currentNode.data != data) {
            previousNode = currentNode;
            currentNode = currentNode.next;
            index++;
        }

        if (index >= this.size) {
            throw new NoSuchElementException();
        }

        if (index == 0) {
            removeFirst();
        } else if (index == this.size - 1) {
            removeLast();
        } else if (index > 0 && index < this.size - 1) {
            previousNode.next = currentNode.next;
            this.size--;
        } else {
            index = -1;
        }
        return index;
    }

    /**
     * Prints the contents of the linked list to the console.
     */
    public void printList() {
        Node currentNode = this.head;

        while (currentNode != null) {
            System.out.println(currentNode.data);
            currentNode = currentNode.next;
        }
    }

    /**
     * Returns the node at the specified index in the linked list.
     * 
     * @param index The index of the node to be returned.
     * @return The node at the specified index.
     */
    public Node<T> getNode(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        } 
        Node<T> currentNode = this.head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
    
    /**
     * Returns the data stored in the node at the specified index in the linked list.
     * 
     * @param index The index of the node whose data is to be returned.
     * @return The data stored in the node at the specified index.
     */
    public T get(int index) {
        return getNode(index).data;
    }

    /**
     * Returns an iterator over the elements in the linked list.
     * 
     * @return An iterator over the elements in the linked list.
     */
    @Override
    public Iterator<T> iterator() {
        return new MyLinkedListIterator<T>(this);
    }

    public void clear() {
        this.head = null;
        this.size = 0;
    }

    public int indexOf(T data) {
        Node<T> currentNode = this.head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (currentNode.data.equals(data)) {
                index = i;
                break;
            }
            currentNode = currentNode.next;
        }
        return index;
    }

    @Override
    public boolean contains(Object o) {
        Node<T> currentNode = this.head;
        for (int i = 0; i < size; i++) {
            if (currentNode.data.equals(o)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        Node<T> current = (Node<T>) head;
        int index = 0;

        while (current != null) {
            a[index++] = current.data;
            current = current.next;
        }
        return a;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }
}