package blackjack.model;

/**
 * Created by igor on 17.03.18.
 */
public class AbstractPlayer {
    private String name;
    private Hand hand;

    public void addCard(final Card card) {
        System.out.println("Added card: " + card);
    }
}

/*
* inline auto getName() const noexcept { return m_name; }
        inline const std::vector<std::unique_ptr<Card>>& getCards() const { return m_hand->cards(); }
        bool isBusted() const;
        int getHandTotal() const;
        bool hasBlackJack() const;
        bool hasTwoCardWithSameSuit() const;
        bool hasTwoAces() const;
        bool hasThreeSevens() const;
        virtual std::string getHandTextDescription() const;
        //indicates whether or not abstract player wants to keep hitting
        virtual bool isHitting() const = 0;
        void addCard(std::unique_ptr<Card> &card);
        void clearCards();
* */
