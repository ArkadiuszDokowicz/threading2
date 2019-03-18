import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
    private Pr_Co_buffer buffer;
    private String product_name;
    private Semaphore sem;
    public Consumer(Pr_Co_buffer obj,Semaphore sem) {
        this.buffer = obj;
        this.sem = sem;
    }
    public void consumption() throws InterruptedException {
        while(true){
            Thread.sleep(100);
            buffer.get();
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
