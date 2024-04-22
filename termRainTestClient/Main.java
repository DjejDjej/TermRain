
public class Main {
    public static void main(String[] args) {
        TCPClient client = new TCPClient("localhost", 1312);

        try {

            client.sendMessage("initPlayer,pepix," + client.socket.getLocalPort());
            Thread.sleep(5000);

            client.sendMessage("startgame," + client.socket.getLocalPort());
            client.sendMessage("addcard," + client.socket.getLocalPort());
            Thread.sleep(5000);
            client.sendMessage("drawcard," + client.socket.getLocalPort() + ",0");
        } catch (Exception e) {
            System.out.println("XD");
        }

        // client.sendMessage("addcard," + client.socket.getLocalPort());

        // Read from console and send messages to the server
        while (true) {
            try {

            } catch (Exception e) {

                System.out.println(e);
                break;
            }

        }
    }
}
