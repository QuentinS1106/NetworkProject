import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        Socket connection = new Socket("127.0.0.1", 50000);
        ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

        System.out.println("Connected to the chatroom!");

        System.out.println("Please input a name.");
        String name = scan.nextLine();

        output.writeObject(name + ">>>\t" + scan.nextLine());
        output.flush();
    }
}