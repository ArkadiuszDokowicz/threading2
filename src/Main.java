import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class Main {
    public enum choices{
        PRODUCER("1"),CONSUMER("2"),MINE("3"),EXIT("0"),OTHER;
        String key;

        choices(String key) {
            this.key=key;
        }
        choices(){}

        static choices getValue(String x){
        if("1".equals(x)){return PRODUCER;}
        else if("2".equals(x)){return CONSUMER;}
        else if("3".equals(x)){return MINE;}
        else if("0".equals(x)){return EXIT;}
        else {return OTHER;}
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int Capacity=100;
        Semaphore sem = new Semaphore(Capacity);

        ThreadPoolExecutor thread_factory= (ThreadPoolExecutor) Executors.newCachedThreadPool();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;


        Pr_Co_buffer buffer = new Pr_Co_buffer();
        ProductionAdaptativeController productioncontroller = new ProductionAdaptativeController(50,250,buffer);
        thread_factory.submit(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        productioncontroller.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        boolean exit=true;
        while(exit){
        str=br.readLine();
        System.out.println(str);

        switch(choices.getValue(str)){
            case CONSUMER:{
                thread_factory.submit(() -> new Consumer(buffer,sem).run());
                System.out.println("Consumer added");
               break;
            }
            case PRODUCER:{
                thread_factory.submit(() -> new Producer(buffer,sem,"cars",productioncontroller).run());
                System.out.println("Producer added");
                break;
            }
            case OTHER:{
                break;

            }
            case EXIT:{
                System.out.println("Exit");
                exit=false;
            }
        }
        }

        thread_factory.shutdown();
        thread_factory.awaitTermination(1, TimeUnit.DAYS);

    }
}
