package blackjack.model;

/**
 * Created by igor on 17.03.18.
 */
public class Player extends AbstractPlayer {
    private double money, bet = 0;

    public Player(final String name, final PlayerType playerType) {
        super(name, playerType);
    }

    @Override
    public boolean isHitting() {
        return this.hand.evaluate() <= Hand.LIMIT;
    }

    //Methods for money operations
    public double getMoneyBalance() {
        return this.money;
    }

    public boolean isNegativeBalance() {
        return this.money < 0;
    }

    public boolean isZeroBalance() {
        return this.money == 0;
    }

    public double getBet() {
        return this.bet;
    }

    public void stake() {
        this.money -= this.bet;
    }

    public void award(final double coefficient) {
        this.money += coefficient * this.bet;
    }

    public void addBonus(final double bonus) {
        this.money += bonus;
    }

    //Returns player's bet in the case of push
    public void returnBet() {
        this.money += this.bet;
    }

    @Override
    public String toString() {
        return String.format("%s %s\n Money:%8.2f$", this.name, this.hand.toString(), this.money);
    }
}
