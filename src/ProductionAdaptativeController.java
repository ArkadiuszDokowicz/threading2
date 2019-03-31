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
    private volatile int producedItemCounter;
    private int lastMeasuredTime;
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
        output/=1000000;//nano to miliseconds
        this.lastMeasuredTime= (int)output;
        return Float.valueOf(output);
        // System.out.println("Elapsed time in milliseconds: " + output );
    }

    public synchronized void productedItemCounterIncrement(){
        this.producedItemCounter++;
    }
    public ProductionAdaptativeController(int optimumBufforoccupancy, int productionSpeed, Pr_Co_buffer buffer) {
        this.optimumBufforoccupancy = optimumBufforoccupancy;//in elements
        this.productionSpeed = productionSpeed; //set base speed
        this.buffer=buffer;
    }

    public void setNumberOfProducers(int numberOfProducers) {
        this.numberOfProducers = numberOfProducers;
    }

    public void bufferStatistic() throws InterruptedException {
        FromBufferReader.clear();
        this.producedItemCounter=0;
        int reaserchTimeInSeconds = 2;

        for(int i=0;i<(reaserchTimeInSeconds*10);i++) {
            FromBufferReader.add(buffer.getArraySize());
            Thread.sleep(100);
        }

    }
    public void setProductionSpeed(){
        this.setNumberOfProducers(this.buffer.producers);
        int firstMeasure= FromBufferReader.get(0);
        int consumedProducts=0;
        int lastMeasure = FromBufferReader.get(FromBufferReader.size()-1);
        int nextProductionTime=this.productionSpeed;
        float productionPerSecondPerOneThread=0;
        float productsConsumedPerSecond=0.0f;
        ////////////

        consumedProducts=(firstMeasure+this.producedItemCounter)-lastMeasure;
        productsConsumedPerSecond=(float)consumedProducts/((float)this.lastMeasuredTime/1000);
        productionPerSecondPerOneThread=productsConsumedPerSecond/(float)this.numberOfProducers;
        System.out.println("Consumption /s: "+(productsConsumedPerSecond)+"Optimal production per one thread("
                +this.numberOfProducers+") /s: "+ productionPerSecondPerOneThread);
        nextProductionTime= (int) (1000/productionPerSecondPerOneThread);
        if(consumedProducts==0){nextProductionTime=100;}
        ////////

        if(firstMeasure>lastMeasure){ //you should increase production
            //System.out.println("NOP: "+this.numberOfProducers+"PPT: "+productionPerSecondPerOneThread );

            if(lastMeasure<this.optimumBufforoccupancy){
                nextProductionTime= (int) Math.round(nextProductionTime*0.9);
                System.out.println("npt:"+nextProductionTime);
            }
        }

        else if(firstMeasure<lastMeasure){ //you should decrease production
            if(lastMeasure>this.optimumBufforoccupancy) {
                if (lastMeasure > this.optimumBufforoccupancy + 10) {
                    System.out.println("slow it down");
                    nextProductionTime = (int) Math.round(nextProductionTime * 1.2);
                } else {
                    nextProductionTime = (int) Math.round(nextProductionTime * 1.05);

                }
            }
        }

        else if(firstMeasure==lastMeasure) { //if production is stable do nothing
        System.out.println("0=0");

            if(lastMeasure<10){
                nextProductionTime=nextProductionTime*2;
            }
            else if(lastMeasure>this.optimumBufforoccupancy+2){ //tolerance +/- 2
                nextProductionTime= (int) Math.round(nextProductionTime*1.2);
            }

            else if(lastMeasure<this.optimumBufforoccupancy-2){ //tolerance +/- 2
                nextProductionTime= (int) Math.round(nextProductionTime*0.8);
            }
        }

        System.out.println("next production time /thread: "+ nextProductionTime);
        this.productionSpeed=nextProductionTime;
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
        setProductionSpeed();
        printBufferStatistic();
    //TODO count trend 
    }

}
