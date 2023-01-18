import java.net.Socket;

public class ClientHandler{
    private final Socket clientSocket;
    private final String clientName;

    public ClientHandler(Socket clientSocket, String clientName) {
        this.clientSocket = clientSocket;
        this.clientName = clientName;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getClientName() {
        return clientName;
    }
}
