package string;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RabinKarpTest {
    @Test
    public void test() {
        String str = "somebody";
        assertTrue(RabinKarp.search(str, "bo") == 4);
        assertTrue(RabinKarp.search(str, "boa") == -1);
    }
}
