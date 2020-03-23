
package implementation.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ListnerServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("listener started at port " + port);
    }

    public void sendMessage(int msgCounter) throws IOException, InterruptedException {
        String msg = in.readLine();
        if (msg.equals("exit")) {
            throw new InterruptedException();
        }
        out.println(msg + msgCounter);
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        ListnerServer server = new ListnerServer();
        int port = Integer.parseInt(args[0]);
        try {
            server.start(port);
            int counter = 1;
            while (true) {
                server.sendMessage(counter);
                counter++;
            }
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.stop();
    }
}
