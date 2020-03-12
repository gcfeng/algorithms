package string;

import org.junit.Test;

import static org.junit.Assert.*;

public class BruteForceTest {
    @Test
    public void test() {
        String str = "somebody";
        assertTrue(BruteForce.search(str, "bo") == 4);
        assertTrue(BruteForce.search(str, "boa") == -1);
    }
}
