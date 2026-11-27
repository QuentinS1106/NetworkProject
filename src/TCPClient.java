import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPClient {

    private static boolean open = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket connection = new Socket("127.0.0.1", 50000);
        ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

        ClientReader reader = new ClientReader(input);
        ClientWriter writer = new ClientWriter(output);

        ExecutorService client = Executors.newCachedThreadPool();
        client.execute(reader);
        client.execute(writer);
        client.shutdown();
    }

    private static class ClientReader implements Runnable {

        private ObjectInputStream input;

        @Override
        public void run() {
            while (open) {
                try {
                    String message = (String) input.readObject();
                    if (message.equals("CLOSE")) {
                        System.out.println("The chatroom has closed.");
                        open = false;
                    } else {
                        System.out.println(message);
                    }
                } catch (Exception e) {}
            }
        }

        public ClientReader(ObjectInputStream input) {
            this.input = input;
        }
    }

    private static class ClientWriter implements Runnable {

        private ObjectOutputStream output;

        @Override
        public void run() {
            Scanner scan = new Scanner(System.in);

            System.out.println("Connected to the chatroom!");

            System.out.println("Please input a name.");
            String name = scan.nextLine();

            System.out.println("Type exit to leave the chatroom.");

            while (open) {
                try {
                    String msg = scan.nextLine();
                    if (open) {
                        if (msg.equals("exit")) {
                            output.writeObject("CLOSE");
                            open = false;
                        } else {
                            output.writeObject(name + ">>>\t" + msg);
                            output.flush();
                        }
                    }
                } catch (Exception e) {}
            }
        }

        public ClientWriter(ObjectOutputStream output) {
            this.output = output;
        }
    }
}