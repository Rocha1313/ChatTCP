import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable {
    private String msg;
    private final Scanner sc = new Scanner(System.in);

    public Sender() {
    }

    @Override
    public void run() {
        while (true) {
            msg = sc.nextLine();
            for (ClientHandler client : Server.clients) {
                try {
                    PrintWriter out = new PrintWriter(client.getClientSocket().getOutputStream());
                    out.println(msg);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
