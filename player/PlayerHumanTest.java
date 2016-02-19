package player;

import org.mockito.Mockito;

import application.ConsoleUtilities;
import deck.Card;
import junit.framework.TestCase;

public class PlayerHumanTest extends TestCase {

	private PlayerHuman player;
	private ConsoleUtilities consoleUtil;
	private Hand hand;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		consoleUtil = Mockito.mock(ConsoleUtilities.class);
		hand = Mockito.mock(Hand.class);
		player = new PlayerHuman(consoleUtil, hand);
	}
	
	public void testGetters() throws Exception {
		assertSame(consoleUtil, player.getConsoleUtilities());
		assertSame(hand, player.getHand());
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
}
