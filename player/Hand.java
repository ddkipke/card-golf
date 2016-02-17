package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deck.Card;
import deck.Card.CardValue;

public class Hand {

	public enum Position {
		NORTH,
		EAST,
		SOUTH,
		WEST
	};
	
	private Map<Position, Card> cards = new HashMap<Position, Card>();
	
	public void setCard(Position position, Card card) {
		this.cards.put(position, card);
	}

	public Card getCard(Position position) {
		return this.cards.get(position);
	}

	private class ScoreTabulator {
		private Card.CardValue cardValue;
		private List<Integer> indicesOfMatches;
		
		private ScoreTabulator(Card.CardValue cardValue) {
			this.cardValue = cardValue;
			this.indicesOfMatches = new ArrayList<Integer>();
		}
		
		private int getScore() {
			if (this.indicesOfMatches.size() > 0) {
				return 0;
			}
			return cardValue.getNumberValue();
		}
	}
	
	public int getScore() {
		
		Collection<Card> allCards = this.cards.values();

		ArrayList<ScoreTabulator> scoreTabs = new ArrayList<ScoreTabulator>();
		for (Card card : allCards) {
			scoreTabs.add(new ScoreTabulator(card.getCardValue()));
		}
		
		evaluateThrees(scoreTabs);
		
		calculateMatches(scoreTabs);

		return calculateScores(scoreTabs);
	}

	private void evaluateThrees(ArrayList<ScoreTabulator> scoreTabs) {
		for (int i = 0; i < scoreTabs.size(); i++) {
			calculateMatches(scoreTabs);
			if (scoreTabs.get(i).cardValue.equals(Card.CardValue.THREE)) {
				scoreTabs.get(i).cardValue = getHighestValueInHand(scoreTabs);
			}
		}
	}

	private CardValue getHighestValueInHand(ArrayList<ScoreTabulator> scoreTabs) {
		ScoreTabulator worstCard = scoreTabs.get(0);
		for (int j = 0; j < scoreTabs.size(); j++) {
			if (scoreTabs.get(j).getScore() >= worstCard.getScore() 
					&& !scoreTabs.get(j).cardValue.equals(Card.CardValue.THREE)) {
				worstCard = scoreTabs.get(j);
			}
		}
		return worstCard.cardValue;
	}

	private int calculateScores(ArrayList<ScoreTabulator> scoreTabs) {
		int tripOrQuadBonus = 0;
		int sumOfScores = 0;
		for (ScoreTabulator scoreTabulator : scoreTabs) {
			if (scoreTabulator.indicesOfMatches.size() == 2) {
				tripOrQuadBonus = -1;
			} else if(scoreTabulator.indicesOfMatches.size() == 3) {
				tripOrQuadBonus = -5;
			}
			sumOfScores += scoreTabulator.getScore();
		}
		
		return sumOfScores + tripOrQuadBonus;
	}

	private void calculateMatches(ArrayList<ScoreTabulator> scoreTabs) {
		for (ScoreTabulator scoreTabulator : scoreTabs) {
			scoreTabulator.indicesOfMatches.clear();
		}
		
		for (int i = 0; i < scoreTabs.size(); i++) {
			for (int j = 0; j < scoreTabs.size(); j++) {
				if (j != i && scoreTabs.get(i).cardValue.equals(scoreTabs.get(j).cardValue)) {
					scoreTabs.get(i).indicesOfMatches.add(j);
				}
			}
		}
	}

}
