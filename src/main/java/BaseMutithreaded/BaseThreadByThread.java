package BaseMutithreaded;

/**
 * <br>
 * 〈基于Thread抽象类创建线程〉
 *
 * @author wensir
 * @create 2018/12/23
 * @since 1.0.0
 */
public class BaseThreadByThread extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("i = "+i);
        }
    }

    public static void main(String[] args) {

        Thread r1 = new Thread(new BaseThreadByThread());
        Thread r2 = new Thread(new BaseThreadByThread());
        r1.start();
        r2.start();

    }

}