package blackjack.model;

import java.util.Objects;

/**
 * Created by igor on 16.03.18.
 */
public class Card {
    public enum Suit {
        CLUBS("C"), HEARTS("H"), SPADES("S"), DIAMONDS("D");

        private String text;

        Suit(final String text) {
            this.text = text;
        }

        public String getText(){
            return this.text;
        }
    };

    public enum Rank {
        TWO(2, false), THREE(3, false), FOUR(4, false), FIVE(5, false), SIX(6, false), SEVEN(7, false), EIGHT(8, false),
        NINE(9, false), TEN(10, false), JACK(10, true), QUEEN(10, true), KING(10, true), ACE(11, true);

        private int value;
        private boolean isfigure;

        Rank(final int value, final boolean isfigure) {
            this.value = value;
            this.isfigure = isfigure;
        }

        public int getValue() {
            return value;
        }

        public boolean isFigure(){
            return this.isfigure;
        }
    }

    public static final int BIG_ACE_VALUE = 11;
    public static final int LOW_ACE_VALUE = 1;
    private final Suit suit;
    private final Rank rank;
    private boolean isFaceUp = true;

    public Card(final Suit suit, final Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue(){
        return this.rank.getValue();
    }

    public boolean isAce() {
        return this.rank.equals(Rank.ACE);
    }

    public boolean isTen() {
        return this.rank.equals(Rank.TEN);
    }

    public boolean isSeven() {
        return this.rank.equals(Rank.SEVEN);
    }

    public boolean isFigure() {
        return this.rank.isFigure();
    }

    public void flip() {
        this.isFaceUp = !this.isFaceUp;
    }

    public boolean isCardFlipped() {
        return !this.isFaceUp;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Card)) return false;
        final Card otherCard = (Card) other;
        return Objects.equals(this.rank, otherCard.getRank())
                && Objects.equals(this.suit, otherCard.getSuit())
                && !this.isFaceUp == otherCard.isCardFlipped();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = getSuit().hashCode();
        result = prime * result + getRank().hashCode();
        result = prime * result + (isFaceUp ? 1 : 0);
        return result;
    }

    @Override
    public String toString(){
        if(this.rank.isFigure()){
            switch (this.rank){
                case JACK:
                    return "J" + this.suit.getText();
                case QUEEN:
                    return "Q" + this.suit.getText();
                case KING:
                    return "K" + this.suit.getText();
                default:
                    return "A" + this.suit.getText();
            }
        } else {
            return  String.valueOf(this.getValue()) + this.suit.getText();
        }
    }
}
