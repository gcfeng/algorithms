package graph;

import datastructure.heap.MinHeap;

import java.util.*;

/**
 * Dijkstra单源最短路径算法
 *
 * 时间复杂度O(E*logV)
 *
 * 应用场景：
 * - 地图两个地点的最短路线、用时最少、最少红绿灯等
 */
public class Dijkstra {
    // 顶点列表
    private int[] vertexes;
    // 邻接表
    private Map<Integer, List<Edge>> adj;

    // 边
    private static class Edge {
        int sid;    // 边的起始顶点编号
        int tid;    // 边的终止顶点编号
        int w;      // 权重

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    // 顶点，用于记录起始顶点到该顶点的距离
    private static class Vertex implements Comparable<Vertex> {
        int id;     // 顶点编号
        int dist;   // 记录从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Vertex v) {
            if (this.dist > v.dist) return 1;
            else if (this.dist < v.dist) return -1;
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return id == vertex.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public Dijkstra(int[] vertexes) {
        this.vertexes = vertexes;
        this.adj = new HashMap<>();
        for (int i = 0, n = vertexes.length; i < n; i++) {
            this.adj.put(vertexes[i], new LinkedList<>());
        }
    }

    /**
     * 添加边 s -> t
     */
    public void addEdge(int s, int t, int w) {
        this.adj.get(s).add(new Edge(s, t, w));
    }

    /**
     * 查找顶点s到顶点t的最短路径
     * @param s 起始顶点
     * @param t 目标顶点
     */
    public List<Integer> search(int s, int t) {
        int n = this.vertexes.length;
        // 用来还原最短路径
        Map<Integer, Integer> predecessor = new HashMap<>();
        // 初始化起始顶点到每个顶点的距离
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int id = this.vertexes[i];
            // 初始将距离设置为无限大
            vertexMap.put(id, new Vertex(id, Integer.MAX_VALUE));
        }

        // 构建小顶堆
        // 用来记录已经遍历到的顶点以及这个顶点与起始顶点的路径长度
        MinHeap<Vertex> heap = new MinHeap<>(n);
        // 标记是否进入过队列
        Map<Integer, Boolean> inqueue = new HashMap<>();
        vertexMap.get(s).dist = 0;
        heap.add(vertexMap.get(s));
        inqueue.put(s, true);
        while (!heap.isEmpty()) {
            // 删除堆顶元素
            Vertex minVertex = heap.pop();
            // 最短路径产生了
            if (minVertex.id == t) break;
            for (int i = 0, m = this.adj.get(minVertex.id).size(); i < m; i++) {
                // 取出一条minVertex相连的边
                Edge edge = this.adj.get(minVertex.id).get(i);
                // minVertex -> nextVertex
                Vertex nextVertex = vertexMap.get(edge.tid);
                // 存在另一条更短的路径经过minVertex到nextVertex
                if (minVertex.dist + edge.w < nextVertex.dist) {
                    nextVertex.dist = minVertex.dist + edge.w;
                    predecessor.put(nextVertex.id, minVertex.id);
                    if (inqueue.getOrDefault(nextVertex.id, false)) {
                        // 更新堆中的值
                        heap.update(nextVertex);
                    } else {
                        heap.add(nextVertex);
                        inqueue.put(nextVertex.id, true);
                    }
                }
            }
        }

        // 还原最短路径
        List<Integer> result = new ArrayList<>();
        int v = t;
        while (v != s) {
            result.add(0, v);
            v = predecessor.get(v);
        }
        result.add(0, v);
        return result;
    }
}
