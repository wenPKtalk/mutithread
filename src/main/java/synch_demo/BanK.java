package synch_demo;

import java.util.Arrays;

/**
 * <br>
 * 〈〉
 *
 * @author wensir
 * @create 2018/12/23
 * @since 1.0.0
 */
public class BanK {

    // 一家银行，有许多银行账户
    private final double[] accounts;

    /**
     * 初始化所有账户金额
     *
     * @param n 数量
     * @param initalBalance 账户初始化值
     */
    public BanK(int n, double initalBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initalBalance);
    }

    /**
     * 将一个账户的钱转移到另外一个账户中
     * @param from  金额来源方
     * @param to   金额获取方
     * @param amount 要转移的钱数
     */
    public void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) return; //当金额来源方资金不足时，结束线程
        System.out.print(Thread.currentThread()); //打印线程

        accounts[from] -= amount;  //金额来源方减去相应的转移金额。
        System.out.printf("%10.2f from %d to %d", amount, from, to);

        accounts[to] += amount;//金额获取方加入相应的金额
        //打印所有账户总金额
        System.out.printf(" Total Balance:%10.2f%n",getTotalBalance());
    }

    /**
     * 计算所有账户总金额之和
     * @return
     */
    private double getTotalBalance() {
        double sum = 0;
        for (double a : accounts) {
            sum += a;

        }
        return sum;
    }

    public int size(){
        return accounts.length;

    }


}