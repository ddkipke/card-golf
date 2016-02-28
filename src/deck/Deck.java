package deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import deck.Card.CardValue;
import deck.Card.Suit;

public class Deck {

	private List<Card> cardList;

	private Deck() {
		this.cardList = new ArrayList<Card>(52);
		CardValue[] cardValues = Card.CardValue.values();
		Suit[] suits = Card.Suit.values();
		
		for (int i = 0; i < cardValues.length; i++) {
			for (int j = 0; j < suits.length; j++) {
				this.cardList.add(new Card(cardValues[i], suits[j]));
			}
		}
	}
	
	public static Deck createShuffledDeck() {
		Deck deck = new Deck();
		deck.shuffle();
		return deck;
	}

	public int size() {
		return this.cardList.size();
	}

	public Card popTopCard() {
		Card card = this.cardList.get(this.cardList.size() - 1);
		this.cardList.remove(card);
		return card;
	}

	private void shuffle() {
		List<Card> shuffledList = new ArrayList<Card>(52);
		Random random = new Random(System.nanoTime());
		while (this.cardList.size() > 0) {
			Card card = this.cardList.get(random.nextInt(this.cardList.size()));
			this.cardList.remove(card);
			shuffledList.add(card);
		}
		this.cardList = shuffledList;
	} 
}
