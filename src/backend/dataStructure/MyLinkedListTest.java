package backend.dataStructure;

import org.junit.Before;
import org.junit.Test;

import backend.dataStructure.MyLinkedList;

import java.util.Iterator;

import static org.junit.Assert.*;

public class MyLinkedListTest {

    private MyLinkedList<Integer> linkedList;

    @Before
    public void setUp() {
        linkedList = new MyLinkedList<>();
    }

    @Test
    public void testSize() {
        assertEquals(0, linkedList.size());
        linkedList.add(1);
        linkedList.add(2);
        assertEquals(2, linkedList.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(linkedList.isEmpty());
        linkedList.addFirst(1);
        assertFalse(linkedList.isEmpty());
    }

    @Test
    public void testadd() {
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        assertEquals(3, linkedList.size());
        assertEquals(1, linkedList.get(0).intValue());
        assertEquals(2, linkedList.get(1).intValue());
        assertEquals(3, linkedList.get(2).intValue());
    }

    @Test
    public void testAddFirst() {
        linkedList.addFirst(1);
        linkedList.addFirst(2);
        linkedList.addFirst(3);

        assertEquals(3, linkedList.size());
        assertEquals(3, linkedList.get(0).intValue());
        assertEquals(2, linkedList.get(1).intValue());
        assertEquals(1, linkedList.get(2).intValue());
    }

    @Test
    public void testAdd() {
        linkedList.add(1, 0);
        linkedList.add(3, 1);
        linkedList.add(2, 1);

        assertEquals(3, linkedList.size());
        assertEquals(1, linkedList.get(0).intValue());
        assertEquals(2, linkedList.get(1).intValue());
        assertEquals(3, linkedList.get(2).intValue());
    }

    @Test
    public void testRemoveFirst() {
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        int removed = linkedList.removeFirst();

        assertEquals(1, removed);
        assertEquals(2, linkedList.size());
        assertEquals(2, linkedList.get(0).intValue());
        assertEquals(3, linkedList.get(1).intValue());
    }

    @Test
    public void testRemoveLast() {
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        int removed = linkedList.removeLast();

        assertEquals(3, removed);
        assertEquals(2, linkedList.size());
        assertEquals(1, linkedList.get(0).intValue());
        assertEquals(2, linkedList.get(1).intValue());
    }

    @Test
    public void testRemove() {
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        int removed = linkedList.remove(1);

        assertEquals(2, removed);
        assertEquals(2, linkedList.size());
        assertEquals(1, linkedList.get(0).intValue());
        assertEquals(3, linkedList.get(1).intValue());
    }

    @Test
    public void testRemoveByData() {
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        int index = linkedList.removeByData(2);

        assertEquals(1, index);
        assertEquals(2, linkedList.size());
        assertEquals(1, linkedList.get(0).intValue());
        assertEquals(3, linkedList.get(1).intValue());
    }
    @Test
    public void testToArray() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Object[] expectedArray = {1, 2, 3};
        Object[] actualArray = list.toArray();

        assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void testIterator() {
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        Iterator<Integer> iterator = linkedList.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next().intValue());

        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next().intValue());

        assertFalse(iterator.hasNext());
    }
}

