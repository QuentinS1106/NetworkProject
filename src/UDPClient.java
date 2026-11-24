import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket(51000);
        String message = "message here";
        byte[] data = message.getBytes();
        System.out.println(InetAddress.getLocalHost());
        DatagramPacket p = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 50000);
        socket.send(p);
    }
}