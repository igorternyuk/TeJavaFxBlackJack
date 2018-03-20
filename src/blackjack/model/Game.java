package blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by igor on 17.03.18.
 */
public class Game {
    public static final int NUMBER_OF_PLAYERS = 5;
    private static final int INITIAL_DEAL_CARD_NUMBER = 2;
    private static final double CASINO_START_POT = 1000000;
    private static final double INITIAL_PLAYER_MONEY_BALANCE = 1000;
    private static final double BONUS_FOR_THREE_SEVENS = 500;
    private static final double BONUS_FOR_BLACK_JACK_WITH_SAME_SUIT_CARDS = 300;
    private static final double MONEY_COEFFICIENT_FOR_TWO_ACES = 2.0;
    private static final double MONEY_COEFFICIENT_FOR_BLACK_JACK = 1.5;
    private static final double MONEY_COEFFICIENT_REGULAR = 1.0;
    private int roundNumber = 0;
    private final Deck deck = new Deck();
    private final Dealer dealer = new Dealer("Casino", PlayerType.COMPUTER, CASINO_START_POT);
    private final List<Player> players = new ArrayList<>(NUMBER_OF_PLAYERS);

    public Game() {
        this.players.add(new Player("You", PlayerType.HUMAN, INITIAL_PLAYER_MONEY_BALANCE));
        for (int i = 1; i < NUMBER_OF_PLAYERS; ++i) {
            this.players.add(new Player("Bot" + i, PlayerType.COMPUTER, INITIAL_PLAYER_MONEY_BALANCE));
        }
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public Dealer getDealer() {
        return this.dealer;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    public void startNewGame() {
        this.roundNumber = 0;
        this.dealer.setCasinoBalance(CASINO_START_POT);
        this.players.forEach(player -> player.setMoneyBalance(INITIAL_PLAYER_MONEY_BALANCE));
        startNewRound();
    }

    public void startNewRound() {
        ++this.roundNumber;
        this.deck.clear();
        this.deck.populate();
        this.deck.shuffle();
        dealer.clearCards();
        players.forEach(player -> {
            player.clearCards();
            player.updateBet();
            player.stake();
            player.setStatus(PlayerStatus.HITTING);
            for (int i = 0; i < INITIAL_DEAL_CARD_NUMBER; ++i) {
                this.deck.deal(player);
            }
        });
        for (int i = 0; i < INITIAL_DEAL_CARD_NUMBER; ++i) {
            this.deck.deal(this.dealer);
        }
        this.dealer.setStatus(PlayerStatus.HITTING);
        this.dealer.flipFirstCard();
    }

    public void hit() {
        final Player humanPlayer = this.players.get(0);
        if (humanPlayer.getStatus().equals(PlayerStatus.HITTING)) {
            this.deck.deal(humanPlayer);
            if (humanPlayer.isBusted()) {
                humanPlayer.setStatus(PlayerStatus.LOST);
                dealToBotsAndDealer();
                determineWinners();
            }
        }
    }

    public void stand() {
        final Player humanPlayer = this.players.get(0);
        if (humanPlayer.getStatus().equals(PlayerStatus.HITTING)) {
            humanPlayer.setStatus(PlayerStatus.STAND);
            dealToBotsAndDealer();
            determineWinners();
        }
    }

    private void awardWinner(final Player player) {
        if (!player.getStatus().equals(PlayerStatus.WON)) return;
        if (player.hasTwoAces()) {
            this.dealer.takeMoneyForWinner(MONEY_COEFFICIENT_FOR_TWO_ACES * player.getBet());
            player.award(MONEY_COEFFICIENT_FOR_TWO_ACES);
        } else if (player.hasBlackJack()) {
            this.dealer.takeMoneyForWinner(MONEY_COEFFICIENT_FOR_BLACK_JACK * player.getBet());
            player.award(MONEY_COEFFICIENT_FOR_BLACK_JACK);
            if (player.hasBlackJackWithSameSuitCards()) {
                this.dealer.takeMoneyForWinner(BONUS_FOR_BLACK_JACK_WITH_SAME_SUIT_CARDS);
                player.addBonus(BONUS_FOR_BLACK_JACK_WITH_SAME_SUIT_CARDS);
            }
        } else {
            this.dealer.takeMoneyForWinner(MONEY_COEFFICIENT_REGULAR * player.getBet());
            player.award(MONEY_COEFFICIENT_REGULAR);
            if (player.hasThreeSevens()) {
                this.dealer.takeMoneyForWinner(BONUS_FOR_THREE_SEVENS);
                player.addBonus(BONUS_FOR_THREE_SEVENS);
            }
        }
    }

    private void dealToBotsAndDealer() {
        this.players.stream().filter(player -> player.getType().equals(PlayerType.COMPUTER)).forEach(player -> {
            while (player.isHitting()) {
                //System.out.println("Dealing to player");
                this.deck.deal(player);
            }
            player.setStatus(PlayerStatus.STAND);
        });
        this.dealer.flipFirstCard();
        while (this.dealer.isHitting()) {
            //System.out.println("Dealing to dealer");
            this.deck.deal(this.dealer);
        }
        this.dealer.setStatus(PlayerStatus.STAND);
    }

    private void determineWinners() {
        if (this.dealer.hasTwoAces()) {
            for (final Player player : this.players) {
                if (player.hasTwoAces()) {
                    player.setStatus(PlayerStatus.PUSH);
                    player.returnBet();
                } else {
                    player.setStatus(PlayerStatus.LOST);
                    this.dealer.takeLosersBet(player.getBet());
                }
            }
        } else if (this.dealer.hasBlackJack()) {
            for (final Player player : this.players) {
                if (player.hasTwoAces()) {
                    player.setStatus(PlayerStatus.WON);
                    this.dealer.takeMoneyForWinner(player.getBet() * MONEY_COEFFICIENT_FOR_TWO_ACES);
                    this.awardWinner(player);
                } else if (player.hasBlackJack()) {
                    player.setStatus(PlayerStatus.PUSH);
                    player.returnBet();
                } else {
                    player.setStatus(PlayerStatus.LOST);
                    this.dealer.takeLosersBet(player.getBet());
                }
            }
        } else if (this.dealer.isBusted()) {
            this.players.forEach(player -> {
                if (player.isBusted()) {
                    player.setStatus(PlayerStatus.LOST);
                    this.dealer.takeLosersBet(player.getBet());
                } else {
                    player.setStatus(PlayerStatus.WON);
                    awardWinner(player);
                }
            });
        } else {
            final int dealerTotal = this.dealer.getHandTotal();
            this.players.forEach(player -> {
                if (player.isBusted() || player.getHandTotal() < dealerTotal) {
                    player.setStatus(PlayerStatus.LOST);
                    this.dealer.takeLosersBet(player.getBet());
                } else if (player.getHandTotal() == dealerTotal) {
                    player.setStatus(PlayerStatus.PUSH);
                    player.returnBet();
                } else {
                    player.setStatus(PlayerStatus.WON);
                    awardWinner(player);
                }
            });
        }
    }
}
