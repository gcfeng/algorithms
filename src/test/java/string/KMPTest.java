package string;

import org.junit.Test;

import static org.junit.Assert.*;

public class KMPTest {
    @Test
    public void test1() {
        String str = "somebody";
        assertEquals(KMP.search(str, "bo"), 4);
        assertEquals(KMP.search(str, "boa"), -1);
    }

    @Test
    public void test2() {
        String str = "CGTGCCTACTTACTTACTTACTTACGCGAA";
        assertEquals(KMP.search(str, "CTTACTTAC"), 8);
    }

    @Test
    public void test3() {
        String str = "BBC ABCDAB ABCDABCDABDE";
        assertEquals(KMP.search(str, "ABCDABD"), 15);
    }
}
