import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Opening");
        DatagramSocket socket = new DatagramSocket(50000);
    }
}