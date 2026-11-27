import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCPServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket socket = new ServerSocket(50000);
        Socket connection = socket.accept();
        // ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
        // output.flush();
        // ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

        // System.out.println(input.readObject());

        ClientConnection connection1 = new ClientConnection(connection);

        ExecutorService e = Executors.newCachedThreadPool();
        e.execute(connection1);

        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        // System.out.println("Attempting Shutdown.");
        // e.shutdownNow();
        // System.out.println("Shutdown.");
        // e.close();
        // System.out.println("Closed.");
        connection1.close();
    }

    private static class ClientConnection implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;
        private Socket connection;

        // Keeps looking for more messages until stopped by main()
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(input.readObject());
                } catch (Exception e) {
                    break;
                }
            }
        }

        public ClientConnection(Socket connection) throws IOException {
            this.connection = connection;
            this.output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            this.input = new ObjectInputStream(connection.getInputStream());
        }

        public void close() throws IOException {
            output.writeObject("CLOSE");
            connection.close();
        }
    }
}