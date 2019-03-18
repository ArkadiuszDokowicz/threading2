import java.util.concurrent.ArrayBlockingQueue;

public class Pr_Co_buffer {
    public Pr_Co_buffer(){}
    public ArrayBlockingQueue<Product> queue = new ArrayBlockingQueue<Product>(100);

    synchronized public void put(Product x){
        System.out.println("Product added");
        queue.offer(x);
    }
    synchronized public void get(){
        System.out.println("Product taken");
    queue.poll();
    }
}
