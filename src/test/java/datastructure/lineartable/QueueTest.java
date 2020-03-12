package datastructure.lineartable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueTest {
    private Queue<Integer> queue;

    @Before
    public void setup() { queue = new Queue<>(); }

    @Test
    public void testEmptyQueue() {
        assertTrue(queue.isEmpty());
        assertEquals(queue.size(), 0);
    }

    @Test(expected = Exception.class)
    public void testPeekOnEmpty() {
        queue.peek();
    }

    @Test(expected = Exception.class)
    public void testDequeueOnEmpty() {
        queue.dequeue();
    }

    @Test
    public void testEnqueue() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(queue.size(), 2);
    }

    @Test
    public void testPeek() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertTrue(queue.peek() == 1);
    }

    @Test
    public void testDequeue() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertTrue(queue.dequeue() == 1);
        assertTrue(queue.dequeue() == 2);
        assertTrue(queue.isEmpty());
    }
}
