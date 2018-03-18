package blackjack.model;

/**
 * Created by igor on 18.03.18.
 */
public enum PlayerStatus {
    HITTING("Hitting"),
    STAND("Stand"),
    WON("Won"),
    LOST("Lost"),
    PUSH("Push");

    PlayerStatus(final String text) {
        this.text = text;
    }

    private String text;
}
