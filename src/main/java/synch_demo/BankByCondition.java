package synch_demo;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br>
 * 〈条件变量使用代码示例〉安全性检查
 *
 * @author wensir
 * @create 2018/12/25
 * @since 1.0.0
 */
public class BankByCondition {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;


    public BankByCondition(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            while (accounts[from] < amount) {  //校验账户金额是否小于要转出的金额，如果条件为否
                sufficientFunds.await(); // 线程等待
            }
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf("%10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n",getTotalBalance());
            sufficientFunds.signalAll();


        }finally {
            bankLock.unlock();
        }
    }

    private double getTotalBalance() {
        bankLock.lock();  //可重入性体现，当前线程已经获取到了该锁再次获得时会默认获取到，并且持有计数加1；
        try {
            double sum = 0;
            for (double a : accounts) {
                sum += a;
            }
            return sum;
        }finally {
            bankLock.unlock();
        }
    }

    public int size() {
        return accounts.length;
    }

}