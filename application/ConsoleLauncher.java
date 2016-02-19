package application;

import java.util.List;
import java.util.Scanner;

import dealer.Dealer;
import deck.Card;
import deck.Deck;
import player.Hand;
import player.Hand.Position;

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

		for (int i = 0; i < hands.size(); i++) {
			String handIdentifier = "Player: " + i;
			String eastCard =  "WEST:  " + hands.get(i).getCard(Hand.Position.WEST);
			String southCard = "SOUTH: " + hands.get(i).getCard(Hand.Position.SOUTH);
			
			System.out.println(handIdentifier);
			System.out.println(southCard);
			System.out.println(eastCard);
			System.out.println();
			System.out.println();
		}
		
		Card kitty = deck.popTopCard();
		
		for (int turn = 0; turn < 4; turn++) {
			System.out.println("Round: " + turn);
			for (int player = 0; player < numberPlayers; player++) {
				System.out.println("Player: " + player);
				kitty = drawNewKittyIfDesired(inputScanner, deck, kitty, util);
				kitty = takeOrRevealDesiredCard(inputScanner, hands.get(player), kitty, util);
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
		}
		
		inputScanner.close();
		
	}

	private static Card drawNewKittyIfDesired(Scanner inputScanner, Deck deck, Card kitty, ConsoleUtilities util) {
		System.out.println("Kitty: " + kitty);
		if (util.yesNoQuestion("Draw a different card?")) {
			kitty = deck.popTopCard();
			System.out.println("New kitty: " + kitty);
		}
		return kitty;
	}
	
	private static Card takeOrRevealDesiredCard(Scanner inputScanner, Hand hand, Card kitty, ConsoleUtilities util) {
		if (util.yesNoQuestion("Take kitty?")) {
			System.out.println("Location to replace? " + util.getUnlockedPositionsAsFormattedString(hand));
			Hand.Position position = util.getPositionFromInput(hand);
			Card cardToSwapOut = hand.getCard(position);
			hand.setCard(position, kitty);
			hand.lockPosition(position);
			System.out.println("You placed a " + kitty + " in the " + position);
			kitty = cardToSwapOut;
			System.out.println("You discarded a " + kitty);
		} else {
			System.out.println("Location to reveal? " + util.getUnlockedPositionsAsFormattedString(hand));
			Hand.Position position = util.getPositionFromInput(hand);
			hand.lockPosition(position);
			System.out.println("You revealed a " + hand.getCard(position) + " in the " + position);
		}
		return kitty;
	}

	

}
