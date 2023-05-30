package dataStructure;

import java.util.Arrays;

public class MySet<T extends Comparable<T>> {
    private Node<T> head;
    private int size;

    private class Node<T extends Comparable<T>> implements Comparable<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public int compareTo(T other) {
            return this.data.compareTo(other);
        }        
    }

    public MySet() {
        this.head = null;
        this.size = 0;
    }

    public MySet(MySet<T> otherSet) {
        this.head = null;
        this.size = 0;
        T[] array = otherSet.toArray();
        for (T element : array) {
            add(element);
        }
    }

    public boolean add(T element) {
        if (this.head == null) {
            this.head = new Node(element);
            size++;
            return true;
        }
        if (this.head.compareTo(element) == 0) {
            return false;
        }
        if (this.head.compareTo(element) > 0) {
            this.head = new Node(element, this.head);
            size++;
            return true;
        }

        Node<T> current = this.head;
        while (current.next != null) {
            if (current.next.compareTo(element) == 0) {
                return false;
            }
            if (current.next.compareTo(element) > 0) {
                Node<T> dummy = current.next;
                current.next = new Node(element, dummy);
                size++;
                return true;
            }
            current = current.next;
        }
        current.next = new Node(element);
        size++;
        return true;
    }

    public boolean remove(T element) {
        if (this.head == null) {
            return false;
        }
        if (this.head.compareTo(element) == 0) {
            this.head = this.head.next;
            size--;
            return true;
        }
        Node current = this.head;
        while (current.next != null) {
            if (current.next.data == element) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;

        
    }

    public boolean contains(T element) {
        if (this.head == null) {
            return false;
        }
        if (this.head.compareTo(element) == 0) {
            return true;
        }
        Node current = this.head;
        while (current.next != null) {
            current = current.next;
            if (current.compareTo(element) == 0) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    public T[] toArray() {
        T[] output = (T[]) new Comparable[size];
        int index = 0;
        Node<T> current = this.head;
        while (current != null) {
            output[index++] = current.data;
            current = current.next;
        }
        return output;
    }

    public T[] toArray(T[] array) {
        if (array.length < size) {
            array = Arrays.copyOf(array, size);
        }
        int index = 0;
        Node<T> current = this.head;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    public MySet<T> union(MySet<T> other) {
        T[] otherArray = other.toArray();
        MySet<T> newSet = this;
        for (T element : otherArray) {
            newSet.add(element);
        }
        return newSet;
    }

    public MySet<T> intersection(MySet<T> other) {
        T[] otherArray = other.toArray();
        MySet<T> newSet = new MySet();
        for (T element : otherArray) {
            if (contains(element)) {
                newSet.add(element);
            }
        }
        return newSet;
    }

    public MySet<T> difference(MySet<T> other) {
        T[] otherArray = other.toArray();
        MySet<T> newSet = this;
        for (T element : otherArray) {
            newSet.remove(element);
        }
        return newSet;
    }

    public void printAll() {
        T[] array = toArray();
        System.out.println(Arrays.toString(array));
    }
    
}
