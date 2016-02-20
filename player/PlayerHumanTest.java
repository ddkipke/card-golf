package player;

import java.util.Random;

import org.mockito.Mockito;

import application.ConsoleUtilities;
import deck.Card;
import junit.framework.TestCase;

public class PlayerHumanTest extends TestCase {

	private PlayerHuman player;
	private ConsoleUtilities consoleUtil;
	private Hand hand;
	private int number;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		consoleUtil = Mockito.mock(ConsoleUtilities.class);
		hand = Mockito.mock(Hand.class);
		Random random = new Random(System.currentTimeMillis());
		number = random.nextInt();
		player = new PlayerHuman(consoleUtil, hand, number);
	}
	
	public void testGetters() throws Exception {
		assertSame(consoleUtil, player.getConsoleUtilities());
		assertSame(hand, player.getHand());
		assertEquals(number, player.getNumber());
	}
	
	public void testWantsCardFromDiscard() throws Exception {
		Card card1 = Mockito.mock(Card.class);
		Mockito.when(consoleUtil.yesNoQuestion(
				"The discard contains a " + card1 + ". Do you want to take it?")).thenReturn(true);
		assertTrue(player.wantsCardFromDiscard(card1));
		
		Card card2 = Mockito.mock(Card.class);
		Mockito.when(consoleUtil.yesNoQuestion(
				"The discard contains a " + card2 + ". Do you want to take it?")).thenReturn(false);
		assertFalse(player.wantsCardFromDiscard(card2));
	}
	
	public void testWantsCardFromTopOfDeck() throws Exception {
		Card card1 = Mockito.mock(Card.class);
		Mockito.when(consoleUtil.yesNoQuestion(
				"You drew a " + card1 + ". Do you want to take it?")).thenReturn(true);
		assertTrue(player.wantsCardFromTopOfDeck(card1));
		
		Card card2 = Mockito.mock(Card.class);
		Mockito.when(consoleUtil.yesNoQuestion(
				"You drew a  " + card2 + ". Do you want to take it?")).thenReturn(false);
		assertFalse(player.wantsCardFromTopOfDeck(card2));
	}
	
	public void testRevealCard() throws Exception {
		Mockito.when(consoleUtil.getPositionFromInput(Mockito.eq(hand), Mockito.anyString())).thenReturn(Hand.Position.NORTH);
		player.revealCard();
		Mockito.verify(hand).lockPosition(Hand.Position.NORTH);
		Mockito.verify(hand).getCard(Hand.Position.NORTH);
		
		Mockito.when(consoleUtil.getPositionFromInput(Mockito.eq(hand), Mockito.anyString())).thenReturn(Hand.Position.EAST);
		player.revealCard();
		Mockito.verify(hand).lockPosition(Hand.Position.EAST);
		Mockito.verify(hand).getCard(Hand.Position.EAST);
	}
	
	public void testSwapCards() throws Exception {
		Mockito.when(consoleUtil.getPositionFromInput(Mockito.eq(hand), Mockito.anyString())).thenReturn(Hand.Position.WEST);
		Card cardOut = Mockito.mock(Card.class);
		Card cardIn = Mockito.mock(Card.class);
		Mockito.when(hand.getCard(Hand.Position.WEST)).thenReturn(cardOut);

		assertSame(cardOut, player.swapCards(cardIn));
		
		Mockito.verify(hand).setCard(Hand.Position.WEST, cardIn);
		Mockito.verify(hand).lockPosition(Hand.Position.WEST);
	}
}
