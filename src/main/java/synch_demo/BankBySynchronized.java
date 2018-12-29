package synch_demo;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <br>
 * 〈内部锁Synchronized实现〉
 *
 * @author wensir
 * @create 2018/12/29
 * @since 1.0.0
 */
public class BankBySynchronized {

    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;


    public BankBySynchronized(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        while (accounts[from] < amount) {  //校验账户金额是否小于要转出的金额，如果条件为否
            wait();
        }
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf("%10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());

        notifyAll();

    }

    private synchronized double getTotalBalance() {
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