import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

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
        ExecutorService thread_factory= Executors.newCachedThreadPool();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        boolean exit=true;
        int Capacity=100;
        Pr_Co_buffer buffer = new Pr_Co_buffer();
        Semaphore sem = new Semaphore(Capacity);
        while(exit){
        str=br.readLine();
        System.out.println(str);

        switch(choices.getValue(str)){
            case CONSUMER:{
                thread_factory.submit(() -> new Consumer(buffer,sem).run());
                System.out.println("Consumer added");
               break;
            }
            case MINE:{break;}
            case PRODUCER:{
                thread_factory.submit(() -> new Producer(buffer,sem,"cars").run());
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
