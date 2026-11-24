import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Opening");
        ServerSocket socket = new ServerSocket(50000);
        Socket connection = socket.accept();
        System.out.println("Connection Accepted.");
        ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        System.out.println("Got Output");
        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Got Input");
        
        
        // Shit during the connection
        output.writeObject("TEST DATA");
        output.flush();

        connection.close();
    }
}