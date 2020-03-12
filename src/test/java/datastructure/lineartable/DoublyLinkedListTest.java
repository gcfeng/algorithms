package datastructure.lineartable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoublyLinkedListTest {
    private DoublyLinkedList<Integer> list;

    @Before
    public void setup() {
        list = new DoublyLinkedList<>();
    }

    @Test
    public void testEmptyList() {
        assertTrue(list.isEmpty());
        assertEquals(list.size(), 0);
    }

    @Test
    public void testAdd() {
        list.addLast(2);
        assertEquals(list.size(), 1);
        list.addFirst(1);
        assertEquals(list.size(), 2);
        list.addLast(3);
        list.addAt(1, 4);
        assertEquals(list.size(), 4);
    }

    @Test
    public void testPeek() {
        list.addLast(2);
        list.addLast(3);
        assertTrue(list.peekFirst() == 2);
        list.addLast(4);
        list.addLast(6);
        assertTrue(list.peekLast() == 6);
        assertTrue(list.peekAt(2) == 4);
    }

    @Test
    public void testRemove() {
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        assertTrue(list.removeLast() == 4);
        assertTrue(list.removeAt(1) == 3);
        list.addLast(5);
        assertTrue(list.remove(5));
        assertFalse(list.remove(9));
    }

    @Test
    public void testClear() {
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        assertTrue(list.size() == 3);
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testToString() {
        DoublyLinkedList<String> strs = new DoublyLinkedList<>();
        assertEquals(strs.toString(), "[  ]");
        strs.addLast("a");
        assertEquals(strs.toString(), "[ a ]");
        strs.addLast("b");
        assertEquals(strs.toString(), "[ a, b ]");
        strs.addLast("c");
        strs.addLast("d");
        assertEquals(strs.toString(), "[ a, b, c, d ]");
    }
}
