package blackjack.model;

import java.util.List;

/**
 * Created by igor on 17.03.18.
 */
public abstract class AbstractPlayer {
    protected final String name;
    protected final Hand hand;
    protected final PlayerType type;
    protected PlayerStatus status;

    public AbstractPlayer(final String name, final PlayerType type) {
        this.name = name;
        this.hand = new Hand();
        this.type = type;
        this.status = PlayerStatus.HITTING;
    }

    public String getName() {
        return this.name;
    }

    public PlayerType getType() {
        return this.type;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public void addCard(final Card card) {
        this.hand.addCard(card);
    }

    public void clearCards() {
        this.hand.clear();
    }

    public List<Card> getCards() {
        return this.hand.getCards();
    }

    public int getHandTotal() {
        return this.hand.evaluate();
    }

    public boolean hasBlackJack() {
        return this.hand.hasBlackJack();
    }

    public boolean hasBlackJackWithSameColorCards() {
        return this.hand.hasBlackJackWithSameSuitCards();
    }

    public boolean hasTwoAces() {
        return this.hand.hasTwoAces();
    }

    public boolean hasThreeSevens() {
        return this.hand.hasThreeSevens();
    }

    public boolean isBusted() {
        return this.hand.isBusted();
    }

    public abstract boolean isHitting();
}
