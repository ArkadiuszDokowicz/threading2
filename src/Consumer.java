import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
    private Pr_Co_buffer buffer;
    private String product_name;
    private Semaphore sem;
    private Product product =null;
    public Consumer(Pr_Co_buffer obj,Semaphore sem) {
        this.buffer = obj;
        this.sem = sem;
    }
    public void consumption() throws InterruptedException {
        while(true){
            Thread.sleep(300);
            Product product= null;
            product=buffer.get();
            if(product!=null){
               // System.out.println(product.name);
            }
            else{
                System.out.println("buffer is empty");
            }
            sem.release();
        }
    }

    @Override
    public void run() {
        try {
            consumption();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
