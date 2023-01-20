import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientReceive implements Runnable{
    private final Socket clientSocket;
    private String msg;
    private BufferedReader in;
    private PrintWriter out;

    public ClientReceive(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            msg = in.readLine();

            while(!Objects.isNull(msg)){
                if(msg.contains("Tell us your Name :")){
                    System.out.print("Tell us your Name : ");
                    msg = in.readLine();
                    continue;
                }
                if(msg.contains("/listCode")){
                    String[] msgSplit = msg.split(" ");
                    for(int i = 1; i < msgSplit.length; i++){
                        System.out.print(msgSplit[i] + " ");
                    }
                    System.out.println();
                    msg = in.readLine();
                    continue;
                }
                System.out.println("Server : " + msg);
                msg = in.readLine();
            }

            System.out.println("Server out of service");
            out.close();
            clientSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
