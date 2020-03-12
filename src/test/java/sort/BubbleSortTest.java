package sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class BubbleSortTest {
    private Random random = new Random();

    @Test
    public void test() {
        int[] arr = new int[100];
        for (int i = 0, n = arr.length; i < n; i++) {
            arr[i] = randomNumber(-10000, 10000);
        }
        int[] copyArr = arr.clone();
        new BubbleSort().run(arr);
        Arrays.sort(copyArr);
        assertArrayEquals(arr, copyArr);
    }

    private int randomNumber(int min, int max) {
        return random.nextInt((max - min + 1)) + min;
    }
}
