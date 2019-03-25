import java.util.ArrayList;
import java.util.Iterator;

public class ProductionAdaptativeController {
    /////////////////
    private int numberOfProducers=0;
    private Pr_Co_buffer buffer;
    private ArrayList<Integer> FromBufferReader = new ArrayList<>();
    private int optimumBufforoccupancy; //optimal % of buffer capacity
    private int productionSpeed; //in milliseconds
    private float bufferTendention;
    static long lEndTime = System.nanoTime();//end of counting
    static long lStartTime = System.nanoTime();//start counting
    static long output;
    /////////////////
    private void time_start(){
        lStartTime = System.nanoTime();//start counting
    }
    private float time_end(){
        lEndTime = System.nanoTime();//end of counting
        output = lEndTime - lStartTime; //time_counter
        output/=1000000000;
        return Float.valueOf(output);
        // System.out.println("Elapsed time in milliseconds: " + output );
    }

    public ProductionAdaptativeController(int optimumBufforoccupancy, int productionSpeed, Pr_Co_buffer buffer) {
        this.optimumBufforoccupancy = optimumBufforoccupancy;
        this.productionSpeed = productionSpeed;
        this.buffer=buffer;
    }

    public void setNumberOfProducers(int numberOfProducers) {
        this.numberOfProducers = numberOfProducers;
    }

    public void bufferStatistic() throws InterruptedException {
        FromBufferReader.clear();
        int reaserchTimeInSeconds = 5;

        for(int i=0;i<(reaserchTimeInSeconds*10);i++) {
            FromBufferReader.add(buffer.getArraySize());
            Thread.sleep(100);
        }
    }
    public void printBufferStatistic(){
        Iterator<Integer> iterator = FromBufferReader.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next()+" ");
        }
        System.out.println(" ");
    }

    synchronized int getProductionSpeed(){
        return productionSpeed;
    }

    public void start() throws InterruptedException {

        time_start();
        bufferStatistic();
        System.out.println(time_end());
        printBufferStatistic();
    //TODO count trend 
    }

}
