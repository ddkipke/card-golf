package application;

import java.util.ArrayList;

import org.mockito.Mockito;

import application.Scoreboard.PlayerScore;
import junit.framework.TestCase;
import player.PlayerInterface;

public class ScoreboardTest extends TestCase {

	private ArrayList<PlayerInterface> players;
	private Scoreboard scoreboard;
	private PlayerInterface player1;
	private PlayerInterface player2;
	private PlayerInterface player3;
	private PlayerInterface player4;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		players = new ArrayList<PlayerInterface>();
		
		player1 = Mockito.mock(PlayerInterface.class);
		player2 = Mockito.mock(PlayerInterface.class);
		player3 = Mockito.mock(PlayerInterface.class);
		player4 = Mockito.mock(PlayerInterface.class);
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		scoreboard = new Scoreboard(players);
	}
	
	public void testGetter() throws Exception {
		assertSame(players, scoreboard.getPlayers());
	}
	
	public void testGetOrderedScores() throws Exception {
		PlayerScore[] scoreList = scoreboard.getOrderedPlayerScores();
		assertEquals(4, scoreList.length);

		checkPlayerScore(player1, 0, 0, scoreList);
		checkPlayerScore(player2, 1, 0, scoreList);
		checkPlayerScore(player3, 2, 0, scoreList);
		checkPlayerScore(player4, 3, 0, scoreList);
		
		Mockito.when(player1.getScoreForCurrentRound()).thenReturn(10);
		Mockito.when(player2.getScoreForCurrentRound()).thenReturn(10);
		Mockito.when(player3.getScoreForCurrentRound()).thenReturn(5);
		Mockito.when(player4.getScoreForCurrentRound()).thenReturn(0);

		scoreboard.incrementTotalScores();
		
		scoreList = scoreboard.getOrderedPlayerScores();
		
		checkPlayerScore(player4, 0, 0, scoreList);
		checkPlayerScore(player3, 1, 5, scoreList);
		checkPlayerScore(player1, 2, 10, scoreList);
		checkPlayerScore(player2, 3, 10, scoreList);
		
		Mockito.when(player1.getScoreForCurrentRound()).thenReturn(2);
		Mockito.when(player2.getScoreForCurrentRound()).thenReturn(1);
		Mockito.when(player3.getScoreForCurrentRound()).thenReturn(-8);
		Mockito.when(player4.getScoreForCurrentRound()).thenReturn(0);
		
		scoreboard.incrementTotalScores();
		
		scoreList = scoreboard.getOrderedPlayerScores();
		
		checkPlayerScore(player3, 0, -3, scoreList);
		checkPlayerScore(player4, 1, 0, scoreList);
		checkPlayerScore(player2, 2, 11, scoreList);
		checkPlayerScore(player1, 3, 12, scoreList);
	}

	private void checkPlayerScore(PlayerInterface player, int index, int score,
			PlayerScore[] scoreList) {
		assertSame(player, scoreList[index].getPlayer());
		assertEquals(score, scoreList[index].getTotalScore());
	}
}
