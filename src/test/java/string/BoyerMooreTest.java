package string;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BoyerMooreTest {
    @Test
    public void test1() {
        String str = "somebody";
        assertTrue(BoyerMoore.search(str, "bo") == 4);
        assertTrue(BoyerMoore.search(str, "boa") == -1);
    }

    @Test
    public void test2() {
        String str = "CGTGCCTACTTACTTACTTACTTACGCGAA";
        assertTrue(BoyerMoore.search(str, "CTTACTTAC") == 8);
    }
}
