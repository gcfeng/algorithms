package string;

/**
 * RK算法(Rabin-Karp算法)，是BF算法的升级版。
 *
 * 算法逻辑：假设主串A，长度为n，模式串B，长度为m。通过哈希算法对主串中的n-m+1个子串分别求哈希值，
 * 然后逐个与模式串的哈希值比较大小。
 *
 * 时间复杂度为O(n)，如果有大量冲突，时间复杂度会退化为O(n * m)。
 */
public class RabinKarp {
    public static int search(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();
        if (str.isEmpty() || pattern.isEmpty() || n < m) return -1;
        long patHash = hash(pattern);
        for (int i = 0; i < n - m + 1; i++) {
            String sub = str.substring(i, i + m);
            long h = hash(sub);
            if (patHash == h && pattern.equals(sub)) return i;
        }
        return -1;
    }

    /**
     * 计算子串的哈希值。
     *
     * 哈希算法的设计比较有技巧。假设要匹配的字符串的字符集中只包含K个字符，我们可以用一个K进制数来表示
     * 一个子串，这个K进制数转化为十进制数来作为子串的哈希值。假设字符串子集中共有26个字母，我们设定K=26，
     * 例如a表示0，b表示1，c表示2，则子串"cba"的哈希值为：
     * ```
     * cba = "c" * 26^2 + "b" * 26 + "a"
     *     = 1 * 26^2 + 2 * 26 + 0
     * ```
     * 该方法不会发生冲突，不过当模式串很大时，计算出来的哈希值会有溢出的风险。在我们这个场景下，哈希算法
     * 是允许有冲突的，如果两个子串的哈希值一样，我们只需要再简单判断一下两个子串是否相等就可以了。
     */
    private static long hash(String str) {
        long h = 0;
        // 假定字符串中都是ASCII字符，简单将字符的ASCII值相加作为哈希值
        for (int i = 0, n = str.length(); i < n; i++) {
            h += str.charAt(i);
        }
        return h;
    }
}
