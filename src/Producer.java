import java.util.concurrent.Semaphore;

public class Producer implements Runnable {
    private Pr_Co_buffer buffer;
    private String product_name;
    private Semaphore sem;
    private ProductionAdaptativeController controller;
    public Producer(Pr_Co_buffer obj,Semaphore sem,String product_name,ProductionAdaptativeController controller) {
        this.buffer = obj;
        this.sem = sem;
        this.product_name = product_name;
        buffer.producerRegistration();
        this.controller=controller;
    }
    public void production() throws InterruptedException {
        while(true){
        sem.acquire();
        Product p1=new Product(product_name);
        Thread.sleep(controller.getProductionSpeed());
        if(buffer.put(p1)!=false){
        this.controller.producedItemCounterIncrement();
        }
        else{System.out.println("queue is fully loaded");}
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
