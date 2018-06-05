public class SyncArrayDemo {
	
	/**
	 * 题目：生产者-消费者。
	 * 同步访问一个数组Integer[10]，生产者不断地往数组放入整数1000，数组满时等待；消费者不断地将数组里面的数置零，数组空时等待。
	 */
      
    private static final int[] mArray =new int[10];
    private static int mSize = 0;
    private static final int MAX_SIZE=10;
    
    // 用于互斥访问  array 对象
    private final Object mArrayLock = new Object();
    
    public Object getArrayLock() {
		return mArrayLock;
	}
      
    public static void main(String[] args)  {    	
    	SyncArrayDemo pc=new SyncArrayDemo();
    	new Thread(pc.new Consumer()).start();
        new Thread(pc.new Produce()).start();
    }  
    
    private void safeWait() {
    	try {
			getArrayLock().wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
    public void consume(){
    	synchronized(getArrayLock() ) {
    		// 全部消费完
    		while(mSize >0) {
    			mArray[--mSize] = 0;
    			System.out.println("successful consume and now size="+mSize);
    		}
    		// 通知生产者线程开始生产
    		getArrayLock().notifyAll();
    		
    		// 自己等待
			System.out.println("消费者线程开始等待");
			safeWait();
			System.out.println("消费者线程结束等待");
    	}
    }
      
    public void produce(int value){
    	synchronized(getArrayLock()) {
    		// 一直生产完（数量得够）
    		while(mSize < MAX_SIZE) {
    			mArray[mSize++] = value;
    			System.out.println("successful produce and now size="+mSize);
    		}
    		// 通知消费者线程消费
    		getArrayLock().notifyAll();
    		// 自己等待
            safeWait();
    	}
    }  

	class Produce implements Runnable{
        public void run(){
            while(true) {
            	produce(1000);
            }
            	
        }
    }
    
    class Consumer implements Runnable{
        public void run(){
            while(true) {
            	consume();
            }
        }
    }

}  
