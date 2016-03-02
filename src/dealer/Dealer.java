package dealer;

import java.util.ArrayList;
import java.util.List;

import deck.Deck;
import player.Hand;
import player.Hand.Position;
import player.PlayerInterface;

public class Dealer {

	public static void deal(Deck deck, List<PlayerInterface> players) {
		
		Position[] cardPositions = Hand.Position.values();
		
		List<Hand> hands = new ArrayList<Hand>();
		
		for (int player = 0; player < players.size(); player++) {
			Hand hand = new Hand();
			hands.add(hand);
			players.get(player).setNewHand(hand);
		}
		for (int position = 0; position < cardPositions.length; position++) {
			for (int hand = 0; hand < hands.size(); hand++) {
				hands.get(hand).setCard(cardPositions[position], deck.popTopCard());
			}
		}
	}

}
