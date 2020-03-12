package util;

public class Prime {
    /**
     * 判断是否为素数
     */
    public static boolean isPrime(int val) {
        if (val < 2) return false;
        if (val < 4) return true;
        if ((val % 2) == 0) return false;
        for (int i = 3; i <= Math.floor(Math.sqrt(val)); i += 2) {
            if ((val % i) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 找到val后的下一个素数
     */
    public static int nextPrime(int val) {
        while (!isPrime(val)) val++;
        return val;
    }
}
