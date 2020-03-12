package datastructure.lineartable;

import org.junit.Test;

import static org.junit.Assert.*;

public class CircularQueueTest {
    CircularQueue queue = new CircularQueue(5);

    @Test
    public void testExhaustively() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        assertEquals(queue.size(), 5);
        assertEquals(queue.dequeue(), 1);
        assertEquals(queue.dequeue(), 2);
        assertEquals(queue.dequeue(), 3);
        assertEquals(queue.dequeue(), 4);
        assertFalse(queue.isEmpty());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(queue.size(), 4);
    }
}
