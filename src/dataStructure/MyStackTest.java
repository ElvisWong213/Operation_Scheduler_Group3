package dataStructure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class MyStackTest {
    @Test
    public void testClear() {
        MyStack MyStack = new MyStack<Integer>();
        MyStack.push(1);
        MyStack.push(1);
        MyStack.push(1);

        MyStack.clear();

        assertEquals(true, MyStack.isEmpty());
    }

    @Test
    public void testContains() {
        MyStack MyStack = new MyStack<Integer>();
        MyStack.push(1);
        MyStack.push(2);
        MyStack.push(3);

        assertEquals(true, MyStack.contains(2));
        assertEquals(false, MyStack.contains(4));
    }

    @Test
    public void testIsEmpty() {
        MyStack MyStack = new MyStack<Integer>();
        
        assertEquals(true, MyStack.isEmpty());
    }

    @Test
    public void testPeek() {
        MyStack MyStack = new MyStack<Integer>();
        MyStack.push(1);
        MyStack.push(2);
        MyStack.push(3);

        assertEquals(3, MyStack.peek());
    }

    @Test
    public void testPop() {
        MyStack MyStack = new MyStack<Integer>();
        MyStack.push(1);
        MyStack.push(2);
        MyStack.push(3);

        assertEquals(3, MyStack.pop());
    }

    @Test
    public void testPush() {
        MyStack MyStack = new MyStack<Integer>();
        MyStack.push(1);
        MyStack.push(2);
        MyStack.push(3);

        assertEquals(3, MyStack.peek());
    }

    @Test
    public void testSize() {
        MyStack MyStack = new MyStack<Integer>();
        MyStack.push(1);
        MyStack.push(2);
        MyStack.push(3);

        assertEquals(3, MyStack.peek());
    }
}
