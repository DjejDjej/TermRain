import java.io.*;
import java.net.*;

public class TCPClient {
    public Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public TCPClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + socket);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // Start a thread to listen for messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Server: " + message);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading server message: " + e.getMessage());
                }
            }).start();

        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing client socket: " + e.getMessage());
        }
    }

}