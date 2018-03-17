package blackjack.tests;

import blackjack.ResourceManager;
import blackjack.model.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * Created by igor on 17.03.18.
 */
public class ResourceManagerTest {
    private ResourceManager rm;

    @Before
    public void setUp() {
        rm = ResourceManager.getInstance();
    }
    //private static final ResourceManager rm = ResourceManager.getInstance();


    @Test
    public void testCardCoordinates() {
        Card aceOfHearts = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        Card jackOfSpades = new Card(Card.Suit.SPADES, Card.Rank.JACK);
        assertEquals(0, rm.getPositionXOnSpriteByCard(aceOfHearts));
        assertEquals(10, rm.getPositionXOnSpriteByCard(jackOfSpades));
    }

    @After
    public void dispose() {
        rm.dispose();
    }
}
