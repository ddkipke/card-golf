package player;

import deck.Card;

public interface PlayerInterface {

	boolean wantsCardFromDiscard(Card card);
	
	boolean wantsCardFromTopOfDeck(Card card);
	
	void checkTwoKnownCards();

	void revealCard();
	
	Card swapCards(Card cardToTake);
	
	int getNumber();
}