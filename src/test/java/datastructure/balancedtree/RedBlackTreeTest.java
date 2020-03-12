package datastructure.balancedtree;

import datastructure.binarysearchtree.TreeTraversalOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class RedBlackTreeTest {
    RedBlackTree<Integer> tree;

    @Before
    public void setup() {
        tree = new RedBlackTree<>();
    }

    @Test
    public void testEmpty() {
        assertTrue(tree.isEmpty());
        tree.add(1);
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(tree.size(), 0);
        tree.add(1);
        tree.add(2);
        assertEquals(tree.size(), 2);
    }

    @Test
    public void testAdd() {
        assertTrue(tree.add(1));
        assertTrue(tree.add(3));
        assertTrue(tree.add(5));
        assertFalse(tree.add(5));
        assertEquals(tree.size(), 3);
        assertTrue(tree.isBalanced());
    }

    @Test
    public void testRemove() {
        tree.add(1);
        tree.add(2);
        assertTrue(tree.remove(2));
        assertFalse(tree.remove(4));
        assertEquals(tree.size(), 1);
        assertTrue(tree.isBalanced());
    }

    @Test
    public void testContains() {
        tree.add(5);
        tree.add(3);
        tree.add(2);
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(5));
        assertFalse(tree.contains(10));
    }

    @Test
    public void testPreOrder() {
        tree.add(7);
        tree.add(3);
        tree.add(18);
        tree.add(10);
        tree.add(22);
        tree.add(8);
        tree.add(11);
        tree.add(26);
        tree.add(2);
        tree.add(6);
        tree.add(13);
        Integer[] items = {10, 7, 3, 2, 6, 8, 18, 13, 11, 26, 22};
        Iterator<Integer> iter = tree.traverse(TreeTraversalOrder.PRE_ORDER);
        int index = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), items[index]);
            index++;
        }
    }

    @Test
    public void testInOrder() {
        tree.add(7);
        tree.add(3);
        tree.add(18);
        tree.add(10);
        tree.add(22);
        tree.add(8);
        tree.add(11);
        tree.add(26);
        tree.add(2);
        tree.add(6);
        tree.add(13);
        Integer[] items = {2, 3, 6, 7, 8, 10, 11, 13, 18, 22, 26};
        Iterator<Integer> iter = tree.traverse(TreeTraversalOrder.IN_ORDER);
        int index = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), items[index]);
            index++;
        }
    }
}
