import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {

    private boolean open = false;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket socket = new ServerSocket(50000);
        Socket connection = socket.accept();
        ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

        System.out.println(input.readObject());

        connection.close();
    }
}