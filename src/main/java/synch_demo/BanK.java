package synch_demo;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private Lock bankLock = new ReentrantLock();

    /**
     * 初始化所有账户金额
     *
     * @param n             数量
     * @param initalBalance 账户初始化值
     */
    public BanK(int n, double initalBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initalBalance);
    }

    /**
     * 将一个账户的钱转移到另外一个账户中
     *
     * @param from   金额来源方
     * @param to     金额获取方
     * @param amount 要转移的钱数
     */
    public void transfer(int from, int to, double amount) {

        bankLock.lock();  //加锁保证安全
        try {

            if (accounts[from] < amount) return; //当金额来源方资金不足时，结束线程
            System.out.print(Thread.currentThread()); //打印线程

            accounts[from] -= amount;  //金额来源方减去相应的转移金额。
            System.out.printf("%10.2f from %d to %d", amount, from, to);
            /*
            该行代码非原子性操作指令可能如下：
            1）将accounts[to]加载到寄存器
            2）增加amount
            3）将结果写回accounts[to]
            假定第一个线程执行步骤1和2，然后它被剥夺了运行权。第2个线程被唤醒并修改了accounts数组中的同一项。然后第一个线程被唤醒并完成其第三步将第一个
            线程前两步执行的结果写会了数组，这一动作擦去了第二个线程所做的更新，于是总金额不再正确。
             */
            accounts[to] += amount;//金额获取方加入相应的金额
            //打印所有账户总金额

            /*
            可重入锁：
            1.transfer方法调用了getTotalBalance方法，这也会封锁bankLock对象（该线程已经持有该锁，故默认获取到），
                此时bankLock对象的持有计数器为2.
            2.当getTotalBalance方法退出的时候，持有计数变回1。当transfer方法退出的时候，持有计数变为0。
            3.线程释放锁。
             */
            System.out.printf(" Total Balance:%10.2f%n", getTotalBalance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            bankLock.unlock();
        }

    }

    /**
     * 计算所有账户总金额之和
     *
     * @return
     */
    private double getTotalBalance() {
        double sum = 0;
        for (double a : accounts) {
            sum += a;

        }
        return sum;
    }

    public int size() {
        return accounts.length;

    }


}