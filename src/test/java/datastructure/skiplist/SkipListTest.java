package datastructure.skiplist;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SkipListTest {
    private SkipList skipList;
    private Random random = new Random();

    @Before
    public void setup() {
        skipList = new SkipList();
    }

    @Test
    public void testEmpty() {
        assertTrue(skipList.isEmpty());
        assertEquals(skipList.level(), 1);
    }

    @Test
    public void testAdd() {
        assertTrue(skipList.add(3));
        assertTrue(skipList.add(6));
        assertTrue(skipList.add(2));
        assertTrue(skipList.add(9));
        assertFalse(skipList.add(3));
        assertEquals(skipList.size(), 4);
    }

    @Test
    public void testRemove() {
        assertFalse(skipList.remove(3));
        skipList.add(3);
        skipList.add(4);
        assertTrue(skipList.remove(3));
    }

    @Test
    public void testContains() {
        assertFalse(skipList.contains(3));
        skipList.add(3);
        skipList.add(4);
        assertTrue(skipList.contains(3));
        assertTrue(skipList.contains(4));
    }

    @Test
    public void testPrint() {
        for (int i = 0, n = 10; i < n; i++) {
            skipList.add(randomNumber(0, 20));
        }
        skipList.print();
    }

    private int randomNumber(int min, int max) {
        return random.nextInt((max - min + 1)) + min;
    }
}
