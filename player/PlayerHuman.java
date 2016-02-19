package player;

import application.ConsoleUtilities;
import deck.Card;

public class PlayerHuman implements PlayerInterface {

	private ConsoleUtilities consoleUtil;
	private Hand hand;

	public PlayerHuman(ConsoleUtilities consoleUtil, Hand hand) {
		this.consoleUtil = consoleUtil;
		this.hand = hand;
	}

	@Override
	public boolean wantsCardFromDiscard(Card card) {
		return consoleUtil.yesNoQuestion("The discard contains a " + card + ". Do you want to take it?");
	}

	@Override
	public boolean wantsCardFromTopOfDeck(Card card) {
		return false;
	}

	public ConsoleUtilities getConsoleUtilities() {
		return this.consoleUtil;
	}

	public Hand getHand() {
		return this.hand;
	}

}
