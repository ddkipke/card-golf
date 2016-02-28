package application;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import player.PlayerInterface;

public class Scoreboard {

	public class PlayerScore {
		private final PlayerInterface player;
		private int totalScore;
		
		public PlayerScore(PlayerInterface player, int totalScore) {
			this.player = player;
			this.totalScore = totalScore;
		}
		
		public int getTotalScore() {
			return this.totalScore;
		}
		
		public PlayerInterface getPlayer() {
			return this.player;
		}
	}
	
	private Comparator<PlayerScore> comparator = new Comparator<Scoreboard.PlayerScore>() {

		@Override
		public int compare(PlayerScore o1, PlayerScore o2) {
			if (o1.totalScore < o2.totalScore) {
				return -1;
			} else if (o1.totalScore == o2.totalScore) {
				return 0;
			}
			return 1;
		}
		
	};
	
	private final List<PlayerInterface> players;
	private PlayerScore[] playerScores;

	public Scoreboard(List<PlayerInterface> players) {
		this.players = players;
		this.playerScores = new PlayerScore[players.size()];
		for (int i = 0; i < players.size(); i++) {
			this.playerScores[i] = new PlayerScore(players.get(i), 0);
		}
	}

	public List<PlayerInterface> getPlayers() {
		return this.players;
	}

	public PlayerScore[] getOrderedPlayerScores() {
		Arrays.sort(playerScores, comparator);
		return this.playerScores;
	}

	public void incrementTotalScores() {
		for (PlayerScore playerScore : this.playerScores) {
			playerScore.totalScore += playerScore.player.getScoreForCurrentRound();
		}
	}

}
