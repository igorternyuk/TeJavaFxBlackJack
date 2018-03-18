package blackjack;

import blackjack.model.Card;
import blackjack.model.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BlackJackController implements Initializable {
    private static final String EXIT_DIALOG_WINDOW_MESSAGE = "Do you really want to exit?";
    private static final String EXIT_DIALOG_WINDOW_TITLE = "Confirm exit, please";
    private static final Color TABLE_COLOR = Color.rgb(29, 190, 100);
    public static final int GAME_TITLE_X = 350;
    public static final int GAME_TITLE_Y = 275;
    public static final int TABLE_RECT_ARC_WIDTH = 50;
    private static ResourceManager rm = ResourceManager.getInstance();
    private final Game game = new Game();
    private static final int[] HANDS_X = {400, 10, 10, 800, 800, 400};
    private static final int[] HANDS_Y = {310, 20, 310, 20, 310, 20};
    private GraphicsContext gc;
    private static final Font LARGE_FONT = new Font("Times", 48);
    private static final Font SMALL_FONT = new Font("Arial", 22);
    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        renderPhase();
    }

    public void onButtonNewGameClicked(ActionEvent event) {
        this.game.startNewGame();
        renderPhase();
    }

    public void onButtonNewRoundClicked(ActionEvent event) {
        this.game.startNewRound();
        renderPhase();
    }

    public void onButtonHitClicked(ActionEvent event) {
        this.game.hit();
        renderPhase();
    }

    public void onButtonStandClicked(ActionEvent event) {
        this.game.stand();
        renderPhase();
    }

    public void onButtonExitClicked(ActionEvent event) {
        final Alert alert = new Alert(Alert.AlertType.WARNING, EXIT_DIALOG_WINDOW_MESSAGE, ButtonType.YES, ButtonType.NO);
        alert.setTitle(EXIT_DIALOG_WINDOW_TITLE);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
            System.exit(0);
        } else if (alert.getResult() == ButtonType.NO) {
            alert.close();
        }
    }

    private void renderTable() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(TABLE_COLOR);
        gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), TABLE_RECT_ARC_WIDTH, TABLE_RECT_ARC_WIDTH);
    }

    private void renderRoundNumber() {
        gc.setFill(Color.YELLOW);
        gc.setFont(LARGE_FONT);
        gc.fillText("Black Jack: Round " + this.game.getRoundNumber(), GAME_TITLE_X, GAME_TITLE_Y);
    }

    private void renderCard(final Card card, final int x, final int y) {
        final Image cardImage = rm.getCardImage(card);
        this.gc.drawImage(cardImage, x, y);
    }

    private void renderCardBack(final int x, final int y) {
        final Image cardBackImage = rm.getCardBack();
        this.gc.drawImage(cardBackImage, x, y);
    }


    private void renderPlayersCards() {
        for (int i = 0; i < Game.NUMBER_OF_PLAYERS; ++i) {
            List<Card> playerCards = this.game.getPlayers().get(i).getCards();
            for (int j = 0; j < playerCards.size(); ++j) {
                renderCard(playerCards.get(j), HANDS_X[i] + j * (ResourceManager.CARD_WIDTH + 2), HANDS_Y[i]);
            }
            final String description = this.game.getPlayers().get(i).toString();
            gc.setFill(Color.BLUE);
            gc.setFont(SMALL_FONT);
            gc.fillText(description, HANDS_X[i], HANDS_Y[i] + ResourceManager.CARD_HEIGHT + 20);
        }
    }

    private void renderDealersCards() {
        List<Card> dealersCards = this.game.getDealer().getCards();
        for (int j = 0; j < dealersCards.size(); ++j) {
            if (dealersCards.get(j).isCardFlipped()) {
                renderCardBack(HANDS_X[5] + j * (ResourceManager.CARD_WIDTH + 2), HANDS_Y[5]);
            } else {
                renderCard(dealersCards.get(j), HANDS_X[5] + j * (ResourceManager.CARD_WIDTH + 2), HANDS_Y[5]);
            }
        }
        final String description = this.game.getDealer().toString();
        gc.setFill(Color.GREEN.darker().darker());
        gc.setFont(SMALL_FONT);
        gc.fillText(description, HANDS_X[5], HANDS_Y[5] + ResourceManager.CARD_HEIGHT + 20);
    }

    private void renderPhase() {
        renderTable();
        renderPlayersCards();
        renderDealersCards();
        if (this.game.getRoundNumber() > 0) {
            renderRoundNumber();
        }
    }
}
