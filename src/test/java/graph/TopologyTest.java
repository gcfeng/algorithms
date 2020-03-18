package graph;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TopologyTest {
    private Topology topology;

    @Before
    public void setup() {
        int[] vertexes = new int[]{1, 2, 3, 4, 5};
        topology = new Topology(vertexes);
        topology.addEdge(1, 4);
        topology.addEdge(1, 5);
        topology.addEdge(2, 1);
        topology.addEdge(2, 3);
        topology.addEdge(2, 5);
        topology.addEdge(4, 3);
    }

    @Test
    public void testSortByKahn() {
        List<Integer> ret = topology.sortByKahn();
        System.out.println(ret);
    }

    @Test
    public void testSortDFS() {
        List<Integer> ret = topology.sortByDFS();
        System.out.println(ret);
    }
}
