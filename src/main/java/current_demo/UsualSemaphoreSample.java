package current_demo;

import java.util.concurrent.Semaphore;

/**
 * <br>
 * 〈Semaphore经典实现〉
 * 限制资源访问：车站等车，每来五个车，上五个人出发后再来五个，，，，
 *
 * @author wensir
 * @create 2019/2/21
 * @since 1.0.0
 */
public class UsualSemaphoreSample {
    public static void main(String[] args) {
        System.out.println("Action ... Go!");
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new SemaphoreSampleWorker(semaphore));
            t.start();
        }
    }

}

class SemaphoreSampleWorker implements Runnable {
    private String name;

    private Semaphore semaphore;

    public SemaphoreSampleWorker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        try {
            log("is Waiting for permit!");

            semaphore.acquire();
            log("acquire a permit");
            log("excuted!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            log("release a permit!");
            semaphore.release();
        }


    }

    private void log(String msg){
        if (name == null) {
            name = Thread.currentThread().getName();
        }
        System.out.println(name + ":"+msg);

    }

}
