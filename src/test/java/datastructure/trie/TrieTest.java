package datastructure.trie;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrieTest {
    private Trie trie;

    @Before
    public void setup() {
        trie = new Trie();
    }

    @Test
    public void testContains() {
        trie.add("aaaaa");
        trie.add("aaaaa");
        trie.add("aaaaa");
        trie.add("aaaaa");
        trie.add("aaaaa");
        assertTrue(trie.contains("aaaaa"));
        assertFalse(trie.contains("aaaa"));
        assertFalse(trie.contains("b"));
    }

    @Test
    public void testAdd() {
        trie.add("hello");
        trie.add("world");
        trie.add("her");
        trie.add("word");
        trie.add("his");
        assertFalse(trie.contains("he"));
        assertTrue(trie.contains("hello"));
        assertTrue(trie.contains("his"));
        assertTrue(trie.contains("world"));
        assertFalse(trie.contains("hello1"));
    }

    @Test
    public void testRemove() {
        trie.add("hello");
        trie.add("world");
        trie.add("her");
        trie.add("word");
        trie.add("his");
        assertTrue(trie.contains("hello"));
        assertTrue(trie.contains("his"));
        assertTrue(trie.contains("world"));
        trie.remove("hello");
        assertFalse(trie.contains("hello"));
    }
}
