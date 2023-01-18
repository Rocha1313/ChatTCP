import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Socket clientSocket;
        System.out.println();

        try {
            clientSocket = new Socket("127.0.0.1", 1234);

            Thread sender = new Thread(new ClientSender(clientSocket));
            sender.start();

            Thread receive = new Thread(new ClientReceive(clientSocket));
            receive.start();

            while(true){
                if(clientSocket.isClosed()){
                    System.out.println("Exiting application.");
                    System.exit(0);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
