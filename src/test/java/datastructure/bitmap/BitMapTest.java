package datastructure.bitmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BitMapTest {
    private BitMap bitMap;

    @Before
    public void setup() {
        bitMap = new BitMap(4 * 32);
    }

    @Test
    public void test() {
        bitMap.set(4);
        bitMap.set(5);
        assertTrue(bitMap.get(4));
        assertTrue(bitMap.get(5));
        assertFalse(bitMap.get(10));
    }
}
