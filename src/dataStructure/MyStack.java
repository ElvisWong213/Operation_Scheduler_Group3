package dataStructure;

import java.util.NoSuchElementException;

public class MyStack<T> {
    private MyLinkedList<T> list;

    public MyStack() {
        this.list = new MyLinkedList<T>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public int size() {
        return list.size();
    }

    public void push(T data) {
        list.addFirst(data);
    }

    public T pop() throws NoSuchElementException {
        return list.removeFirst();
    }

    public T peek() {
        return list.get(0);
    }

    public void clear() {
        list.clear();
    }

    public boolean contains(T data) {
        return list.contains(data);
    }
}