package co.lijian.lib;

public class ArraySyncDemo {

    static int[] mArray = new int[10];

    // 用于互斥访问数组
    static final Object mLock = new Object();

    // 用于同步线程。
    static int mSize = 0;

    static class Producer implements Runnable {
        int [] array;

        public Producer(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {

            while(true) {
                synchronized(mLock) {
                    while (mSize >= 10) {
                        try {
                            mLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("生产者线程操作数组: 开始");
                    for (int i = 0; i < 10; i++) {
                        array[i] = i;
                    }
                    mSize = 10;
                    System.out.println("生产者线程操作数组: 结束");
                    mLock.notify();
                }
                System.out.println("");
            }
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
                    while (mSize <= 0) {
                        try {
                            mLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("消费者线程操作数组: 开始");
                    for (int i = 0; i < 10; i++) {
                        System.out.println(array[i]);
                        array[i] = 1000;
                    }
                    mSize = 0;
                    System.out.println("费者线程操作数组: 结束");
                    mLock.notify();
                }
                System.out.println("");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Program started");
        Thread producer = new Thread(new Producer(mArray));
        Thread consumer = new Thread(new Consumer(mArray));
        producer.start();
        consumer.start();
    }
}

// 这个版本保证了对数组的互斥访问，即，生产者在生产时，消费者不会消费；消费者在消费时，生产者不会生产。
// 实现 生产者-消费者同步。即没有实现：生产 -> 消费 -> 生产 -> 消费。
