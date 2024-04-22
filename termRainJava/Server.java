import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private List<String> messages;

    public Server(int port) {
        this.messages = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }

    public List<ClientHandler> getClients() {

        if (clients.size() > 0) {
            return clients;

        }
        return new ArrayList<>();
    }

    public void startServer() {
        Thread serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected: " + socket);

                    ClientHandler clientHandler = new ClientHandler(socket);
                    clients.add(clientHandler);

                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    System.out.println("Error accepting client connection: " + e.getMessage());
                }
            }
        });

        serverThread.start();
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);

        }
    }

    public void sendMessage(String message, ClientHandler client) {
        client.sendMessage(message);

    }

    public List<String> getMessages() {
        if (messages.size() != 0) {
            return messages;

        }
        return new ArrayList<>();
    }

    public void clearMessages() {
        messages.clear();
    }

    public void clearMessage(int index) {
        messages.remove(index);
    }

    public ClientHandler getClientBySocket(String socket) {
        for (ClientHandler c : clients) {

            if (Integer.toString(c.socket.getPort()).equals(socket)) {
                return c;
            }

        }
        System.out.println("RETURNED NULL?");
        return null;
    }

    public class ClientHandler implements Runnable {
        public Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            } catch (IOException e) {
                System.out.println("Error creating client handler: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    // System.out.println("Received from client: " + message);
                    messages.add(message);
                }
            } catch (IOException e) {
                System.out.println("Error handling client connection: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                    clients.remove(this);
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        public void sendMessage(String message) {
            writer.println(message);
        }
    }
}