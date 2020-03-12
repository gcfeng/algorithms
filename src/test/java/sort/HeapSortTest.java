package sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class HeapSortTest {
    private Random random = new Random();

    @Test
    public void test() {
        int[] arr = new int[10];
        for (int i = 0, n = arr.length; i < n; i++) {
            arr[i] = randomNumber(-10000, 10000);
        }

        int[] copyArr = arr.clone();
        new HeapSort().run(arr);
        Arrays.sort(copyArr);
        assertArrayEquals(arr, copyArr);
    }

    private int randomNumber(int min, int max) {
        return random.nextInt((max - min + 1)) + min;
    }
}
