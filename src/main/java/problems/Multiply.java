package problems;

/**
 * 字符串相乘
 */
public class Multiply {
    /**
     * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
     * 解答：
     * 模拟手工竖式相乘的方式
     *
     * https://leetcode-cn.com/problems/multiply-strings/
     */
    public String solution(String num1, String num2) {
        // 结果最多为m+n位数
        int m = num1.length();
        int n = num2.length();
        int[] res = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                // num1[i]与num2[j]相乘，影响最终结果的res[i+j]和res[i+j+1]
                int sum = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j;
                int p2 = i + j + 1;
                // 叠加到res上
                sum += res[p2];
                res[p2] = sum % 10;
                res[p1] += sum / 10;
            }
        }
        // 输出结果，过滤掉前面没有用到的0位
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (flag) {
                sb.append(res[i]);
            } else if (res[i] != 0) {
                flag = true;
                sb.append(res[i]);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        Multiply multiply = new Multiply();
        System.out.println(multiply.solution("123", "456"));    // 56088
    }
}
