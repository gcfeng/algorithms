package datastructure.lineartable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StackTest {
    private Stack<Integer> stack;

    @Before
    public void setup() { stack = new Stack<>(); }

    @Test
    public void testEmptyStack() {
        assertTrue(stack.isEmpty());
        assertEquals(stack.size(), 0);
    }

    @Test(expected = Exception.class)
    public void testPeekOnEmpty() {
        stack.peek();
    }

    @Test(expected = Exception.class)
    public void testPopOnEmpty() {
        stack.pop();
    }

    @Test
    public void testPush() {
        stack.push(1);
        stack.push(2);
        assertEquals(stack.size(), 2);
    }

    @Test
    public void testPeek() {
        stack.push(1);
        stack.push(2);
        assertTrue(stack.peek() == 2);
    }

    @Test
    public void testPop() {
        stack.push(1);
        stack.push(2);
        assertTrue(stack.pop() == 2);
        assertTrue(stack.pop() == 1);
        assertTrue(stack.isEmpty());
    }
}
