public class SyncArrayDemo {
	
	/**
	 * 题目：生产者-消费者。
	 * 同步访问一个数组Integer[10]，生产者不断地往数组放入整数1000，数组满时等待；消费者不断地将数组里面的数置零，数组空时等待。
	 */
      
    private static final Integer[] array =new Integer[10];
    private static int size=0;
    private static final int MAX_SIZE=10;
      
    public static void main(String[] args)  {
    	SyncArrayDemo pc=new SyncArrayDemo();
    	new Thread(pc.new Consumer()).start();
        new Thread(pc.new Produce()).start();
        
    }  
  
    public synchronized void consume(){
            while(size<=0){  
                try {  
                    System.out.println(size+"- consume wait");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }  
            array[--size]=0;
            notifyAll();
            System.out.println("successful consume and now size="+size);
    }
      
    public  synchronized void produce(int value){
            while(size==MAX_SIZE){
                try {  
                    System.out.println(size+"- produce wait");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  
            }  
            array[size++]=value;
            notifyAll();  
            System.out.println("successful produce and now size="+size);
    }  
      
    class Produce implements Runnable{
        public void run(){
            while(true)
            	produce(1000);
        }
    }
    
    class Consumer implements Runnable{
        public void run(){
            while(true)
            	consume();
        }
    }

}  
