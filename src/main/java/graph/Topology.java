package graph;

import java.util.*;

/**
 * 拓扑排序。由一个有向无环图的顶点组成的序列，并且需要满足以下条件：
 * - 每个顶点出现且只出现一次
 * - 若A在序列中排在B的前面，则在图中不存在从B到A的路径
 *
 * 应用场景：
 * - 输出源文件的依赖编译关系
 */
public class Topology {
    // 顶点列表
    private int[] vertexes;
    // 邻接表
    private Map<Integer, List<Integer>> adj;
    // 逆邻接表
    private Map<Integer, List<Integer>> inverseAdj;

    public Topology(int[] vertexes) {
        this.vertexes = vertexes;
        adj = new HashMap<>();
        inverseAdj = new HashMap<>();
        for (int i = 0, n = vertexes.length; i < n; i++) {
            adj.put(vertexes[i], new LinkedList<>());
            inverseAdj.put(vertexes[i], new LinkedList<>());
        }
    }

    /**
     * 添加边 s -> t
     */
    public void addEdge(int s, int t) {
        List<Integer> list = adj.get(s);
        list.add(t);
    }

    /**
     * Kahn算法，用的是贪心算法思想。
     *
     * 从图中找出一个入度为0的顶点，将其输出到结果序列中，并且将这个顶点从图中删除（也就是将这个顶点可达的顶点的入度都减1）。
     * 循环这个过程，直到所有的顶点都被输出。
     *
     * 时间复杂度：O(V+E)
     */
    public List<Integer> sortByKahn() {
        // 统计每个顶点的入度
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int i = 0, n = vertexes.length; i < n; i++) {
            int iv = vertexes[i];
            // 如果顶点还没有入度值，先初始化顶点的入度为0
            inDegree.put(iv, inDegree.getOrDefault(iv, 0));
            for (int j = 0, m = adj.get(iv).size(); j < m; j++) {
                int ov = adj.get(iv).get(j);
                inDegree.put(ov, inDegree.getOrDefault(ov, 0) + 1);
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0, n = vertexes.length; i < n; i++) {
            int v = vertexes[i];
            if (inDegree.get(v) == 0) queue.add(v);
        }

        List<Integer> ret = new ArrayList<>();
        while (!queue.isEmpty()) {
            int v = queue.remove();
            ret.add(v);
            // 删除顶点v
            for (int j = 0, m = adj.get(v).size(); j < m; j++) {
                int ov = adj.get(v).get(j);
                int degree = inDegree.get(ov);
                inDegree.put(ov, --degree);
                if (degree == 0) queue.add(ov);
            }
        }

        return ret;
    }

    /**
     * 使用深度优先遍历
     *
     * 时间复杂度：O(V+E)
     */
    public List<Integer> sortByDFS() {
        // 通过邻接表生成逆邻接表。边s->t表示s依赖于t，t先于s
        for (int i = 0, n = vertexes.length; i < n; i++) {
            int iv = vertexes[i];
            for (int j = 0, m = adj.get(iv).size(); j < m; j++) {
                int ov = adj.get(iv).get(j);
                List<Integer> list = inverseAdj.get(ov);
                list.add(iv);
            }
        }
        // 深度优先遍历图
        List<Integer> ret = new ArrayList<>();
        Map<Integer, Boolean> visited = new HashMap<>();
        for (int i = 0, n = vertexes.length; i < n; i++) {
            int v = vertexes[i];
            if (!visited.getOrDefault(v, false)) {
                visited.put(v, true);
                dfs(v, visited, ret);
            }
        }
        return ret;
    }

    // 递归处理每个顶点。对于顶点v来说，先输出它所有可达的顶点，然后再输出自己
    private void dfs(int v, Map<Integer, Boolean> visited, List<Integer> ret) {
        List<Integer> inList = inverseAdj.get(v);
        for (int i = 0, m = inList.size(); i < m; i++) {
            int iv = inList.get(i);
            if (visited.getOrDefault(iv, false)) continue;
            visited.put(iv, true);
            dfs(iv, visited, ret);
        }
        ret.add(v);
    }
}
