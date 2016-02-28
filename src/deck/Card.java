package deck;

public class Card {

	public enum CardValue {
		ACE(1),
		KING(0),
		QUEEN(10),
		JACK(10),
		TEN(10),
		NINE(9),
		EIGHT(8),
		SEVEN(7),
		SIX(6),
		FIVE(5),
		FOUR(4),
		THREE(-1),
		TWO(2);
		
		private final int value;

		CardValue(int value) {
			this.value = value;
		}
		
		public int getNumberValue() {
			return this.value;
		}
	}

	public enum Suit {
		HEARTS,
		DIAMONDS,
		CLUBS,
		SPADES
	}
	
	private final CardValue cardValue;
	private final Suit suit;
	private boolean faceUp;
	
	public Card(CardValue cardValue, Suit suit) {
		this.cardValue = cardValue;
		this.suit = suit;
	}
	
	public CardValue getCardValue() {
		return this.cardValue;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public void setFaceUp() {
		this.faceUp = true;
	}
	
	public boolean getFaceUp() {
		return this.faceUp;
	}
	
	@Override
	public String toString() {
		return this.cardValue.toString() + " OF " + this.suit.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cardValue == null) ? 0 : cardValue.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardValue != other.cardValue)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
}
