package blackjack.model;

/**
 * Created by igor on 16.03.18.
 */
public class Card {
    public enum Suit {
        HEARTS("H"), CLUBS("C"), DIAMONDS("D"), SPADES("S");

        private String text;

        private Suit(final String text){
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

        private Rank(final int value, final boolean isfigure){
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

    private final Suit suit;
    private final Rank rank;

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
