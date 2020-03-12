package datastructure.balancedtree;

import datastructure.binarysearchtree.TreeTraversalOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class AVLTreeTest {
    AVLTree<Integer> tree;

    @Before
    public void setup() {
        tree = new AVLTree<>();
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
    public void testHeight() {
        /*
            构造树如下：
                    9
                   /  \
                  1    10
                /  \     \
               0    5     11
              /    /  \
             -1   2    6
         */

        assertEquals(tree.height(), 0);

        // 第一层
        tree.add(9);
        assertEquals(tree.height(), 0);

        // 第二层
        tree.add(1);
        tree.add(10);
        assertEquals(tree.height(), 1);

        // 第三层
        tree.add(0);
        assertEquals(tree.height(), 2);
        tree.add(5);
        assertEquals(tree.height(), 2);
        tree.add(11);
        assertEquals(tree.height(), 2);

        // 第四层
        tree.add(-1);
        tree.add(2);
        tree.add(6);
        assertEquals(tree.height(), 3);
        // 判断树是否自平衡的
        assertTrue(tree.isBalanced());
    }

    @Test
    public void testAdd() {
        assertTrue(tree.add(1));
        assertTrue(tree.add(3));
        assertTrue(tree.add(5));
        assertFalse(tree.add(5));
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
        tree.add(9);
        tree.add(20);
        tree.add(30);
        tree.add(40);
        tree.add(50);
        tree.add(10);
        Integer[] items = {20, 9, 10, 40, 30, 50};
        Iterator<Integer> iter = tree.traverse(TreeTraversalOrder.PRE_ORDER);
        int index = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), items[index]);
            index++;
        }
    }

    @Test
    public void testInOrder() {
        tree.add(9);
        tree.add(20);
        tree.add(30);
        tree.add(40);
        tree.add(50);
        tree.add(10);
        Integer[] items = {9, 10, 20, 30, 40, 50};
        Iterator<Integer> iter = tree.traverse(TreeTraversalOrder.IN_ORDER);
        int index = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), items[index]);
            index++;
        }
    }
}
