package current_demo;

import java.util.concurrent.Semaphore;

/**
 * <br>
 * 〈非典型Semaphore〉
 *
 * @author wensir
 * @create 2019/2/21
 * @since 1.0.0
 */
public class AbnormalSemaphoreSample {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        for (int i = 0 ; i <= 10 ; i++) {
            Thread t = new Thread(new MyWorker(semaphore));
            t.start();
        }
        System.out.println("Action Go....");
        semaphore.release(5);
        System.out.println("Wait for permits Off....");
        while (semaphore.availablePermits() != 0) {
            Thread.sleep(100L);

        }
        System.out.println("Action Go again...");
        semaphore.release(5);

    }



}

class MyWorker implements Runnable {

    private Semaphore semaphore;
    public MyWorker(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        try {
            semaphore.acquire();
            System.out.println("excutes");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}