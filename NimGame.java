/*
 * *****************************************************************************
 * @author Dongnan Cao
 * Id: 970205
 * username: dongnanc
 * Description: This is the third class for COMP90041 projectB.
 *              This class describes the process of playing the game.
 * Written: 15/04/2019
 * Last updated: 05/05/2019
 * ****************************************************************************
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class NimGame {
	private int currentStone;
	private int upperBound;
	private NimPlayer player1;
	private NimPlayer player2;
	
	// constructor
	public NimGame()
	{
		currentStone = 0;
		upperBound = 0;
		player1 = null;
		player2 = null;
	}
	/*
	 * this method allows user to set the Nim game
	 * input: a String array type command and the player list array
	 */
	public void setNimGame(String[] input, NimPlayer[] playerList)
	{
		currentStone = Integer.parseInt(input[1]);
		upperBound = Integer.parseInt(input[2]);
		// find two players in playerList by user name
		for (int i = 0; i < playerList.length; i++)
		{
			if (playerList[i] != null)
			{
				if (playerList[i].getUsername().equals(input[3]))
					player1 = playerList[i];
				if (playerList[i].getUsername().equals(input[4]))
					player2 = playerList[i];
			}
		}
	}
	/*
	 * this method describes the game process
	 * input: a Scanner object for user to input
	 */
	public void play(Scanner keyboard)
	{
		System.out.println("\nInitial stone count: " + currentStone);
		System.out.println("Maximum stone removal: " + upperBound);
		System.out.println("Player 1: " + player1.getGivenName() + 
				" " + player1.getFamilyName());
		System.out.println("Player 2: " + player2.getGivenName() + 
				" " + player2.getFamilyName());
		System.out.println();
		
		int i = 0;    // count turns
		while (currentStone > 0)    // main game loop
		{
			int removeNum = 0;    // initialize the number of stone to be removed
			boolean validMove = false;    //  use to check whether the input is valid
			do
			{
				displayAsterisk(currentStone);    // display stone number by "*"
				try {
					if ((i % 2) == 0)    // check whose turn; i%2==0 implies player1's turn
						removeNum = player1.removeStone(currentStone, upperBound);
					else
						removeNum = player2.removeStone(currentStone, upperBound);
					// check if the input number is valid
					if (removeNum < 1 || removeNum > Math.min(currentStone, upperBound))
						throw new InvalidMoveException(currentStone, upperBound);
					else
						validMove = true;
				}
				catch(InputMismatchException e)
				{
					System.out.println("Invalid move. You must remove between 1 and "
							+ Math.min(currentStone, upperBound) + " stones.\n");
					keyboard.nextLine();
				}
				catch(InvalidMoveException e)
				{
					System.out.println(e.getMessage());
				}
			}while(!validMove);
			currentStone = currentStone - removeNum;    // update current stone number
			i++;
		}
		System.out.println("Game Over");
		// check who wins; i%2==0 implies player1 wins
		if ((i % 2) == 0)
		{
			System.out.print(player1.getGivenName() 
					+ " " + player1.getFamilyName() + " wins!");
			player1.setGameWon(player1.getGameWon() + 1);
		}
		else
		{
			System.out.print(player2.getGivenName() 
					+ " " + player2.getFamilyName() + " wins!");
			player2.setGameWon(player2.getGameWon() + 1);
		}
		// update player's number of game that has played
		player1.setGamePlayed(player1.getGamePlayed() + 1);
		player2.setGamePlayed(player2.getGamePlayed() + 1);
		System.out.print("\n");
	}
	/*
	 * this method is used to print asterisk according to the current stone number
	 * input: the number of stone, int type
	 */
	public void displayAsterisk(int stoneNum)
	{
		String asterisk = stoneNum + " stones left: ";
		for (int k = 0; k < stoneNum; k++)
		{
			asterisk = asterisk.concat("* ");
		}
		System.out.println(asterisk.trim());    // delete blanks on both sides
	}
	

}
