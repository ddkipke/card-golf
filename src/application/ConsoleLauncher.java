package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dealer.Dealer;
import deck.Card;
import deck.Deck;
import player.Hand;
import player.Hand.Position;
import player.PlayerHuman;
import player.PlayerInterface;

public class ConsoleLauncher {

	public static void main(String[] args) {
		
		int numberPlayers;
		System.out.println("Enter number of players:");
		Scanner inputScanner = new Scanner(System.in);
		ConsoleUtilities util = new ConsoleUtilities(inputScanner);
		numberPlayers = inputScanner.nextInt();

		System.out.println("Number of players: " + numberPlayers);
		Deck deck = Deck.createShuffledDeck();
		
		List<Hand> hands = Dealer.deal(deck, numberPlayers);
		List<PlayerInterface> players = new ArrayList<PlayerInterface>();
		
		for (int round = 0; round < CommonProperties.NUMBER_ROUNDS; round++) {
			
			for (int i = 0; i < hands.size(); i++) {
				PlayerInterface player = new PlayerHuman(util, hands.get(i), i + 1);
				player.checkTwoKnownCards();
				players.add(player);
			}
			
			Card discard = deck.popTopCard();
			
			System.out.println("ROUND " + (round + 1));
			for (int turn = 0; turn < CommonProperties.NUMBER_TURNS; turn++) {
				System.out.println("Turn: " + (turn + 1));
				for (PlayerInterface player : players) {
					System.out.println("Player: " + player.getNumber());
					discard = playersTurnLogic(deck, discard, player);
				}
				System.out.println();
				System.out.println();
			}
			
			for (int player = 0; player < numberPlayers; player++) {
				Hand hand = hands.get(player);
				System.out.println("Player: " + player);
				System.out.println("Cards:");
				System.out.println(hand.getCard(Position.NORTH));
				System.out.println(hand.getCard(Position.EAST));
				System.out.println(hand.getCard(Position.SOUTH));
				System.out.println(hand.getCard(Position.WEST));
				System.out.println("Score: " + hand.getScore());
				players.get(player).incrementScore(hand.getScore());
				System.out.println("Total Score: " + players.get(player).getScore());
				System.out.println();
			}
			
			System.out.println();
			System.out.println();
		}

		System.out.println("***FINAL SCORES***");
		
		for (int player = 0; player < numberPlayers; player++) {
			System.out.println("Player: " + player);
			System.out.println("FinalScore: " + players.get(player).getScore());
			System.out.println();
		}
		
		inputScanner.close();
		
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
