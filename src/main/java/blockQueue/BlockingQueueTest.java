package blockQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <br>
 * 〈阻塞队列测试代码〉
 *
 * @author wensir
 * @create 2019/1/1
 * @since 1.0.0
 */
public class BlockingQueueTest {

    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter bas director (e.g. /opt/jdk1.8.0/src): ");
            String director = in.next();
            System.out.println("Enter keybotd(e.g. volatile):");
            String keyword = in.next();
            Runnable enumerator = () -> {
                try {
                    enumerate(new File(director));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            new Thread(enumerator).start();
            for (int i = 1; i <= SEARCH_THREADS; i++) {
                Runnable searcher = () -> {
                    try {
                        boolean done = false;
                        while (!done) {//搜索完成
                            File file = queue.take();

                            if (file == DUMMY) {
                                queue.put(file);
                                done = true;
                            } else {
                                search(file, keyword);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                new Thread(searcher).start();

            }
        }
        ;


    }

    private static void search(File file, String keyword) {
        try (Scanner in = new Scanner(file, "UTF-8")) {

            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    System.out.printf("%s:%d:%s%n",file.getPath(),lineNumber,line);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void enumerate(File file) throws InterruptedException {
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                enumerate(file1);

            } else {
                queue.put(file1);
            }
        }
    }


}