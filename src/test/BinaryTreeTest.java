package test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import backend.dataStructure.BinaryTree;
import backend.dataStructure.exception.DuplicateElementException;

public class BinaryTreeTest {

    private BinaryTree<Integer> binaryTree;

    @Before
    public void setUp() {
        binaryTree = new BinaryTree<>();
    }

    @Test
    public void testAdd() throws DuplicateElementException {
        binaryTree.add(5);
        binaryTree.add(3);
        binaryTree.add(7);

        assertEquals(3, binaryTree.size());
        assertTrue(binaryTree.preOrder().contains(5));
        assertTrue(binaryTree.preOrder().contains(3));
        assertTrue(binaryTree.preOrder().contains(7));
    }

    @Test(expected = DuplicateElementException.class)
    public void testAddDuplicate() throws DuplicateElementException {
        binaryTree.add(5);
        binaryTree.add(5);
    }

    @Test
    public void testUpdate() throws DuplicateElementException {
        binaryTree.add(5);
        binaryTree.add(3);
        binaryTree.add(7);

        binaryTree.update(7);
        assertTrue(binaryTree.preOrder().contains(7));
    }

    @Test
    public void testRemove() throws DuplicateElementException {
        binaryTree.add(5);
        binaryTree.add(3);
        binaryTree.add(7);

        binaryTree.remove(3);
        assertFalse(binaryTree.preOrder().contains(3));
        assertEquals(2, binaryTree.size());
    }

    @Test
    public void testSearch() throws DuplicateElementException {
        binaryTree.add(5);
        binaryTree.add(3);
        binaryTree.add(7);

        int result = binaryTree.search(3);
        assertEquals(3, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testSearchNotFound() throws DuplicateElementException {
        binaryTree.add(5);
        binaryTree.add(3);
        binaryTree.add(7);

        binaryTree.search(10);
    }

    @Test
    public void testPreOrder() throws DuplicateElementException {
        binaryTree.add(3);
        binaryTree.add(5);
        binaryTree.add(7);

        Integer[] expectedArray = {5, 3, 7};

        assertArrayEquals(expectedArray, binaryTree.preOrder().toArray());
    }

    @Test
    public void testPostOrder() throws DuplicateElementException {
        binaryTree.add(3);
        binaryTree.add(5);
        binaryTree.add(7);
    
        Integer[] expectedArray = {3, 7, 5};


        assertArrayEquals(expectedArray, binaryTree.postOrder().toArray());
    }

    @Test
    public void testInOrder() throws DuplicateElementException {
        binaryTree.add(3);
        binaryTree.add(5);
        binaryTree.add(7);

        Integer[] expectedArray = {3, 5, 7};

        assertArrayEquals(expectedArray, binaryTree.inOrder().toArray());
    }

    @Test
    public void testSize() throws DuplicateElementException {
        binaryTree.add(3);
        binaryTree.add(5);
        binaryTree.add(7);

        assertEquals(3, binaryTree.size());
    }
}
