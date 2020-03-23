package implementation.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Initiator {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("inijtiator started at port " + port);
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String message = in.readLine();
        System.out.println("initiator :: response " + message);
        return message;
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("initiator shut down");
    }

    public static void main(String[] args) throws InterruptedException {
        int port = Integer.parseInt(args[0]);
        Initiator initiator = new Initiator();
        try {
            initiator.start("127.0.0.1", port);
            String msg = "1";
            for (int messageCounter = 1; messageCounter < 11; ) {
                String message = initiator.sendMessage(msg);
                msg = message + ++messageCounter;
            }

            initiator.sendMessage("exit");
            initiator.stop();
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        }
    }
}