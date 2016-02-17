package application;

import java.util.List;
import java.util.Scanner;

import dealer.Dealer;
import deck.Card;
import deck.Deck;
import player.Hand;

public class ConsoleLauncher {

	public static void main(String[] args) {

		int numberPlayers;
		System.out.println("Enter number of players:");
		Scanner inputScanner = new Scanner(System.in);
		numberPlayers = inputScanner.nextInt();

		System.out.println("Number of players: " + numberPlayers);
		Deck deck = Deck.createShuffledDeck();
		List<Hand> hands = Dealer.deal(deck, numberPlayers);

		int totalLengthToClear = 0;
		for (int i = 0; i < hands.size(); i++) {
			String handIdentifier = "Player: " + i;
			String southCard = "South: " + hands.get(i).getCard(Hand.Position.SOUTH);
			String eastCard = "East: " + hands.get(i).getCard(Hand.Position.EAST);
			totalLengthToClear += handIdentifier.length() + southCard.length() + eastCard.length();
			
			System.out.println(handIdentifier);
			System.out.println(southCard);
			System.out.println(eastCard);
			System.out.println();
			System.out.println();
		}
		
//		String confirmationString = "Got it? (hit any key)";
//		System.out.println(confirmationString);
//		inputScanner.nextByte();
		
//		totalLengthToClear += confirmationString.length();
//		for (int i = 0; i < totalLengthToClear; i++) {
//			System.out.println('\u0008');
//		}
		
		Card kitty = deck.popTopCard();
		
		for (int round = 0; round < 4; round++) {
			System.out.println("Round: " + round);
			for (int player = 0; player < numberPlayers; player++) {
				System.out.println("Player: " + player);
				kitty = drawNewKittyIfDesired(inputScanner, deck, kitty);
				kitty = takeOrRevealDesiredCard(inputScanner, hands, kitty, player);
			}
			System.out.println();
			System.out.println();
		}
		
		for (int player = 0; player < numberPlayers; player++) {
			System.out.println("Player: " + player + " Score: " + hands.get(player).getScore());
		}
		
		inputScanner.close();
		
	}

	private static Card drawNewKittyIfDesired(Scanner inputScanner, Deck deck, Card kitty) {
		System.out.println("Kitty: " + kitty);
		if (yesNoQuestion(inputScanner, "Draw a different card?")) {
			kitty = deck.popTopCard();
			System.out.println("New kitty: " + kitty);
		}
		return kitty;
	}

	private static Card takeOrRevealDesiredCard(Scanner inputScanner, List<Hand> hands, Card kitty, int player) {
		if (yesNoQuestion(inputScanner, "Take kitty?")) {
			System.out.println("Location to replace? (n, e, s, w)");
			Hand.Position position = getPositionFromInput(inputScanner);
			Card cardToSwapOut = hands.get(player).getCard(position);
			hands.get(player).setCard(position, kitty);
			kitty = cardToSwapOut;
			System.out.println("You discarded a " + cardToSwapOut);
		} else {
			System.out.println("Location to reveal? (n, e, s, w)");
			Hand.Position position = getPositionFromInput(inputScanner);
			System.out.println("You revealed a " + hands.get(player).getCard(position));
		}
		return kitty;
	}

	private static Hand.Position getPositionFromInput(Scanner inputScanner) {
		Hand.Position position;
		switch (inputScanner.next()) {
		case "n":
			position = Hand.Position.NORTH;
			break;
		case "e":
			position = Hand.Position.EAST;
			break;
		case "s":
			position = Hand.Position.SOUTH;
			break;
		case "w":
			position = Hand.Position.WEST;
			break;
		default:
			position = Hand.Position.NORTH;
			break;
		}
		return position;
	}

	private static boolean yesNoQuestion(Scanner inputScanner, String question) {
		System.out.println(question + "(y/n)");
		String drawResponse = inputScanner.next();
		boolean equals = drawResponse.equals("y");
		return equals;
	}

}
