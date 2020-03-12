package datastructure.hashtable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest {
    private HashTable hashTable;

    @Before
    public void setup() {
        hashTable = new HashTable();
    }

    @Test
    public void testEmpty() {
        assertTrue(hashTable.isEmpty());
        assertEquals(hashTable.size(), 0);
    }

    @Test
    public void testAdd() {
        assertTrue(hashTable.add("one", 1));
        assertTrue(hashTable.add("two", 2));
        assertTrue(hashTable.add("two", 1));
        assertEquals(hashTable.size(), 2);
    }

    @Test
    public void testRemove() {
        hashTable.add("one", 1);
        hashTable.add("two", 2);
        assertTrue(hashTable.remove("one"));
        assertFalse(hashTable.remove("one1"));
        assertEquals(hashTable.size(), 1);
        assertTrue(hashTable.remove("two"));
        assertEquals(hashTable.size(), 0);
    }

    @Test
    public void testGet() {
        hashTable.add("one", 1);
        hashTable.add("two", 2);
        hashTable.add("three", 3);
        hashTable.add("four", 4);
        assertEquals(hashTable.size(), 4);
        assertTrue(hashTable.get("one").data == 1);
        assertTrue(hashTable.get("two").data == 2);
        assertTrue(hashTable.get("three").data == 3);
        assertTrue(hashTable.get("five") == null);
        hashTable.remove("one");
        assertTrue(hashTable.get("one") == null);
        assertEquals(hashTable.size(), 3);
    }

    @Test
    public void testPrint() {
        hashTable.add("one", 1);
        hashTable.add("two", 2);
        hashTable.add("three", 3);
        hashTable.add("four", 4);
        hashTable.print();
    }
}
