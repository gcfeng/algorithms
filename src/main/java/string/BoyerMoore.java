package string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Boyer-Moore算法，其本质是在每次不匹配时，尽量往后多移动模式串。为了寻找这种规律，Boyer-Moore算法
 * 依赖下面两个规则。
 *
 * 1、坏字符规则（bad character rule）
 *  每次匹配按照模式串的倒序来进行比对，如下：
 *
 *  a b c a e a b d c
 *  a b d               <- 从d到a开始匹配
 *
 *  将主串中第一个不匹配的字符叫作坏字符，比如这里的"c"就是一个坏字符。将坏字符c与模式串的字符进行比较，
 *  发现模式串中并不存在c这个字符，这个时候我们就可以将模式串整体移动到c后面进行下一次匹配。
 *
 *  a b c a e a b d c
 *        a b d         <- 从d到a开始匹配
 *
 *  坏字符为a，但是坏字符a与模式串的第一个字符a匹配，这个时候我们移动模式串将两个a对齐。
 *
 *  a b c a e a b d c
 *            a b d
 *
 *  总结一下规律，我们定义坏字符对应模式串的字符下标为oIndex，对应上面的例子，两次oIndex都是2。如果
 *  该坏字符在模式串中存在，将该坏字符在模式串中的下标为pIndex，如果不存在，pIndex就记作-1。对应上面
 *  的例子，第一次pIndex=-1，第二次pIndex=0。那模式串往后移动的位数就等于oIndex - pIndex。
 *
 *  需要注意的是，如果坏字符在模式串中多次出现，pIndex取靠后的一个。这样就不会让模式串滑动过多，导致本来
 *  匹配的情况被滑过忽略。
 *
 *  在最好情况下，坏字符规则每次都能滑过模式串的长度，其时间复杂度是O(n/m)。不过oIndex - pIndex有可能
 *  会出现负数。比如主串是aaaaaaa，模式串是baaa，此时模式串会后退。所以Boyer-Moore算法还需要依赖好后缀
 *  规则。
 *
 * 2、好后缀规则（good suffix shift）
 *  查看下面匹配：
 *
 *  a b c a c a e b c e a b c d b c f
 *        a b c d b c                       <- 从后往前匹配，末尾匹配了bc
 *
 *  我们将已经匹配的bc叫作好后缀，记为suffix。在模式串中查找suffix，将匹配成功的子串记为prefix，然后将
 *  模式串滑动到prefix与主串中suffix对齐的位置。如下图：
 *
 *  a b c a c a e b c e a b c d b c f
 *              a b c d b c
 *
 *  如果在模式串中没有找到另一个等于suffix的子串，我们就直接将模式串滑动到主串中suffix的后面，因为之前的
 *  任何一次往后滑动，都不会匹配主串中suffix的情况。
 *
 *  a b c a c a e b c e a b c d b c f
 *        a b e d b c                       <- 模式串不存在另一个bc子串，直接将模式串滑动到主串中bc的后面
 *                    a b e d b c
 *
 *  注意这里会有个问题，看下图：
 *
 *  a b c a c a e b c b e b c d b c f
 *          c b e b c
 *                    c b e b c             <- 滑动过度
 *                  c b e b c               <- 正确滑动
 *
 *  bc是好后缀，只要主串中的suffix与模式串有重合，肯定就无法匹配。但是如果主串suffix的后缀与模式串的前缀有
 *  部分重合的时候，就有可能会出现完全匹配。针对这种情况，我们不仅要考虑好后缀在模式串中是否有另一个完全匹配
 *  的子串，还要考虑好后缀的后缀子串，是否存在跟模式串中的前缀子串相匹配的。比如abc的后缀子串为b,bc，
 *  前缀子串为a,ab。我们从好后缀的后缀子串中，找一个最长的并且能跟模式串的前缀子串匹配的，记为subSuffix。
 *  然后就将模式串滑动到主串中subSuffix的位置。
 *
 *
 *  Boyer-Moore算法在匹配过程中，分别计算坏字符规则和好后缀规则往后滑动的位数，然后取两者中最大的。
 *
 *
 *  更多阅读：
 *  http://www.cs.jhu.edu/~langmea/resources/lecture_notes/boyer_moore.pdf
 */
public class BoyerMoore {
    // 用来存储模式串中字符对应的下标
    private static Map<Character, Integer> map = new HashMap<>();
    // 用来存储模式串中后缀子串匹配的下标
    // 数组的下标表示后缀子串的长度，数组值为相匹配的前缀子串的下标值
    // 比如模式串为： abcabc，suffixes数组存储如下：
    // 子串    长度    suffixes
    // c        1     suffixes[1] = 2
    // bc       2     suffixes[2] = 1
    // abc      3     suffixes[3] = 0
    // cabc     4     suffixes[4] = -1
    // bcabc    5     suffixes[5] = -1
    private static int[] suffixes;

    public static int search(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();
        if (str.isEmpty() || pattern.isEmpty() || n < m) return -1;

        generateBC(pattern);
        generateGS(pattern);

        for (int i = 0; i < n - m + 1;) {
            StringBuilder suffix = new StringBuilder();
            // 倒序比对
            for (int j = i + m - 1; j >= 0; j--) {
                if (str.charAt(j) == pattern.charAt(j - i)) {
                    suffix.insert(0, str.charAt(j));
                    // 匹配成功
                    if (suffix.length() == pattern.length()) {
                        return i;
                    }
                } else {
                    // 求出坏字符规则下滑动距离
                    int diffBC = getBC(pattern, str.charAt(j), j - i);
                    int diffGS = getGS(pattern, suffix.toString(), j - i);
                    i += Math.max(diffBC, diffGS);
                    break;
                }
            }
        }
        return -1;
    }

    // 根据坏字符规则求出滑动距离
    private static int getBC(String pattern, Character badChar, int oIndex) {
        // 求出坏字符在模式串中的下标
        int pIndex = map.get(badChar) == null ? -1 : map.get(badChar);
        return oIndex - pIndex;
    }

    // 根据好后缀规则求出滑动距离
    private static int getGS(String pattern, String suffix, int oIndex) {
        int index = suffixes[suffix.length()];
        // 存在与好后缀匹配的前缀子串
        if (index != -1) return oIndex - index + 1;

        // 不存在与好后缀匹配的前缀子串，求出最大的匹配后缀子串
        int len = 0;
        for (int i = suffix.length() - 1; i >= 1; i--) {
            if (suffixes[i] != -1) {
                index = suffixes[i];
                len = i;
                break;
            }
        }
        if (index != -1) {
            return (pattern.length() - (index + len));
        }

        // 没有匹配的后缀子串，移动模式串的长度
        return pattern.length();
    }

    // 构建模式串字符的散列表，根据该散列表我们可以快速知道坏字符在模式串的哪个位置
    private static void generateBC(String pattern) {
        for (int i = 0, m = pattern.length(); i < m; i++) {
            map.put(pattern.charAt(i), i);
        }
    }

    // 预处理模式串的后缀子串
    private static void generateGS(String pattern) {
        suffixes = new int[pattern.length()];
        Arrays.fill(suffixes, -1);
        StringBuilder sub = new StringBuilder();
        for (int i = pattern.length() - 1; i >= 0; i--) {
            sub.insert(0, pattern.charAt(i));
            String subStr = sub.toString();
            int subLen = subStr.length();
            for (int j = 0; j < i; j++) {
                String prefix = pattern.substring(j, j + subLen);
                if (prefix.equals(subStr)) {
                    suffixes[subLen] = j;
                }
            }
        }
    }
}
