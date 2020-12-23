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
     * BFS算法，时间复杂度是O(n)
     */
    public int solution1(String[] deadends, String target) {
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

    /**
     * 双向BFS算法，时间复杂度O(N)
     */
    public int solution2(String[] deadends, String target) {
        HashSet<String> deads = new HashSet<>(Arrays.asList(deadends));
        // 用集合而不用队列，是为了下面可以快速判断元素是否存在
        HashSet<String> queue1 = new HashSet<>();
        HashSet<String> queue2 = new HashSet<>();
        HashSet<String> visited = new HashSet<>();

        // 初始化访问步数
        int step = 0;
        queue1.add("0000");
        queue2.add(target);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            // queue1在遍历的时候不能变更，这里使用temp来记录扩散的结果
            HashSet<String> temp = new HashSet<>();

            for (String cur : queue1) {
                // 死亡数字
                if (deads.contains(cur)) continue;
                // 开始队列和结束队列有交叉，说明已经会合
                if (queue2.contains(cur)) return step;
                visited.add(cur);

                // 将相邻的结点添加到队列中
                for (int j = 0; j < 4; j++) {
                    String up = plusOne(cur, j);
                    if (!visited.contains(up)) temp.add(up);
                    String down = minusOne(cur, j);
                    if (!visited.contains(down)) temp.add(down);
                }
            }

            // 增加步数
            step++;
            // 交换两个队列，这里相当于交替来遍历两个队列
            queue1 = queue2;
            queue2 = temp;
        }

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
        System.out.println(lock.solution1(deadends, "0202")); // 6
        System.out.println(lock.solution2(deadends, "0202")); // 6
    }
}
