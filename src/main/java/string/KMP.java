package string;

/**
 * KMP算法
 *
 * 在开始之前，我们先来了解前缀子串和后缀子串。假如模式串为ABCDABD，其前缀子串和后缀子串分别为：
 * 前缀子串：A、AB、ABC、ABCD、ABCDA、ABCDAB
 * 后缀子串：D、BD、ABD、DABD、CDABD、BCDABD
 *
 * KMP算法依赖一个部分匹配值数组，该数组是模式串前缀和后缀子串的最长的共有元素的长度。假如模式串是ABCDABD：
 * - "A"的前缀和后缀都为空集，共有元素的长度为0
 * - "AB"的前缀为[A]，后缀为[B]，共有元素的长度为0
 * - "ABC"的前缀为[A、AB]，后缀为[C、BC]，共有元素的长度为0
 * - "ABCD"的前缀为[A、AB、ABC]，后缀为[D、CD、BCD]，共有元素的长度为0
 * - "ABCDA"的前缀为[A、AB、ABC、ABCD]，后缀为[A、DA、CDA、BCDA]，最长共有元素为A，长度为1
 * - "ABCDAB"的前缀为[A、AB、ABC、ABCD、ABCDA]，后缀为[B、AB、DAB、CDAB、BCDAB]，最长共有元素为AB，长度为2
 * - "ABCDABD"的前缀为[A、AB、ABC、ABCD、ABCDA、ABCDAB]，后缀为[D、BD、ABD、DABD、CDABD、BCDABD]，共有元素的长度为0
 *
 * 下面演示一个匹配过程：
 *
 * 步骤1：
 * BBC ABCDAB ABCDABCDABDE
 * ABCDABD
 * 主串第一个字符B与模式串第一个字符A不匹配，模式串后移一位
 *
 * 步骤2：
 * BBC ABCDAB ABCDABCDABDE
 *     ABCDABD
 * 一直重复步骤1，直到上图。主串字符" "不匹配模式串最后一个字符D，这里其实已经知道匹配了ABCDAB。
 * KMP算法的核心是要利用这个信息来让模式串尽量往后移动。根据前面我们知道ABCDAB的共有元素长度为2，
 * 需要移动的位数等于已匹配的字符数减去部分匹配值。这里移动位数 = 6 - 2 = 4。共有元素是AB，相当于
 * 将前缀AB移动到后缀AB匹配的位置上。移动成步骤3。
 *
 * 步骤3：
 * BBC ABCDAB ABCDABCDABDE
 *         ABCDABD
 * 已匹配子串为AB，部分匹配值为0，移动位数 = 2 - 0 = 2
 *
 * 步骤4：
 * BBC ABCDAB ABCDABCDABDE
 *           ABCDABD
 * 无匹配子串，往后移动一位
 *
 * 步骤5：
 * BBC ABCDAB ABCDABCDABDE
 *            ABCDABD
 * 已匹配子串ABCDAB，部分匹配值为2，移动位数 = 6 - 2
 *
 * 步骤6：
 * BBC ABCDAB ABCDABCDABDE
 *                ABCDABD
 * 成功匹配，退出
 */
public class KMP {
    public static int search(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();
        if (str.isEmpty() || pattern.isEmpty() || n < m) return -1;
        int[] next = getNext(pattern);
        // 主串指针一直往后移动，只是不停的调整模式串的指针
        int matched = 0;
        for (int i = 0; i < n; i++) {
            while (matched > 0 && str.charAt(i) != pattern.charAt(matched)) {
                matched = next[matched - 1];
            }
            if (str.charAt(i) == pattern.charAt(matched)) matched++;
            if (matched == m) { // 找到模式串了
                return i - m + 1;
            }
        }

        return -1;
    }

    /**
     * 构建部分匹配值数组。
     * 假设模式串为abababzabababa，前后缀子串的最大匹配数如下：
     * 子串：      a b a b a b z a b a b a b a
     * 最大匹配数： 0 0 1 2 3 4 0 1 2 3 4 5 6 ?
     *
     * 我们知道了abababzababab的最大子串是ababab，次大就是abab。观察发现，次大匹配的abab只能
     * 出现在最大子串ababab中，所以次大匹配长度就是ababab的最大匹配长度。接着往下，第三大在abab中查找，
     * 根据上表得出第三大匹配子串是ab。这样，在计算下一个子串的最大匹配子串时，就可以复用前面已经算出来
     * 的结果。
     *
     * 根据前面的讨论来计算一下?的结果。我们已经知道abababzababab的最大匹配子串长度是6，最后一个字符是a，
     * a != z，所以abababzabababa的最大匹配子串长度不能是 6 + 1，而是需要回退到次大匹配abab，刚好abab
     * 之后的a与末尾的a匹配，所以?处的最大匹配长度是4+1=5。
     */
    private static int[] getNext(String pattern) {
        int m = pattern.length();
        int[] next = new int[m];
        int maxLength = 0;
        for (int i = 1; i < m; i++) {
            while (maxLength > 0 && pattern.charAt(maxLength) != pattern.charAt(i)) {
                maxLength = next[maxLength - 1];
            }
            if (pattern.charAt(maxLength) == pattern.charAt(i)) maxLength++;
            next[i] = maxLength;
        }
        return next;
    }
}
