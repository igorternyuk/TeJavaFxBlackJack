package blackjack.tests;

import blackjack.model.Card;
import blackjack.model.Hand;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static blackjack.model.Card.Suit;
import static blackjack.model.Card.Rank;
import static org.junit.Assert.assertTrue;

/**
 * Created by igor on 17.03.18.
 */
public class HandTest {
    private Hand hand1, hand2;

    @Before
    public void setUp() {
        hand1 = new Hand();
        hand2 = new Hand();
    }

    @Test
    public void testHandEvaluation() {
        hand1.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand1.addCard(new Card(Suit.HEARTS, Rank.TEN));
        assertEquals(21, hand1.evaluate());
    }

    @Test
    public void testLowAce() {
        hand1.addCard(new Card(Suit.SPADES, Rank.EIGHT));
        hand1.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand1.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        hand2.addCard(new Card(Suit.SPADES, Rank.THREE));
        hand2.addCard(new Card(Suit.HEARTS, Rank.THREE));
        hand2.addCard(new Card(Suit.DIAMONDS, Rank.THREE));
        hand2.addCard(new Card(Suit.CLUBS, Rank.THREE));
        hand2.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        assertEquals(19, hand1.evaluate());
        assertEquals(13, hand2.evaluate());
    }

    @Test
    public void testTwoAces() {
        hand1.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand1.addCard(new Card(Suit.CLUBS, Rank.ACE));
        assertEquals(Hand.TWO_ACES, hand1.evaluate());
        assertTrue(hand1.hasTwoAces());
    }

    @Test
    public void testThreeSevens() {
        hand1.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        hand1.addCard(new Card(Suit.CLUBS, Rank.SEVEN));
        hand1.addCard(new Card(Suit.SPADES, Rank.SEVEN));
        hand1.evaluate();
        assertEquals(21, hand1.evaluate());
        assertTrue(hand1.hasThreeSevens());
    }

    @Test
    public void testBlackJack() {
        hand1.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand1.addCard(new Card(Suit.CLUBS, Rank.TEN));
        hand2.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        hand2.addCard(new Card(Suit.SPADES, Rank.JACK));
        assertEquals(21, hand1.evaluate());
        assertTrue(hand1.hasBlackJack());
        assertEquals(21, hand2.evaluate());
        assertTrue(hand2.hasBlackJack());
    }

    @Test
    public void testBlackJackWithSameSuitCards() {
        hand1.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand1.addCard(new Card(Suit.HEARTS, Rank.TEN));
        hand2.addCard(new Card(Suit.DIAMONDS, Rank.ACE));
        hand2.addCard(new Card(Suit.DIAMONDS, Rank.JACK));
        assertEquals(21, hand1.evaluate());
        assertTrue(hand1.hasBlackJackWithSameSuitCards());
        assertEquals(21, hand2.evaluate());
        assertTrue(hand2.hasBlackJackWithSameSuitCards());
    }

    @Test
    public void testBusted() {
        hand1.addCard(new Card(Suit.HEARTS, Rank.KING));
        hand1.addCard(new Card(Suit.CLUBS, Rank.KING));
        hand1.addCard(new Card(Suit.DIAMONDS, Rank.KING));
        assertTrue(hand1.isBusted());
        hand2.addCard(new Card(Suit.HEARTS, Rank.QUEEN));
        hand2.addCard(new Card(Suit.CLUBS, Rank.JACK));
        assertFalse(hand2.isBusted());
    }
}
