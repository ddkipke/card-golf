package player;

import application.ConsoleUtilities;
import deck.Card;
import player.Hand.Position;

public class PlayerHuman implements PlayerInterface {

	private final ConsoleUtilities consoleUtil;
	private final Hand hand;
	private final String takeItQuestion = ". Do you want to take it?";
	private final String deckDrawString = "You drew a ";
	private final String discardTakeString = "The discard contains a ";
	private final int number;

	public PlayerHuman(ConsoleUtilities consoleUtil, Hand hand, int number) {
		this.consoleUtil = consoleUtil;
		this.hand = hand;
		this.number = number; 
	}

	@Override
	public boolean wantsCardFromDiscard(Card card) {
		return this.consoleUtil.yesNoQuestion(this.discardTakeString + card + this.takeItQuestion);
	}

	@Override
	public boolean wantsCardFromTopOfDeck(Card card) {
		return this.consoleUtil.yesNoQuestion(this.deckDrawString + card + this.takeItQuestion);
	}

	@Override
	public void checkTwoKnownCards() {
		String handIdentifier = "Player: " + number;
		String eastCard =  "WEST:  " + hand.getCard(Hand.Position.WEST);
		String southCard = "SOUTH: " + hand.getCard(Hand.Position.SOUTH);
		
		System.out.println(handIdentifier);
		System.out.println(southCard);
		System.out.println(eastCard);
		System.out.println();
	}

	@Override
	public void revealCard() {
		String revealQuestion = "Which position do you want to reveal?";
		Position positionToReveal = consoleUtil.getPositionFromInput(hand, revealQuestion);
		System.out.println("You revealed a " + hand.getCard(positionToReveal) +
				" in the " + positionToReveal);
		hand.lockPosition(positionToReveal);
	}

	@Override
	public Card swapCards(Card cardIn) {
		String discardQuestion = "Which card do you want to discard?";
		Position position = consoleUtil.getPositionFromInput(hand, discardQuestion);
		Card cardOut = hand.getCard(position);
		System.out.println("You discarded a " + cardOut);
		hand.setCard(position, cardIn);
		hand.lockPosition(position);
		return cardOut;
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public ConsoleUtilities getConsoleUtilities() {
		return this.consoleUtil;
	}

	@Override
	public int getNumber() {
		return this.number;
	}
}