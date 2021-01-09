package current_demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CountDownLatchDemo2 {
    Executor executor = Executors.newFixedThreadPool(2);

    public void checkAccount() throws InterruptedException {
        while (true) {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            executor.execute(() -> {
                System.out.println("获取异常账单");
                countDownLatch.countDown();
            });

            executor.execute(() -> {
                System.out.println("获取派送单");
                countDownLatch.countDown();
            });

            //等待两个查询结束
            countDownLatch.await();
            System.out.println("已经对账一次");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo2 demo2 = new CountDownLatchDemo2();
        demo2.checkAccount();
    }
}
