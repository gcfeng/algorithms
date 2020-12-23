package problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 打开转盘锁
 *
 * https://leetcode-cn.com/problems/open-the-lock/
 */
public class OpenLock {
    /**
     * BFS算法
     */
    public int solution(String[] deadends, String target) {
        // 默认将死亡数字加入到已经访问过的Set中，在后续的遍历中不会访问这些元素
        HashSet<String> visited = new HashSet<>(Arrays.asList(deadends));
        LinkedList<String> queue = new LinkedList<>();
        int step = 0;
        queue.add("0000");

        while (!queue.isEmpty()) {
            int sz = queue.size();
            // 将当前队列的所有结点向四周扩散
            for (int i = 0; i < sz; i++) {
                String cur = queue.removeFirst();
                // 当前结点已经被访问过，跳过
                if (visited.contains(cur)) continue;
                // 已经到达了终点
                if (cur.equals(target)) return step;

                // 结点已经被访问过了
                visited.add(cur);

                // 将相邻的结点添加到队列中
                for (int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if (!visited.contains(up)) {
                        queue.add(up);
                    }
                    String down = minusOne(cur, j);
                    if (!visited.contains(down)) {
                        queue.add(down);
                    }
                }
            }
            // 增加步数
            step++;
        }

        // 穷举完成，没有找到目标，返回-1
        return -1;
    }

    // 将给定转盘上的数字增加1
    private String plusOne(String str, int i) {
        char[] chars = str.toCharArray();
        char ch = chars[i];
        if (ch == '9') chars[i] = '0';
        else chars[i] += 1;
        return new String(chars);
    }

    // 将给定转盘上的数字减少1
    private String minusOne(String str, int i) {
        char[] chars = str.toCharArray();
        char ch = chars[i];
        if (ch == '0') chars[i] = '9';
        else chars[i] -= 1;
        return new String(chars);
    }

    public static void main(String[] args) {
        OpenLock lock = new OpenLock();
        String[] deadends = new String[]{"0201","0101","0102","1212","2002"};
        System.out.println(lock.solution(deadends, "0202")); // 6
    }
}
