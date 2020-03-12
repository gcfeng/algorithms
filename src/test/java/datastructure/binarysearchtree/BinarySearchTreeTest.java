package datastructure.binarysearchtree;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    BinarySearchTree<String> tree;

    @Before
    public void setup() {
        tree = new BinarySearchTree<>();
    }

    @Test
    public void testEmpty() {
        assertTrue(tree.isEmpty());
        tree.add("a");
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(tree.size(), 0);
        tree.add("a");
        tree.add("b");
        assertEquals(tree.size(), 2);
    }

    @Test
    public void testHeight() {
        /*
            构造树如下：
                m
              /   \
             e     s
            / \   / \
           b   f n   t
          /
         a
         */

        assertEquals(tree.height(), 0);

        // 第一层
        tree.add("m");
        assertEquals(tree.height(), 1);

        // 第二层
        tree.add("e");
        tree.add("s");
        assertEquals(tree.height(), 2);

        // 第三层
        tree.add("b");
        assertEquals(tree.height(), 3);
        tree.add("f");
        assertEquals(tree.height(), 3);
        tree.add("n");
        assertEquals(tree.height(), 3);
        tree.add("t");
        assertEquals(tree.height(), 3);

        // 第四层
        tree.add("a");
        assertEquals(tree.height(), 4);
    }

    @Test
    public void testAdd() {
        assertTrue(tree.add("a"));
        assertFalse(tree.add("a"));
        assertTrue(tree.add("b"));
    }

    @Test
    public void testRemove() {
        tree.add("a");
        tree.add("b");
        assertTrue(tree.remove("a"));
        assertFalse(tree.remove("a"));
        assertEquals(tree.size(), 1);
    }

    @Test
    public void testContains() {
        tree.add("a");
        tree.add("b");
        tree.add("c");
        assertTrue(tree.contains("b"));
        assertFalse(tree.contains("d"));
    }

    @Test
    public void testPreOrder() {
        tree.add("b");
        tree.add("a");
        tree.add("c");
        String[] items = {"b", "a", "c"};
        Iterator<String> iter = tree.traverse(TreeTraversalOrder.PRE_ORDER);
        int index = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), items[index]);
            index++;
        }
    }

    @Test
    public void testInOrder() {
        tree.add("b");
        tree.add("a");
        tree.add("c");
        String[] items = {"a", "b", "c"};
        Iterator<String> iter = tree.traverse(TreeTraversalOrder.IN_ORDER);
        int index = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), items[index]);
            index++;
        }
    }
}
