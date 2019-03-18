import java.util.concurrent.Semaphore;

public class Producer implements Runnable {
    private Pr_Co_buffer buffer;
    private String product_name;
    private Semaphore sem;
    public Producer(Pr_Co_buffer obj,Semaphore sem,String product_name) {
        this.buffer = obj;
        this.sem = sem;
        this.product_name = product_name;
    }
    public void production() throws InterruptedException {
        while(true){
        sem.acquire();
        Product p1=new Product(product_name);
        Thread.sleep(250);
        buffer.put(p1);
        }
    }

    @Override
    public void run() {
        try {
            production();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
