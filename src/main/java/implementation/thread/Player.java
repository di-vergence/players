package implementation.thread;

import java.util.concurrent.SynchronousQueue;

public class Player implements Runnable {

  public static final String LISTENER = "listener";
  public static final String INITIATOR = "initiator";
  private static final String POISON_PILL = "poison pill";

  private String role;
  private int messageCounter = 0;
  private SynchronousQueue<String> queue;

  public Player(String role, SynchronousQueue<String> queue) {
    this.role = role;
    this.queue = queue;
  }

  public void run() {
    Thread.currentThread().setName(role);
    try {
      if (INITIATOR.equals(role)) {
        queue.put(++messageCounter + "some msg\n");
      }
      while (true) {
        String readMessage = queue.take();
        System.out.println(role + " :: [" + readMessage + "]");
        checkCompletionCondition(readMessage);
        String sendingMessage = messageCounter != 10 ?
            readMessage + " " + ++messageCounter + " some msg\n" :
            POISON_PILL;
        queue.put(sendingMessage);
        checkCompletionCondition(sendingMessage);
      }
    } catch (InterruptedException ignored) {
      System.out.println(role + " complete work");
      // this is interruption policy. intentionally ignored
    }
  }

  private void checkCompletionCondition(String message) throws InterruptedException {
    if (message == POISON_PILL) {
      throw new InterruptedException();
    }
  }
}