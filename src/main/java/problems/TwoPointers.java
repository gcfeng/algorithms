package problems;

/**
 * 双指针
 */
public class TwoPointers {
    /**
     * 给定一个链表，判断链表中是否有环。
     * https://leetcode-cn.com/problems/linked-list-cycle/
     *
     * 使用快慢指针，一个跑得快，一个跑得慢。如果不含有环，跑得快的那个指针最终会遇到null，说明链表
     * 不含有环；如果含有环，快指针最终会超慢指针一圈，和慢指针相遇，说明链表有环。
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }

    /**
     * 给定一个链表，返回链表开始入环的第一个结点。
     * https://leetcode-cn.com/problems/linked-list-cycle-ii/
     *
     * 推导1：慢指针入环第一圈没走完的时候就会和快指针相遇
     * 设环的长度为A，慢指针入环时快指针在环中的位置为B（取值范围从0到A-1），当快慢指针相遇时，
     * 假设慢指针在环中走了距离C，有：
     * C % A = (B + 2C) % A，等价于
     * An + C = B + 2C，合并得
     * C = An - B
     * 当n=1时，满足0<= C < A，这表示慢指针在第一圈中必定能和快指针相遇
     *
     * 假设从链表开始到环开始结点的长度为a，慢指针进入环后，又走了b的距离与快指针相遇，假设环的长度等于
     * b+c。此时快指针已经走完了环的n圈，则快指针走过的总路程为：
     * a + n(b + c) + b = a + (n + 1)b + nc
     * 任意时刻，快指针走过的距离都为慢指针的2倍，且慢指针必定在第一圈与快指针相遇，因此有：
     * a + (n + 1)b + nc = 2(a + b) => a = c + (n - 1)(b + c)
     *
     * 这表明，当快慢指针相遇时，我们设置一个指针从链表开始出发，慢指针也继续步进，它们必定在环开始结点
     * 相遇。
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            // 两个指针相遇，退出循环
            if (fast == slow) break;
        }
        // 没有环
        if (fast == null || fast.next == null) return null;

        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 删除链表的倒数第N个结点
     * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
     *
     * 使用快慢指针，让快指针比慢指针多走N步，之后两者同步前进，当快指针走到末尾时，慢指针指向的就是
     * 链表的倒数第N个结点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head, slow = head;
        // 快指针先前进n步
        while (n-- > 0) {
            fast = fast.next;
        }
        // 到达了末尾，刚好要删除的就是第一个结点
        if (fast == null) return head.next;
        // 加上fast.next != null这个判断条件，是为了能让slow能落在删除元素的前一个位置
        while (fast != null && fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    /**
     * 给定一个已按照升序排列的有序数组，找到两个数使得它们相加之和等于目标数
     * https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1, sum;
        while (left < right) {
            sum = numbers[left] + numbers[right];
            if (sum == target) {
                // 题目中要求下标不是从0开始
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
