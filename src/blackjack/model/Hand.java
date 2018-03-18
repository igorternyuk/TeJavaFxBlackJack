package blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by igor on 16.03.18.
 */
public class Hand {
    public static final int MAX_NUMBER_OF_CARDS = 5;
    public static final int TWO_ACES = 100;
    public static final int LIMIT = 21;
    private List<Card> cards = new ArrayList<>(MAX_NUMBER_OF_CARDS);
    private int total = 0;
    private boolean areTwoAces = false;
    private boolean isBlackJack = false;
    private boolean isBlackJackWithSameSuitCards = false;
    private boolean areThreeSevens = false;
    private boolean hasHandChanged = true;

    public void addCard(final Card card) {
        if (this.cards.size() < MAX_NUMBER_OF_CARDS) {
            this.cards.add(card);
        }
        this.hasHandChanged = true;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(this.cards);
    }

    public boolean hasTwoAces() {
        if (this.hasHandChanged) evaluate();
        return this.areTwoAces;
    }

    public boolean hasBlackJack() {
        if (this.hasHandChanged) evaluate();
        return this.isBlackJack;
    }

    public boolean hasBlackJackWithSameSuitCards() {
        if (this.hasHandChanged) evaluate();
        return this.isBlackJackWithSameSuitCards;
    }


    public boolean hasThreeSevens() {
        if (this.hasHandChanged) evaluate();
        return this.areThreeSevens;
    }

    public int getNumberOfCards() {
        return this.cards.size();
    }

    public int evaluate() {
        if (!this.hasHandChanged) return this.total;
        this.total = 0;
        int aceNumber = 0;
        int sevensNumber = 0;
        int tensNumber = 0;
        int figuresNumber = 0;
        for (final Card card : this.cards) {
            this.total += card.getValue();
            if (card.isAce()) {
                ++aceNumber;
            } else if (card.isSeven()) {
                ++sevensNumber;
            } else if (card.isTen()) {
                ++tensNumber;
            } else if (card.isFigure()) {
                ++figuresNumber;
            }
        }
        if (aceNumber > 0) {
            if (this.cards.size() == 2) {
                if (aceNumber == 2) {
                    this.total = TWO_ACES;
                    this.areTwoAces = true;
                } else if (aceNumber == 1 && (tensNumber == 1 || figuresNumber == 1)) {
                    this.isBlackJack = true;
                    if (this.cards.get(0).getSuit().equals(this.cards.get(1).getSuit())) {
                        this.isBlackJackWithSameSuitCards = true;
                    }
                }

            } else if (this.total > LIMIT) {
                this.total -= aceNumber * Card.BIG_ACE_VALUE;
                this.total += aceNumber * Card.LOW_ACE_VALUE;
            }
        } else if (this.cards.size() == 3 && sevensNumber == 3) {
            System.out.println("Three sevens detected");
            this.areThreeSevens = true;
        }
        hasHandChanged = false;
        return this.total;
    }

    public boolean isBusted() {
        if (hasTwoAces()) return false;
        return evaluate() > Hand.LIMIT;
    }

    public void flipFirstCard() {
        if (!this.cards.isEmpty()) {
            this.cards.get(0).flip();
        }
    }

    public void clear() {
        this.cards.clear();
        this.areTwoAces = false;
        this.isBlackJack = false;
        this.isBlackJackWithSameSuitCards = false;
        this.areThreeSevens = false;
    }

    @Override
    public String toString() {
        if (this.areTwoAces) {
            return "Two aces";
        } else if (this.isBlackJack) {
            return "Black Jack";
        } else {
            StringBuilder textRepresentation = new StringBuilder();
            for (final Card card : this.cards) {
                textRepresentation.append(card).append("-");
            }
            return !textRepresentation.toString().isEmpty()
                    ? textRepresentation.substring(0, textRepresentation.length() - 1)
                    : "";
        }
    }
}
