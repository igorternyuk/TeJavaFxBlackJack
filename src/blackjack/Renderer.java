package blackjack;

import blackjack.model.Card;
import blackjack.model.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

/**
 * Created by igor on 20.03.18.
 */
public class Renderer {
    private static ResourceManager rm = ResourceManager.getInstance();
    private static final int[] HANDS_X = {400, 10, 10, 800, 800, 400};
    private static final int[] HANDS_Y = {310, 20, 310, 20, 310, 20};
    private static final Font LARGE_FONT = new Font("Times", 48);
    private static final Font SMALL_FONT = new Font("Arial", 22);
    private static final Color TABLE_COLOR = Color.rgb(29, 190, 100);
    private static final int GAME_TITLE_X = 350;
    private static final int GAME_TITLE_Y = 275;
    private static final int TABLE_RECT_ARC_SIZE = 50;

    static void renderTable(final GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(TABLE_COLOR);
        gc.fillRoundRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight(),
                TABLE_RECT_ARC_SIZE, TABLE_RECT_ARC_SIZE);
    }

    static void renderRoundNumber(final GraphicsContext gc, final Game game) {
        gc.setFill(Color.YELLOW);
        gc.setFont(LARGE_FONT);
        gc.fillText("Black Jack: Round " + game.getRoundNumber(), GAME_TITLE_X, GAME_TITLE_Y);
    }

    private static void renderCard(final GraphicsContext gc, final Card card, final int x, final int y) {
        final Image cardImage = rm.getCardImage(card);
        gc.drawImage(cardImage, x, y);
    }

    private static void renderCardBack(final GraphicsContext gc, final int x, final int y) {
        final Image cardBackImage = rm.getCardBack();
        gc.drawImage(cardBackImage, x, y);
    }


    static void renderPlayersCards(final GraphicsContext gc, final Game game) {
        for (int i = 0; i < Game.NUMBER_OF_PLAYERS; ++i) {
            final List<Card> playerCards = game.getPlayers().get(i).getCards();
            for (int j = 0; j < playerCards.size(); ++j) {
                renderCard(gc, playerCards.get(j), HANDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2), HANDS_Y[i]);
            }
            final String description = game.getPlayers().get(i).toString();
            gc.setFill(Color.BLUE);
            gc.setFont(SMALL_FONT);
            gc.fillText(description, HANDS_X[i], HANDS_Y[i] + ResourceManager.CARD_HEIGHT + 20);
        }
    }

    static void renderDealersCards(final GraphicsContext gc, final Game game) {
        List<Card> dealersCards = game.getDealer().getCards();
        for (int j = 0; j < dealersCards.size(); ++j) {
            if (dealersCards.get(j).isCardFlipped()) {
                renderCardBack(gc, HANDS_X[5] + j * (ResourceManager.CARD_WIDTH + 2), HANDS_Y[5]);
            } else {
                renderCard(gc, dealersCards.get(j), HANDS_X[5] + j * (ResourceManager.CARD_WIDTH + 2), HANDS_Y[5]);
            }
        }
        final String description = game.getDealer().toString();
        gc.setFill(Color.GREEN.darker().darker());
        gc.setFont(SMALL_FONT);
        gc.fillText(description, HANDS_X[5], HANDS_Y[5] + ResourceManager.CARD_HEIGHT + 20);
    }
}
