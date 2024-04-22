import java.util.LinkedList;
import java.util.List;

public class Pack {
    private List<Card> cards = new LinkedList<>();

    public Pack() {

    }

    public void fillPack() {
        Rank[] ranks = Rank.values();
        Suit[] suits = Suit.values();
        for (int si = 0; si < suits.length; si++) {
            for (int ri = 0; ri < ranks.length; ri++) {

                cards.add(new Card(suits[si], ranks[ri]));

            }

        }

    }

    public Card dropCard(int id) {
        if (id < cards.size() && id >= 0) {
            Card c = cards.get(id);
            cards.remove(id);
            return c;
        } else {
            return null;
        }

    }

    public Card dropLastCard() {
        if (cards.size() != 0) {
            Card c = cards.getLast();
            cards.removeLast();
            return c;
        }
        return null;

    }

    public void addCard(Card newCard) {

        cards.add(newCard);

    }

    public int getCardsize() {
        return cards.size();
    }

    public void printPack() {
        for (Card cards : cards) {
            System.out.println(cards.getCardString());
        }
    }

    public String getEncodedLastCard() {

        if (cards.size() != 0) {
            return cards.getLast().getCardString();
        }
        return null;

    }

    public String getEncodedCards() {
        String encCards = "";
        for (Card card : cards) {
            encCards += card.getCardString() + ";";
        }
        return encCards;

    }

}
