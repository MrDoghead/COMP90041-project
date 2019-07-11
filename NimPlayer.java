/*
 * *****************************************************************************
 * @author Dongnan Cao
 * Id: 970205
 * username: dongnanc
 * Description: This is the second class for COMP90041 projectB. 
 *              This class describes player's information and actions.
 * Written: 05/05/2019
 * Last updated: 20/05/2019
 * ****************************************************************************
 */
import java.io.Serializable;
import java.util.Scanner;

public class NimPlayer implements Serializable{
	private String username;
	private String givenName;
	private String familyName;
	private int gamePlayed = 0;    // initialize the number of games played
	private int gameWon = 0;    // initialize the number of games won
	transient private Scanner keyboard;
	
	// define two constructors
	public NimPlayer()
	{
		username = null;
		givenName = null;
		familyName = null;
	}
	public NimPlayer(String username, String familyName, String givenName)
	{
		this.username = username;
		this.givenName = givenName;
		this.familyName = familyName;
	}
	// rewrite toString
	public String toString()
	{
		return username + "," + givenName + "," + familyName 
				+ "," + gamePlayed + " games," + gameWon + " wins\n";
	}
	/*
	 * this method is used to set the Scanner
	 * input: Scanner keyboard
	 */
	public void setScanner(Scanner keyboard)
	{
		this.keyboard = keyboard;
	}
	/*
	 * this method for player to remove stone by given number
	 * input: Scanner object
	 * output: the number of stone to be removed, int type
	 */
	public int removeStone(int currentStone, int upperBound)
	{
		System.out.println(givenName + "'s turn - remove how many?");
		int removeNum = keyboard.nextInt();
		System.out.println();
		keyboard.nextLine();
		return removeNum;
	}
	/*
	 * this method allows human players to remove stones in advanced games
	 * input: boolean[] available, String lastMove
	 * output: String move
	 */
	public String advancedMove(boolean[] available, String lastMove)
	{
		System.out.println(givenName + "'s turn - which to remove?");
		//System.out.println("human is playing");
		String move = keyboard.nextLine();
		System.out.println();
		return move;
	}
	/*
	 * this method is used to set a new user name
	 * input: a new user name, String type
	 */
	public void setUsername(String newUsername)
	{
		username = newUsername;
	}
	/*
	 * this method is used to get user name
	 * output: the user name, String type
	 */
	public String getUsername()
	{
		return username;
	}
	/*
	 * this method is used to set a new given name
	 * input: a new given name, String type
	 */
	public void setGivenName(String newGivenName)
	{
		givenName = newGivenName;
	}
	/*
	 * this method is used to get given name
	 * output: the given name, String type
	 */
	public String getGivenName()
	{
		return givenName;
	}
	/*
	 * this method is used to set a new family name
	 * input: a new family name, String type
	 */
	public void setFamilyName(String newFamilyName)
	{
		familyName = newFamilyName;
	}
	/*
	 * this method is used to get family name
	 * output: the family name, String type
	 */
	public String getFamilyName()
	{
		return familyName;
	}
	/*
	 * this method is used to set the number of games played
	 * input: the number of games played, int type
	 */
	public void setGamePlayed(int num)
	{
		gamePlayed = num;
	}
	/*
	 * this method is used to get the number of games played
	 * output: the number of game played, int type
	 */
	public int getGamePlayed()
	{
		return gamePlayed;
	}
	/*
	 * this method is used to set the number of games won
	 * input: the number of games won, int type
	 */
	public void setGameWon(int num)
	{
		gameWon = num;
	}
	/*
	 * this method is used to get the number of games won
	 * output: the number of game won, int type
	 */
	public int getGameWon()
	{
		return gameWon;
	}
	/*
	 * this method is used to get the percentage of game won
	 * percentage = gameWon * 100 / gamePlayed
	 * if the user haven't played any game, percentage = 0
	 * output: the percentage of game won, int type
	 */
	public int getPercentage()
	{
		if (gamePlayed != 0)
			return (int) Math.round(gameWon * 100.0 / gamePlayed);
		else
			return 0;
	}
}
