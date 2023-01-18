import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {
    public static Set<ClientHandler> clients = new HashSet<>();
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Executor executor = Executors.newFixedThreadPool(10);
        BufferedReader in;
        PrintWriter out;


        try {
            serverSocket = new ServerSocket(1234);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientName;
                out = new PrintWriter(clientSocket.getOutputStream());
                out.println("Tell us your Name : ");
                out.flush();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientName = in.readLine();
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientName);
                clients.add(clientHandler);
                executor.execute(new Sender());
                executor.execute(new Receive(clientHandler));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
