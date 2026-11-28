import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TCPServer {

    private static boolean open = true;
    private static ExecutorService clients;
    private static ArrayList<ClientConnection> connections;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket socket = new ServerSocket(50000);
        clients = Executors.newCachedThreadPool();
        connections = new ArrayList<>();

        // Later, we will be accepting connections, this timeout allows the server to eventually break out of the cycle
        // when it needs to close
        socket.setSoTimeout(5000);

        clients.execute(new ExitTask());

        while (open) {
            try {
                Socket connection = socket.accept();
                ClientConnection client = new ClientConnection(connection);
                clients.execute(client);
                connections.add(client);
            } catch (SocketTimeoutException e) {
                System.out.println("Yeah it's working");
            }
        }

        closeAll();
        clients.shutdown();

        // ClientConnection connection1 = new ClientConnection(connection);

        // clients.execute(connection1);
        // clients.shutdown();
    }

    public static void sendToAll(String message) throws IOException {
        for (ClientConnection client : connections) {
            if (client.isOpen()) {
                client.send(message);
            }
        }
    }

    public static void closeAll() throws IOException {
        for (ClientConnection client : connections) {
            if (client.isOpen()) {
                client.close();
            }
        }
    }

    private static class ClientConnection implements Runnable {

        private final ObjectOutputStream output;
        private final ObjectInputStream input;
        private final Socket connection;
        private boolean connectionOpen;

        // Keeps looking for more messages until stopped by main()
        @Override
        public void run() {
            while (true) {
                try {
                    String message = (String) input.readObject();
                    if (message.equals("CLOSE")) {
                        connectionOpen = false;
                        break;
                    } else {
                        sendToAll(message);
                    }
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
            connectionOpen = true;
        }

        public void close() throws IOException {
            output.writeObject("CLOSE");
            connection.close();
        }
        
        public void send(String message) throws IOException {
            output.writeObject(message);
        }

        // This is so that the server knows which connections still need to be closed
        public boolean isOpen() {
            return connectionOpen;
        }
    }

    private static class ExitTask implements Runnable {
        @Override
        public void run() {
            Scanner scan = new Scanner(System.in);
            System.out.println("Type \"CLOSE\" to close the chatroom.");
            while (open) {
                String message = scan.nextLine();
                if (message.equals("CLOSE")) {
                    System.out.println("CLOSING...");
                    open = false;
                }
            }
        }
    }
}