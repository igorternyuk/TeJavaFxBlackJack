package blackjack.tests;

import blackjack.model.Card;
import blackjack.model.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by igor on 17.03.18.
 */
public class DeckTest {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testDeckSize() {
        assertEquals(52, deck.cardsRemains());
    }

    @Test
    public void testDeckContent() {
        for (final Card.Rank rank : Card.Rank.values()) {
            assertEquals(4, deck.getCards().stream().filter(card -> card.getRank().equals(rank)).count());
        }
        for (final Card.Suit suit : Card.Suit.values()) {
            assertEquals(13, deck.getCards().stream().filter(card -> card.getSuit().equals(suit)).count());
        }
    }
}
