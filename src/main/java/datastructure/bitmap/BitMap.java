package datastructure.bitmap;

/**
 * 位图
 *
 * 假设有1千万个数据，数字范围从1到1亿，需要1亿个二进制位，相当于12MB的内存空间。如果数字范围从1到10亿，则需要120MB的内存空间。
 * 为了进一步降低内存空间，布隆过滤器做了一些改进。用K个哈希函数，对同一个数字进行哈希求值，就会得到K个不同的哈希值，分别记住
 * X1, X2, ..., Xk。将这K个数字作为位图的下标，将对应BitMap[X1], BitMap[X2], ..., BitMap[Xk]都设置为true，也就是说用K
 * 个二进制为来表示一个数字的存在。
 *
 * 布隆过滤器会存在误判，但是只会对存在的情况下误判。如果布隆过滤器判断某个数字不存在，就真的不存在。如果布隆过滤器判断某个数字
 * 存在，它有可能不存在。
 *
 * 布隆过滤器应用场景：
 * - 网络爬虫，判断一个网址是否被访问过
 * - 垃圾邮件过滤
 */
public class BitMap {
    private char[] bytes;
    private int nbits;

    public BitMap(int nbits) {
        this.nbits = nbits;
        // Java中char是2个字节，也就是16bit
        this.bytes = new char[nbits/16 + 1];
    }

    public void set(int k) {
        if (k > nbits) return;
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        bytes[byteIndex] |= (1 << bitIndex);
    }

    public boolean get(int k) {
        if (k > nbits) return false;
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }
}
