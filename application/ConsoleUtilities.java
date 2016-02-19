package application;

import java.util.List;
import java.util.Scanner;

import player.Hand;
import player.Hand.Position;

public class ConsoleUtilities {
	
	private Scanner scanner;

	public ConsoleUtilities(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public boolean yesNoQuestion(String question) {
		System.out.println(question + "(y/n)");
		String response = this.scanner.next();
		
		if (response.equals("y")) {
			return true;
		} else if (response.equals("n")) {
			return false;
		} else {
			return yesNoQuestion(question);
		}
	}
	
	public String getUnlockedPositionsAsFormattedString(Hand hand) {
		String formattedString = "";
		for (Position position : hand.getUnlockedPositions()) {
			formattedString += "\n" + position.toString();
		}
		return formattedString;
	}
	
	public Hand.Position getPositionFromInput(Hand hand) {
		List<Position> unlockedPositions = hand.getUnlockedPositions();
		
		switch (this.scanner.next()) {
		case "n":
			if (unlockedPositions.contains(Hand.Position.NORTH)) {
				return Hand.Position.NORTH;
			}
			break;
		case "e":
			if (unlockedPositions.contains(Hand.Position.EAST)) {
				return Hand.Position.EAST;
			}
			break;
		case "s":
			if (unlockedPositions.contains(Hand.Position.SOUTH)) {
				return Hand.Position.SOUTH;
			}
			break;
		case "w":
			if (unlockedPositions.contains(Hand.Position.WEST)) {
				return Hand.Position.WEST;
			}
			break;
		default:
			break;
		}
		System.out.println("That position is locked, try again: ");
		return this.getPositionFromInput(hand);
	}
}
