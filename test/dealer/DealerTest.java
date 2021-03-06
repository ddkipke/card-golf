package dealer;

import java.util.ArrayList;
import java.util.List;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import deck.Card;
import deck.Deck;
import junit.framework.TestCase;
import player.Hand;
import player.Hand.Position;
import player.PlayerInterface;

public class DealerTest extends TestCase {

	public void testDeal_case1() throws Exception {
		
		int numberHands = 2;
		Deck deck = Mockito.mock(Deck.class);
		
		List<Card> expectedCards = new ArrayList<Card>();
		for (int i = 0; i < numberHands * 4; i++) {
			expectedCards.add(Mockito.mock(Card.class));
		}
		Mockito.when(deck.popTopCard()).thenReturn(
				expectedCards.get(0),
				expectedCards.get(1),
				expectedCards.get(2),
				expectedCards.get(3),
				expectedCards.get(4),
				expectedCards.get(5),
				expectedCards.get(6),
				expectedCards.get(7)
				);
		
		ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
		players.add(Mockito.mock(PlayerInterface.class));
		players.add(Mockito.mock(PlayerInterface.class));
		
		Dealer.deal(deck, players);
		
		ArgumentCaptor<Hand> captor = ArgumentCaptor.forClass(Hand.class);
		
		Mockito.verify(players.get(0)).setNewHand(captor.capture());
		Mockito.verify(players.get(1)).setNewHand(captor.capture());
		
		List<Hand> hands = captor.getAllValues();
		
		assertSame(numberHands, hands.size());

		assertSame(expectedCards.get(0), hands.get(0).getCard(Hand.Position.NORTH));
		assertSame(expectedCards.get(2), hands.get(0).getCard(Hand.Position.EAST));
		assertSame(expectedCards.get(4), hands.get(0).getCard(Hand.Position.SOUTH));
		assertSame(expectedCards.get(6), hands.get(0).getCard(Hand.Position.WEST));
		
		assertSame(expectedCards.get(1), hands.get(1).getCard(Hand.Position.NORTH));
		assertSame(expectedCards.get(3), hands.get(1).getCard(Hand.Position.EAST));
		assertSame(expectedCards.get(5), hands.get(1).getCard(Hand.Position.SOUTH));
		assertSame(expectedCards.get(7), hands.get(1).getCard(Hand.Position.WEST));
	}
	
	public void testDeal_case2() throws Exception {
		int numberHands = 6;
		Deck deck = Mockito.mock(Deck.class);

		List<Card> expectedCards = new ArrayList<Card>();
		for (int i = 0; i < 52; i++) {
			expectedCards.add(Mockito.mock(Card.class));
		}
		Mockito.when(deck.popTopCard()).thenReturn(expectedCards.get(0),
				expectedCards.subList(1, expectedCards.size() - 1).toArray(new Card[51]));
		
		
		ArrayList<PlayerInterface> players = new ArrayList<PlayerInterface>();
		players.add(Mockito.mock(PlayerInterface.class));
		players.add(Mockito.mock(PlayerInterface.class));
		players.add(Mockito.mock(PlayerInterface.class));
		players.add(Mockito.mock(PlayerInterface.class));
		players.add(Mockito.mock(PlayerInterface.class));
		players.add(Mockito.mock(PlayerInterface.class));
		
		Dealer.deal(deck, players);
		
		ArgumentCaptor<Hand> captor = ArgumentCaptor.forClass(Hand.class);
		
		Mockito.verify(players.get(0)).setNewHand(captor.capture());
		Mockito.verify(players.get(1)).setNewHand(captor.capture());
		Mockito.verify(players.get(2)).setNewHand(captor.capture());
		Mockito.verify(players.get(3)).setNewHand(captor.capture());
		Mockito.verify(players.get(4)).setNewHand(captor.capture());
		Mockito.verify(players.get(5)).setNewHand(captor.capture());
		
		List<Hand> hands = captor.getAllValues();
		
		assertSame(numberHands, hands.size());

		Position[] positions = Hand.Position.values();
		for (int j = 0; j < positions.length; j++) {
			for (int i = 0; i < numberHands; i++) {
				assertSame(expectedCards.get(i + numberHands*j), hands.get(i).getCard(positions[j]));
			}
		}
	}
}
