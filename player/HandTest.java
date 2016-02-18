package player;

import java.util.List;

import org.mockito.Mockito;

import deck.Card;
import junit.framework.TestCase;
import player.Hand.Position;

public class HandTest extends TestCase {
	
	private Hand hand;
	private Card northCard;
	private Card southCard;
	private Card eastCard;
	private Card westCard;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		westCard = Mockito.mock(Card.class);
		eastCard = Mockito.mock(Card.class);
		southCard = Mockito.mock(Card.class);
		northCard = Mockito.mock(Card.class);
		
		hand = new Hand();
		hand.setCard(Hand.Position.NORTH, northCard);
		hand.setCard(Hand.Position.EAST, eastCard);
		hand.setCard(Hand.Position.SOUTH, southCard);
		hand.setCard(Hand.Position.WEST, westCard);
	}
	
	public void testGettersAndSetters() throws Exception {
		assertEquals(northCard, hand.getCard(Position.NORTH));
		assertEquals(eastCard, hand.getCard(Position.EAST));
		assertEquals(southCard, hand.getCard(Position.SOUTH));
		assertEquals(westCard, hand.getCard(Position.WEST));
	}
	
	public void testLockedPositions() throws Exception {
		List<Hand.Position> unlockedPositions = hand.getUnlockedPositions();
		assertEquals(4, unlockedPositions.size());
		assertTrue(unlockedPositions.contains(Hand.Position.NORTH));
		assertTrue(unlockedPositions.contains(Hand.Position.EAST));
		assertTrue(unlockedPositions.contains(Hand.Position.SOUTH));
		assertTrue(unlockedPositions.contains(Hand.Position.WEST));
		
		hand.lockPosition(Hand.Position.NORTH);
		assertEquals(3, unlockedPositions.size());
		assertFalse(unlockedPositions.contains(Hand.Position.NORTH));
		assertTrue(unlockedPositions.contains(Hand.Position.EAST));
		assertTrue(unlockedPositions.contains(Hand.Position.SOUTH));
		assertTrue(unlockedPositions.contains(Hand.Position.WEST));
		
		hand.lockPosition(Hand.Position.WEST);
		assertEquals(2, unlockedPositions.size());
		assertFalse(unlockedPositions.contains(Hand.Position.NORTH));
		assertTrue(unlockedPositions.contains(Hand.Position.EAST));
		assertTrue(unlockedPositions.contains(Hand.Position.SOUTH));
		assertFalse(unlockedPositions.contains(Hand.Position.WEST));
		
		assertFalse(hand.setCard(Hand.Position.NORTH, eastCard));
		assertTrue(hand.setCard(Hand.Position.EAST, southCard));
		assertTrue(hand.setCard(Hand.Position.SOUTH, westCard));
		assertFalse(hand.setCard(Hand.Position.WEST, northCard));
		
		assertEquals(northCard, hand.getCard(Position.NORTH));
		assertEquals(southCard, hand.getCard(Position.EAST));
		assertEquals(westCard, hand.getCard(Position.SOUTH));
		assertEquals(westCard, hand.getCard(Position.WEST));
	}
	
	public void testCalculateScoreNoMatches() throws Exception {
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.FIVE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.TWO, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.FOUR, Card.Suit.DIAMONDS));
		assertEquals(12, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.FIVE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.TWO, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.SIX, Card.Suit.DIAMONDS));
		assertEquals(14, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.KING, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.TWO, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.SIX, Card.Suit.DIAMONDS));
		assertEquals(9, hand.getScore());
	}
	
	public void testCalculateScorePairs() throws Exception {
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.KING, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.SIX, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.SIX, Card.Suit.CLUBS));
		assertEquals(1, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.SEVEN, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.JACK, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.SEVEN, Card.Suit.DIAMONDS));
		assertEquals(11, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.SEVEN, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.JACK, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.SEVEN, Card.Suit.DIAMONDS));
		assertEquals(11, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.NINE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.NINE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.JACK, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.TEN, Card.Suit.DIAMONDS));
		assertEquals(20, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.NINE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.FIVE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.FIVE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.TEN, Card.Suit.DIAMONDS));
		assertEquals(19, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.NINE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.FIVE, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.FIVE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.NINE, Card.Suit.DIAMONDS));
		assertEquals(0, hand.getScore());
	}
	
	public void testCalculateScoreTriple() throws Exception {
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.QUEEN, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.QUEEN, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.QUEEN, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.EIGHT, Card.Suit.DIAMONDS));
		assertEquals(7, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.TWO, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.TWO, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.KING, Card.Suit.DIAMONDS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.TWO, Card.Suit.DIAMONDS));
		assertEquals(-1, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.FOUR, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.FIVE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.FOUR, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.FOUR, Card.Suit.DIAMONDS));
		assertEquals(4, hand.getScore());
	}
	
	public void testCalculateScoreQuad() throws Exception {
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.SIX, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.SIX, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.SIX, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.SIX, Card.Suit.DIAMONDS));
		assertEquals(-5, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.ACE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.ACE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.ACE, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		assertEquals(-5, hand.getScore());
	}
	
	public void testCalculateScoreWithWilds() throws Exception {
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.THREE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.FIVE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.FOUR, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.SIX, Card.Suit.DIAMONDS));
		assertEquals(9, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.THREE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.THREE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.ACE, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.THREE, Card.Suit.DIAMONDS));
		assertEquals(-5, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.THREE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.NINE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.TEN, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.THREE, Card.Suit.DIAMONDS));
		assertEquals(0, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.THREE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.THREE, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.THREE, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.THREE, Card.Suit.DIAMONDS));
		assertEquals(-5, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.THREE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.SEVEN, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.THREE, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.ACE, Card.Suit.DIAMONDS));
		assertEquals(0, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.THREE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.JACK, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.JACK, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.THREE, Card.Suit.DIAMONDS));
		assertEquals(-5, hand.getScore());
		
		hand.setCard(Hand.Position.NORTH, new Card(Card.CardValue.THREE, Card.Suit.HEARTS));
		hand.setCard(Hand.Position.EAST, new Card(Card.CardValue.JACK, Card.Suit.SPADES));
		hand.setCard(Hand.Position.SOUTH, new Card(Card.CardValue.TWO, Card.Suit.CLUBS));
		hand.setCard(Hand.Position.WEST, new Card(Card.CardValue.TWO, Card.Suit.DIAMONDS));
		assertEquals(0, hand.getScore());
	}
}

