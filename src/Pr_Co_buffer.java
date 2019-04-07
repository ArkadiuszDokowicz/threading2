import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class Pr_Co_buffer {
    ////////////////////////////
    private volatile int producers=0;
    private volatile long productRequest=0;
    private boolean duringTest=false;
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
    if(duringTest==true){
        this.incrementationProductRequests();
    }
    return queue.poll();
    }

    public long getProductRequests() {
        this.duringTest=false;
        return productRequest;
    }


    public void setProductRequests(long productRequest) {
        this.duringTest=true;

        this.productRequest = productRequest;
    }

    private void incrementationProductRequests(){
        this.productRequest++;
    }
    public void producerRegistration(){
        producers++;
    }

    public int getProducers() {
        return producers;
    }
    public void printBuffer(){
        Iterator iteratorValues = queue.iterator();

        // Print elements of iterator
        System.out.println("Buffer state:");
        while (iteratorValues.hasNext()) {
             if(iteratorValues.next()!=null)
            System.out.print(1+",");
        }
        System.out.println();
    }
}
