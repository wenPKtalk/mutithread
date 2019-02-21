package current_demo;

import java.util.concurrent.CountDownLatch;

/**
 * <br>
 * 〈CountDownLatch的简单demo〉
 *
 * @author wensir
 * @create 2019/2/21
 * @since 1.0.0
 */
public class LatchSample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new FirstBatchWorker(countDownLatch));
            t.start();
        }

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new SecondBatchWorker(countDownLatch));
            t.start();
        }

        while (countDownLatch.getCount() != 1) {
            Thread.sleep(100L);
            System.out.println("Wait for first batch finish");
            countDownLatch.countDown();

        }
    }

}

/**
 * <br>
 * 〈〉
 *
 * @author wensir
 * @create 2019/2/21
 * @since 1.0.0
 */
class FirstBatchWorker implements Runnable {

    private CountDownLatch countDownLatch;

    public FirstBatchWorker(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("First batch Excuted");
        countDownLatch.countDown();
    }
}

class SecondBatchWorker implements Runnable {

    private CountDownLatch countDownLatch;

    public SecondBatchWorker(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Second batch Excuted");
        countDownLatch.countDown();
    }
}
