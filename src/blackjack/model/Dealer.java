package blackjack.model;

/**
 * Created by igor on 17.03.18.
 */
public class Dealer extends AbstractPlayer {
    private static final int HITTING_LIMIT = 16;
    private double casinoMoney;

    public Dealer(final String name, final PlayerType type, final double casinoStartPot) {
        super(name, type);
        this.casinoMoney = casinoStartPot;
    }

    public void setCasinoBalance(final double casinoMoney) {
        this.casinoMoney = casinoMoney;
    }

    public double getCasinoMoney() {
        return this.casinoMoney;
    }

    public void flipFirstCard() {
        this.hand.flipFirstCard();
    }

    @Override
    public boolean isHitting() {
        return this.hand.evaluate() < HITTING_LIMIT;
    }

    public void takeMoneyForWinner(final double prizeMoney) {
        this.casinoMoney -= prizeMoney;
    }

    public void takeLosersBet(final double bet) {
        this.casinoMoney += bet;
    }

    @Override
    public String toString() {
        return String.format("%s %s\nHand total: %s\nCasino balance:%8.2f$\n%s", this.name, this.hand.toString(),
                this.getCards().get(0).isCardFlipped() ? "" : String.valueOf(this.getHandTotal()),
                this.casinoMoney, this.status.toString());
    }


}
