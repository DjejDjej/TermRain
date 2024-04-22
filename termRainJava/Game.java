
import java.util.ArrayList;
import java.util.List;

public class Game {

    // Game config
    public int startingCards;
    public int startingPlayers;

    // Server config
    private Server server;

    private List<Player> players;
    private Pack inBundle;
    private Pack onTable;
    private int round;
    private int playerCount = 0;
    private boolean started;
    private int cardMltp = 1;

    public Game(int port) {
        playerCount = 0;
        startingCards = 4;
        startingPlayers = 2;
        inBundle = new Pack();
        round = 0;
        started = false;
        onTable = new Pack();
        server = new Server(port);
        server.startServer();
        players = new ArrayList<>();
        inBundle.fillPack();
    }

    public String[] decryptMessage(String message) {

        return message.split(",");

    }

    public Player getPlayerBySocket(String id) {

        for (Player player : players) {
            if (player.getClientID() == Integer.parseInt(id)) {
                return player;
            }

        }
        return null;
    }

    public void initPlayers() {

        try {

            List<String> messQ = server.getMessages();
            for (int i = 0; i < messQ.size(); i++) {

                String[] decrpMess = decryptMessage(messQ.get(i));
                if (decrpMess[0].equals("initPlayer")) {
                    players.add(new Player(playerCount, decrpMess[1], server.getClientBySocket(decrpMess[2])));
                    if (playerCount == 0) {
                        players.get(0).makeLeader();
                    }
                    playerCount++;
                    server.clearMessage(i);
                    System.out.println("new player created");
                }
                if (decrpMess[0].equals("startgame")) {
                    if (getPlayerBySocket(decrpMess[1]).isLeader()) {
                        started = true;
                        System.out.println("started");
                        server.clearMessage(i);
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e + "WTF");
        }

    }

    public boolean canBeDropped() {
        return true;
    }

    public void gameMechanicsDecoder() {
        try {
            Thread.sleep(500);

            List<String> messQ = server.getMessages();
            Player p = null;
            for (int i = 0; i < messQ.size(); i++) {

                String[] decrpMess = decryptMessage(messQ.get(i));
                p = getPlayerBySocket(decrpMess[1]);
                switch (decrpMess[0]) {

                    case "addcard":
                        for (int m = 0; m < cardMltp; m++) {
                            p.addCard(inBundle.dropLastCard());
                        }
                        cardMltp = 1;
                        p.callClient("hand," + p.getEncodedCards());
                        System.out.println("hand----");
                        p.getName();
                        System.out.println("hand----");

                        break;

                    case "drawcard":
                        if (canBeDropped()) {
                            onTable.addCard(p.dropCard(decrpMess[2]));
                            System.out.println("table----");
                            server.broadcastMessage("table," + onTable.getEncodedLastCard());
                            onTable.printPack();
                            System.out.println("table----");

                        }
                }

                server.clearMessage(i);

            }

        } catch (Exception e) {

            System.out.println("mecha " + e);
        }

    }

    public void startGame() {

        try {
            while (true) {
                if (playerCount < startingPlayers) {
                    initPlayers();
                } else {
                    gameMechanicsDecoder();

                }
                Thread.sleep(500);

            }

        } catch (Exception e) {
            System.out.println("CRASH" + e);

        }

    }

}