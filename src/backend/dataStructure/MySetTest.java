package backend.dataStructure;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MySetTest {
    private MySet<Integer> set;

    @Before
    public void setUp() {
        set = new MySet();
        set.add(1);
        set.add(2);
        set.add(3);
    }

    @Test
    public void testAdd() {
        assertFalse(set.add(1));
        assertTrue(set.add(6));
        assertTrue(set.add(5));
        assertTrue(set.add(4));
        assertEquals(6, set.size());
        assertTrue(set.contains(4));
        Integer[] array = set.toArray(new Integer[set.size()]);
        Integer[] expectedArray = {1,2,3,4,5,6};
        assertArrayEquals(expectedArray, array);
        set.printAll();
    }

    @Test
    public void testRemove() {
        assertTrue(set.remove(1));
        assertFalse(set.remove(4));
        assertEquals(2, set.size());
        assertFalse(set.contains(1));
    }

    @Test
    public void testContains() {
        assertTrue(set.contains(1));
        assertFalse(set.contains(4));
    }

    @Test
    public void testSize() {
        assertEquals(3, set.size());
        set.add(4);
        assertEquals(4, set.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(set.isEmpty());
        set.clear();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testToArray() {
        assertEquals(3, set.size());
        Integer[] array = set.toArray(new Integer[set.size()]);
        Integer[] expectedArray = {1,2,3};
        assertEquals(3, array.length);
        assertArrayEquals(expectedArray, array);
    }

    @Test
    public void testDifference() {
        MySet other = new MySet();
        other.add(3);
        other.add(4);
        MySet difference = set.difference(other);
        assertEquals(2, difference.size());
        assertTrue(difference.contains(1));
        assertTrue(difference.contains(2));
        
    }

    @Test
    public void testIntersection() {
        MySet other = new MySet();
        other.add(3);
        other.add(4);
        MySet intersection = set.intersection(other);
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains(3));
        
    }

    @Test
    public void testUnion() {
        MySet<Integer> other = new MySet();
        other.add(3);
        other.add(4);
        MySet union = set.union(other);
        assertEquals(4, union.size());
        assertTrue(union.contains(1));
        assertTrue(union.contains(2));
        assertTrue(union.contains(3));
        assertTrue(union.contains(4));
    }

    
}
