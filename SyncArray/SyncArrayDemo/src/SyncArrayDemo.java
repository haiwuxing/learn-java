import java.util.ArrayList;
import java.util.List;

public class SyncArrayDemo {

    static List<Integer> list = new ArrayList<Integer>();

    static int[] mArray = new int[10];
    static int size = 0;

    static Object mLock = new Object();

    static class Producer implements Runnable {
        int [] array;

        public Producer(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {

            while(true) {
                synchronized(mLock) {
                    while(size >= 10) {
                        try {
                            mLock.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    System.out.println("生产者线程开始操作数组");
                    for (int i = 0; i < 10; i++) {
                        array[i] = i;
                        //System.out.println(array[i]);
                        //randomWait();
                    }
                    size = 10;
                    System.out.println("生产者线程结束操作数组");
                    mLock.notifyAll();
                }
            }

            //To change body of generated methods, choose Tools | Templates.
        }

    }

    static class Consumer implements Runnable {
        int [] array;

        public Consumer(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {

            while(true) {
                synchronized(mLock) {
                    while(size <= 0) {
                        try {
                            mLock.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    System.out.println("消费者线程开始操作数组");
                    for (int i = 0; i < 10; i++) {
                        System.out.println(array[i]);
                        array[i] = 1000;
                        //randomWait();
                    }
                    size = 0;
                    System.out.println("消费者线程结束操作数组");
                    mLock.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer(mArray));
        Thread consumer = new Thread(new Consumer(mArray));
        producer.start();
        consumer.start();
    }
}