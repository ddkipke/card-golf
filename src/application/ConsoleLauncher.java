package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.Scoreboard.PlayerScore;
import dealer.Dealer;
import deck.Card;
import deck.Deck;
import player.Hand;
import player.Hand.Position;
import player.PlayerHuman;
import player.PlayerInterface;

public class ConsoleLauncher {

	public static void main(String[] args) {
		
		Scanner inputScanner = new Scanner(System.in);
		ConsoleUtilities util = new ConsoleUtilities(inputScanner);
		
		int numberPlayers = util.getNumberOfPlayers();
		
		List<PlayerInterface> players = new ArrayList<PlayerInterface>();
		
		for (int i = 0; i < numberPlayers; i++) {
			players.add(new PlayerHuman(util, null, new Integer(i+1).toString()));
		}

		Scoreboard scoreboard = new Scoreboard(players);
		
		for (int round = 0; round < CommonProperties.NUMBER_ROUNDS; round++) {
			doRound(players, scoreboard, round);
		}

		ConsoleOutput.printFinalScores(scoreboard);
		
		inputScanner.close();
		
	}


	private static void doRound(List<PlayerInterface> players, Scoreboard scoreboard, int round) {
		Deck deck = Deck.createShuffledDeck();
		List<Hand> hands = Dealer.deal(deck, players.size());
		
		for (int i = 0; i < hands.size(); i++) {
			players.get(i).setNewHand(hands.get(i));
			players.get(i).checkTwoKnownCards();
		}
		
		Card discard = deck.popTopCard();
		
		System.out.println("ROUND " + (round + 1));
		for (int turn = 0; turn < CommonProperties.NUMBER_TURNS; turn++) {
			System.out.println("Turn: " + (turn + 1));
			for (PlayerInterface player : players) {
				System.out.println("Player: " + player.getName());
				discard = playersTurnLogic(deck, discard, player);
			}
			System.out.println();
			System.out.println();
		}
		
		scoreboard.incrementTotalScores();
		PlayerScore[] scores = scoreboard.getOrderedPlayerScores();
		
		ConsoleOutput.printRoundScores(scores);
	}


	private static Card playersTurnLogic(Deck deck, Card discard, PlayerInterface player) {
		if (player.wantsCardFromDiscard(discard)) {
			discard = player.swapCards(discard);
			return discard;
		} else {
			discard = deck.popTopCard();
		}
		
		if (player.wantsCardFromTopOfDeck(discard)) {
			discard = player.swapCards(discard);
		} else {
			player.revealCard();
		}
		return discard;
	}
}
