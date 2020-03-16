package problems;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySearchTest {
    private int[] arr;

    @Before
    public void setup() {
        arr = new int[]{1, 4, 5, 9, 12, 19, 19, 28, 31, 36};
    }

    @Test
    public void testSearch() {
        assertEquals(BinarySearch.search(arr, 28), 7);
    }

    @Test
    public void testSearchFirst() {
        assertEquals(BinarySearch.searchFirst(arr, 19), 5);
    }

    @Test
    public void testSearchLast() {
        assertEquals(BinarySearch.searchLast(arr, 19), 6);
    }

    @Test
    public void testSearchFirstBigger() {
        assertEquals(BinarySearch.searchFirstBigger(arr, 20), 7);
    }

    @Test
    public void testSearchLastSmaller() {
        assertEquals(BinarySearch.searchLastSmaller(arr, 30), 7);
    }

    @Test
    public void testSqrt() {
        double diff = Math.abs(Math.sqrt(7) - BinarySearch.sqrt(7, 6));
        assertTrue(diff < 0.000001);
    }
}
