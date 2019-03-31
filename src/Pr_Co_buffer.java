import java.util.concurrent.ArrayBlockingQueue;

public class Pr_Co_buffer {
    ////////////////////////////
    volatile int producers=0;

    ///////////////////////////
    public Pr_Co_buffer(){}
    private ArrayBlockingQueue<Product> queue = new ArrayBlockingQueue<Product>(100);

    synchronized public int getArraySize(){
        return this.queue.size();
    }

    synchronized public Boolean put(Product x){
        //System.out.println("Product added");
        Boolean b =queue.offer(x);
    return b;
    }
    synchronized public Product get(){
       // System.out.println("Product taken");
    return queue.poll();
    }
    public void producerRegistration(){
        producers++;
    }

    public int getProducers() {
        return producers;
    }
}
