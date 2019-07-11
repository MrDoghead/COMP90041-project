/*
 * *****************************************************************************
 * @author Dongnan Cao
 * Id: 970205
 * username: dongnanc
 * Description: This is the sixth class for COMP90041 projectB. 
 *              This class describes the advanced game.
 * Written: 05/05/2019
 * Last updated: 20/05/2019
 * ****************************************************************************
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class NimAdvancedGame extends NimGame{
	private int totalStone;    // the total number of stone
	private final int MAX_MOVE = 2;    // the upper bound in this case
	private boolean[] available;    // stone list
	private NimPlayer player1;
	private NimPlayer player2;
	
	public NimAdvancedGame()
	{
		super();
	}
	/*
	 * initialize the advanced game
	 * input: String[] input, Nimplayer[] playerList
	 */
	public void setNimGame(String[] input, NimPlayer[] playerList)
	{
		totalStone = Integer.parseInt(input[1]);
		// initialize the stone list
		available = new boolean[totalStone];
		for (int i = 0; i < totalStone; i++)
			available[i] = true;
		// find two players in playerList by user name
		for (int i = 0; i < playerList.length; i++)
		{
			if (playerList[i] != null)
			{
				if (playerList[i].getUsername().equals(input[2]))
					player1 = playerList[i];
				if (playerList[i].getUsername().equals(input[3]))
					player2 = playerList[i];
			}
		}
	}
	/*
	 * the main method to play the advanced game
	 * input: Scanner keyboard
	 */
	public void play(Scanner keyboard)
	{
		System.out.println("\nInitial stone count: " + totalStone);
		System.out.print("Stones display: ");
		displayAsterisk(available);
		System.out.println("Player 1: " + player1.getGivenName() +
				" " + player1.getFamilyName());
		System.out.println("Player 2: " + player2.getGivenName() +
				" " + player2.getFamilyName());
		System.out.println();
		
		int turn = 0;    // count turns
		String lastMove = "";    // Initialize the last move
		int position = 0;    // the position of the stone
		int number = 0;    // the number of stone removed
		while(getCurrentStone() > 0)
		{
			boolean validMove = false;
			do
			{
				System.out.print(getCurrentStone() + " stones left: ");
				displayAsterisk(available);
				try {
					if ((turn % 2) == 0)    // check whose turn; i%2==0 implies player1's turn
						lastMove = player1.advancedMove(available, lastMove);
					else
						lastMove = player2.advancedMove(available, lastMove);
					String[] move = lastMove.split(" ");
					position = Integer.parseInt(move[0]);
					number = Integer.parseInt(move[1]);
					// check if the input number is valid
					if (number == 1 && available[position - 1])
						validMove = true;
					else if (number == 2 && available[position - 1] && available[position])
						validMove = true;
					else
						throw new InvalidMoveException(getCurrentStone(),MAX_MOVE);
				}
				catch(InputMismatchException e)
				{
					System.out.println("Invalid move.\n");
					keyboard.nextLine();
				}
				catch(NumberFormatException e)
				{
					System.out.println("Invalid move.\n");
					keyboard.nextLine();
				}
				catch(InvalidMoveException e)
				{	
					System.out.println("Invalid move.\n");
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println("Invalid move.\n");
				}
			}while(!validMove);
			updateStone(position,number);
			turn++;
		}
		System.out.println("Game Over");
		// check who wins; i%2 !=0 implies player1 wins
		if ((turn % 2) != 0)
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
	 * this method is used to update the stone state
	 * input: int position, int number
	 */
	public void updateStone(int position, int number)
	{
		for(int i = 0; i < number; i++)
			available[position - 1 + i] = false;
	}
	/*
	 * this method is used to display stones by *
	 * input: boolean[] available
	 */
	public void  displayAsterisk(boolean[] available)
	{
		String asterisk = "";
		for(int i = 0; i < totalStone; i++)
		{
			if (available[i])
				asterisk = asterisk.concat("<" + (i + 1) + ",*> ");
			else
				asterisk = asterisk.concat("<" + (i + 1) + ",x> ");
		}
		System.out.println(asterisk.trim());
	}
	/*
	 * this method is used to get the current number of stone
	 * output: int count
	 */
	public int getCurrentStone()
	{
		int count = 0;
		for (int i = 0; i < available.length; i++)
			if (available[i])
				count++;
		return count;
	}

}
