package deck;

import java.util.HashMap;

import deck.Card.CardValue;
import deck.Card.Suit;
import junit.framework.TestCase;

public class DeckTest extends TestCase {

	public void testCreateDeckWith52UniqueCards() throws Exception {
		 
		HashMap<Card, Boolean> cardWasPresentMap = new HashMap<Card, Boolean>();
		CardValue[] cardValues = Card.CardValue.values();
		Suit[] suitValues = Card.Suit.values();
		
		for (int i = 0; i < suitValues.length; i++) {
			for (int j = 0; j < cardValues.length; j++) {
				cardWasPresentMap.put(new Card(cardValues[j], suitValues[i]), false);
			}
		}
		
		Deck deck = Deck.createShuffledDeck();
		assertEquals(52, deck.size());
		for (int i = 0; i < 52; i++) {
			Card card = deck.popTopCard();
			cardWasPresentMap.put(card, true);
			assertEquals(51 - i, deck.size());
		}

		assertFalse(cardWasPresentMap.containsValue(false));
	}
	
	public void testDecksAreAlmostAlwaysDifferent() throws Exception {
		for (int i = 0; i < 50000; i++) {
			checkTwoDecksDiffer(Deck.createShuffledDeck(), Deck.createShuffledDeck());
		}
	}

	private void checkTwoDecksDiffer(Deck deck1, Deck deck2) {
		int deckSize = deck1.size();
		assertEquals(deckSize, deck2.size());
		boolean differencePresent = false;
		for (int i = 0; i < deckSize; i++) {
			if (!deck1.popTopCard().equals(deck2.popTopCard())) {
				differencePresent = true;
				break;
			}
		}
		
		assertTrue(differencePresent);
	}
}
