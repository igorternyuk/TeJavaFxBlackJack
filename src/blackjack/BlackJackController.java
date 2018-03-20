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
    private final Game game = new Game();
    private GraphicsContext gc;

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

    private void renderPhase() {
        Renderer.renderTable(this.gc);
        Renderer.renderPlayersCards(this.gc, this.game);
        Renderer.renderDealersCards(this.gc, this.game);
        if (this.game.getRoundNumber() > 0) {
            Renderer.renderRoundNumber(this.gc, this.game);
        }
    }
}
