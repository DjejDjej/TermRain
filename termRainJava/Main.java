import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Player> players = new ArrayList<Player>();
        // players.add(new Player(0, "chlap"));
        Game g = new Game(1312);
        g.startGame();
    }

}
