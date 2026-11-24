import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Opening");
        DatagramSocket socket = new DatagramSocket(50000);
        // UDP requires declaring a place to hold the data
        byte[] data = new byte[100];
        DatagramPacket p = new DatagramPacket(data, data.length);
        socket.receive(p);
        // This is neccessary to convert the data in the packet to readable things
        String s = new String(p.getData(), 0, p.getLength());
        System.out.println(s);
    }
}