import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket connection = new Socket("192.168.0.200", 50000);
        System.out.println("Connected");
        ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        System.out.println("Got output");
        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Got input");
        
        System.out.println(input.readObject());
    }
}