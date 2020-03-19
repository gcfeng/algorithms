package graph;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DijkstraTest {
    private Dijkstra dijkstra;

    @Before
    public void setup() {
        int[] vertexes = new int[]{1, 2, 3, 4, 5};
        dijkstra = new Dijkstra(vertexes);
    }

    @Test
    public void test() {
        dijkstra.addEdge(1, 4, 2);
        dijkstra.addEdge(1, 3, 10);
        dijkstra.addEdge(3, 2, 6);
        dijkstra.addEdge(3, 5, 4);
        dijkstra.addEdge(3, 2, 6);
        dijkstra.addEdge(4, 3, 5);
        dijkstra.addEdge(4, 5, 3);
        dijkstra.addEdge(5, 2, 2);

        List<Integer> predecessor = dijkstra.search(1, 2);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(4);
        expected.add(5);
        expected.add(2);
        assertEquals(expected, predecessor);
    }
}
