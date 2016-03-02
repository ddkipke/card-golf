package application;

import application.Scoreboard.PlayerScore;
import player.Hand;
import player.PlayerInterface;
import player.Hand.Position;

public class ConsoleOutput {

	public static void printFinalScores(Scoreboard scoreboard) {
		System.out.println("***FINAL SCORES***");
		
		PlayerScore[] scores = scoreboard.getOrderedPlayerScores();
		for (PlayerScore score : scores) {
			System.out.println("Player: " + score.getPlayer().getName());
			System.out.println("FinalScore: " + score.getTotalScore());
			System.out.println();
		}
	}

	public static void printRoundScores(PlayerScore[] scores) {
		for (PlayerScore score : scores) {
			
			PlayerInterface player = score.getPlayer();
			Hand hand = player.getHand();
			
			System.out.println("Player: " + player.getName());
			System.out.println("Cards:");
			System.out.println(hand.getCard(Position.NORTH));
			System.out.println(hand.getCard(Position.EAST));
			System.out.println(hand.getCard(Position.SOUTH));
			System.out.println(hand.getCard(Position.WEST));
			System.out.println("Score: " + player.getScoreForCurrentRound());
			System.out.println("Total Score: " + score.getTotalScore());
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
	}

	
}
