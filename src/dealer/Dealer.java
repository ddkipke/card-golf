package dealer;

import java.util.ArrayList;
import java.util.List;

import deck.Deck;
import player.Hand;
import player.Hand.Position;

public class Dealer {

	public static List<Hand> deal(Deck deck, int numberHands) {
		
		List<Hand> hands = new ArrayList<Hand>();
		Position[] cardPositions = Hand.Position.values();
		
		for (int handCount = 0; handCount < numberHands; handCount++) {
			hands.add(new Hand());
		}
		for (int position = 0; position < cardPositions.length; position++) {
			for (int handCount = 0; handCount < numberHands; handCount++) {
				hands.get(handCount).setCard(cardPositions[position], deck.popTopCard());
			}
		}
		return hands;
	}

}
