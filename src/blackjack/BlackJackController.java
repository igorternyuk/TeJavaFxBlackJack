package blackjack;

import blackjack.model.Card;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class BlackJackController implements Initializable {
    private static final String EXIT_DIALOG_WINDOW_MESSAGE = "Do you really want to exit?";
    private static final String EXIT_DIALOG_WINDOW_TITLE = "Confirm exit, please";
    private static final Color TABLE_COLOR = Color.rgb(29, 241, 147);
    private static ResourceManager rm = ResourceManager.getInstance();

    @FXML
    private Canvas canvas;

    @FXML
    private Button btnNewGame;

    private GraphicsContext gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = canvas.getGraphicsContext2D();
        render();
    }

    public void onButtonNewGameClicked(ActionEvent event) {
        render();
    }

    public void onButtonHitClicked(ActionEvent event) {

    }

    public void onButtonStandClicked(ActionEvent event) {

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
        gc.fillRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 50, 50);
    }

    private void render() {
        renderTable();
        Card queenOfDiamonds = new Card(Card.Suit.SPADES, Card.Rank.KING);
        gc.drawImage(rm.getCardImage(queenOfDiamonds), 50, 50);
        gc.drawImage(rm.getCardBack(), 130, 50);
    }
}
