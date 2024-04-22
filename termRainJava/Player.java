
public class Player {
    private int id;
    private String name;
    private Pack hand;
    private boolean won;
    private Server.ClientHandler client;
    private boolean leader;

    public Player(int id, String name, Server.ClientHandler client) {
        this.id = id;
        leader = false;
        this.client = client;
        this.name = name;
        this.hand = new Pack();
        won = false;
    }

    public int getClientID() {
        return client.socket.getPort();
    }

    public boolean isLeader() {
        return leader;
    }

    public void makeLeader() {
        leader = true;
    }

    public String getEncodedCards() {

        return hand.getEncodedCards();
    }

    public void callClient(String message) {
        client.sendMessage(message);
    }

    public int getId() {
        return id;

    }

    public Server.ClientHandler getClient() {
        return client;
    }

    public void addCard(Card newCard) {
        hand.addCard(newCard);
    }

    public Card dropCard(String id) {

        return hand.dropCard(Integer.parseInt(id));
    }

    public boolean getIfWon() {
        return won;
    }

    public String getName() {
        this.hand.printPack();
        return name;

    }

}
