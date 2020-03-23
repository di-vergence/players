package implementation.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class Main {

    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue<String>();
        Player initiator = new Player(Player.INITIATOR, queue);
        Player listener = new Player(Player.LISTENER, queue);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(initiator);
        executorService.execute(listener);
        executorService.shutdown();
    }

}