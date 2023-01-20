import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Receive implements Runnable{
    private final ClientHandler client;
    private String msg;
    private BufferedReader in;
    private PrintWriter out;

    public Receive(ClientHandler client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getClientSocket().getOutputStream());
            in = new BufferedReader(new InputStreamReader(client.getClientSocket().getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            msg = in.readLine();

            while(!msg.contains("/quit")){
                if(msg.contains("/help")){
                    PrintWriter out = new PrintWriter(client.getClientSocket().getOutputStream());
                    out.println("""
                            /list    -> To get the list of connected clients
                            /help    -> Get all the commands
                            /quit    -> Disconnect
                            /whisper -> Send a message to a specific client""");
                    out.flush();
                    msg = in.readLine();
                    continue;
                }

                if(msg.contains("/list")){
                    PrintWriter out = new PrintWriter(client.getClientSocket().getOutputStream());
                    for(ClientHandler client : Server.clients){
                        out.println("/listCode " + client.getClientName());
                        out.flush();
                    }
                    msg = in.readLine();
                    continue;
                }

                System.out.println(client.getClientName() + " : " + msg);
                msg = in.readLine();
            }

            Server.clients.remove(client);

            System.out.println("Client disconnected");


            out.close();
            client.getClientSocket().close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
