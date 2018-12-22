package BaseMutithreaded;

/**
 * <br>
 * 〈实现Runnable接口实现多线程〉
 *
 * @author wensir
 * @create 2018/12/23
 * @since 1.0.0
 */
public class BaseThreadByRunnable implements Runnable{


    public static void main(String[] args) {

        //Runnable是一个函数式接口可以使用lambda表达式建立一个实例
       /* Runnable r = ()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(" i = "+i);

            }
        };*/


        Thread t1 = new Thread(new BaseThreadByThread());
        Thread t2 = new Thread(new BaseThreadByThread());
        t1.start();
        t2.start();

    }
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(" i = " + i);

        }
    }

}