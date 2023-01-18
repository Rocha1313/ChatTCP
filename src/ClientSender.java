import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSender implements Runnable{
    private final Socket clientSocket;
    private String msg;
    private PrintWriter out;
    private final Scanner sc = new Scanner(System.in);

    public ClientSender(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true){
            msg = sc.nextLine();
            out.println(msg);
            out.flush();

            if(msg.equals("/quit")){
                break;
            }
        }
    }
}
