package deck;

import deck.Card.CardValue;
import deck.Card.Suit;
import junit.framework.TestCase;

public class CardTest extends TestCase {

	public void testConstructor() throws Exception {
		Card knownCard = new Card(Card.CardValue.JACK, Card.Suit.HEARTS);
		assertSame(Card.CardValue.JACK, knownCard.getCardValue());
		assertSame(Card.Suit.HEARTS, knownCard.getSuit());
		assertFalse(knownCard.getFaceUp());
		knownCard.setFaceUp();
		assertTrue(knownCard.getFaceUp());
	}
	
	public void testCardEquality() throws Exception {
		Card card1 = new Card(CardValue.TEN, Suit.HEARTS);
		Card card2 = new Card(CardValue.ACE, Suit.SPADES);
		Card card3 = new Card(CardValue.TEN, Suit.HEARTS);
		Card card4 = new Card(CardValue.TEN, Suit.SPADES);

		assertTrue(card1.equals(card3));
		assertFalse(card1.equals(card2));
		assertFalse(card1.equals(card4));
		
		assertFalse(card2.equals(card1));
		assertFalse(card2.equals(card3));
		assertFalse(card2.equals(card4));
		
		assertTrue(card3.equals(card1));
		assertFalse(card3.equals(card2));
		assertFalse(card3.equals(card4));
		
		assertFalse(card4.equals(card1));
		assertFalse(card4.equals(card2));
		assertFalse(card4.equals(card3));
	}
	
	public void testToString() throws Exception {
		Card card = new Card(CardValue.TEN, Suit.SPADES);
		assertEquals("TEN OF SPADES", card.toString());
		card = new Card(CardValue.FIVE, Suit.DIAMONDS);
		assertEquals("FIVE OF DIAMONDS", card.toString());
	}
}
