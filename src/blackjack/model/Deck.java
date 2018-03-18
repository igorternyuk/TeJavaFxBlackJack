package blackjack.model;


import java.util.*;

/**
 * Created by igor on 16.03.18.
 */
public class Deck {
    private static final int MAX_NUMBER_OF_CARDS = 52;
    private List<Card> cards = new ArrayList<>(MAX_NUMBER_OF_CARDS);

    public Deck() {
        populate();
        shuffle();
    }

    public void populate() {
        this.cards.clear();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                this.cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this.cards, new Random());
    }

    public void clear() {
        this.cards.clear();
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(this.cards);
    }

    public int cardsRemains() {
        return this.cards.size();
    }

    public void deal(final AbstractPlayer player) {
        player.addCard(this.cards.get(this.cards.size() - 1));
        this.cards.remove(this.cards.size() - 1);
        //System.out.println("Cards in deck: " + this.cards.size());
    }
}
