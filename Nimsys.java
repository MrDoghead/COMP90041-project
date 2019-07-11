/*
 * *****************************************************************************
 * @author Dongnan Cao
 * Id: 970205
 * username: dongnanc
 * Description: This is the first class for COMP90041 projectC. 
 *              This class is the main class, describing the system of the game.
 * Written: 13/05/2019
 * Last updated: 13/05/2019
 * ****************************************************************************
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Nimsys {
	private Scanner keyboard = new Scanner(System.in);
	private final int MAX_PLAYER = 100;    // maximum players
	private NimPlayer[] playerList = new NimPlayer[MAX_PLAYER];
	private boolean idleState = true;    // represent whether the state is idle or game
	private NimGame nimGame;
	
	public static void main(String[] args) {
		Nimsys nimSystem = new Nimsys();
		nimSystem.game();
	}
	/*
	 * this method is used to control the game system
	 * it has two states, idle and game
	 */
	public void game()
	{
		// load file into system
		try
		{
			ObjectInputStream inputStream = 
					new ObjectInputStream(new FileInputStream("players.dat"));
			playerList = (NimPlayer[]) inputStream.readObject();
			inputStream.close();
		}
		catch(IOException e)
		{
			//System.out.println("Problems with input from player.dat.");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Problems with file input.");
		}
		System.out.println("Welcome to Nim");
		while (idleState)
		{
			System.out.print("\n$");
			StringTokenizer inputLine = new StringTokenizer(keyboard.nextLine(), ", ");
			
			// use input[] to store input, at most 5 elements
			final int MAX_INPUT = 5;
			String[] input = new String[MAX_INPUT];    
			for (int i = 0; i < MAX_INPUT; i++)
				if (inputLine.hasMoreElements()) 
					input[i] = inputLine.nextToken();
			String command = "";
			try
			{
				if (isCommand(input[0]))
					command = input[0];
				else 
					throw new IllegalArgumentException();
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("'" + input[0] + "' is not a valid command.");
			}
			// check the command type
			if (command.equals("addplayer") || command.equals("addaiplayer"))
			{
				try {
					if (input[3] != null)
					{
						if (usernameExist(input[1]))    // check whether this user already exists
						{
							System.out.println("The player already exists.");
						}
						else 
							addPlayer(input[1], input[2], input[3],command.equals("addplayer"));
					}
					else
						throw new IncorrectArgumentNumException();
				}
				catch(IncorrectArgumentNumException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (command.equals("removeplayer"))
			{
				if (input[1] == null)
				{
					System.out.println("Are you sure you want to remove all players? (y/n)");
					String choose = keyboard.nextLine();    // choose whether to remove all
					if (choose.equals("y"))
					{
						for (int i = 0; i < playerList.length; i++)
							if (playerList[i] != null)
								removePlayer(playerList[i].getUsername());
					}
				}
				else if (usernameExist(input[1]))    // check whether this user exists
					removePlayer(input[1]);
				else 
					System.out.println("The player does not exist.");
			}
			else if (command.equals("editplayer"))
			{
				try
				{
					if (input[1] != null)
						if (usernameExist(input[1]))    // check whether this user exists
							editPlayer(input[1], input[2], input[3]);
						else
							System.out.println("The player does not exist.");
					else
						throw new IncorrectArgumentNumException();
				}
				catch(IncorrectArgumentNumException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (command.equals("resetstats"))
			{
				if (input[1] == null)
				{
					System.out.println("Are you sure you want to reset all player statistics? (y/n)");
					String choose = keyboard.nextLine();    // choose whether to reset all
					if (choose.equals("y"))
					{
						for (int i = 0; i < playerList.length; i++)
							if (playerList[i] != null)
								resetStats(playerList[i].getUsername());
					}
				}
				else if (usernameExist(input[0]))    // check whether this user exists
					resetStats(input[0]);
				else
					System.out.println("The player does not exist.");
			}
			else if (command.equals("displayplayer"))
			{
				if (input[1] == null)    // display all if not specify
				{
					// get current players' user names
					String[] username = new String[getPlayerNum()];
					int t = 0;
					for (int i = 0; i < playerList.length; i++)
						if (playerList[i] != null)
						{
							username[t] = playerList[i].getUsername();
							t++;
						}
					Arrays.sort(username);    // sort user name
					for (int i = 0; i < username.length; i++)
						displayPlayer(username[i]);
				}
				else if (usernameExist(input[1]))    // check whether this user exists
					displayPlayer(input[1]);
				else
					System.out.println("The player does not exist.");
			}
			else if (command.equals("rankings"))
				rankings(input[1]);
			else if (command.equals("startgame"))
			{
				try
				{
					if (input[4] != null)
						// check both players exist
						if (usernameExist(input[3]) && usernameExist(input[4]))
							startGame(input);
						else
							System.out.println("One of the players does not exist.");
					else
						throw new IncorrectArgumentNumException();
				}
				catch(IncorrectArgumentNumException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (command.equals("startadvancedgame"))
			{
				try
				{
					if (input[3] != null && input[4] == null)
						// check both players exist
						if (usernameExist(input[2]) && usernameExist(input[3]))
							startAdvancedgame(input);
						else
							System.out.println("One of the players does not exist.");
					else
						throw new IncorrectArgumentNumException();
				}
				catch(IncorrectArgumentNumException e)
				{
					System.out.println(e.getMessage());
				}
			}
			else if (command.equals("exit"))
			{
				System.out.println();
				try 
				{
					ObjectOutputStream outputStream = 
							new ObjectOutputStream(new FileOutputStream("players.dat"));
					outputStream.writeObject(playerList);
					outputStream.close();
				}
				catch(IOException e) 
				{
					e.printStackTrace();
					System.out.println("Problems with file output.");
				}
				System.exit(0);
			}
		}
	}
	/*
	 * this method allows new players to be added to the game
	 * input: a user name, a family name, and a given name, all String type
	 */
	public void addPlayer(String username, String familyName, String givenName, boolean isHuman)
	{
		for (int i = 0; i < playerList.length; i++)
		{
			if (playerList[i] == null)
			{
				if (isHuman)
				{
					playerList[i] = new NimPlayer(username, familyName, givenName);
					playerList[i].setScanner(keyboard);
				}
				else
					playerList[i] = new NimAIPlayer(username, familyName, givenName);
				// sort players by user names alphabetically
				playerList = sortByUsername(playerList);    
				break;
			}
		}
	}
	/*
	 * this method allows player to be removed from the game
	 * input: the player's user name, String type
	 */
	public void removePlayer(String username)
	{
		for (int i = 0; i < playerList.length; i++)
			if (playerList[i] != null && playerList[i].getUsername().equals(username))
			{
				playerList[i] = null;  
				break;
			}
	}
	/*
	 * this method allows player details to be edited
	 * input: the user name, a new family name, and a new given name, all String type
	 */
	public void editPlayer(String username, String newFamilyName, String newGivenName)
	{
		for (int i = 0; i < playerList.length; i++)
		{
			if (playerList[i].getUsername().equals(username))
			{
				playerList[i].setFamilyName(newFamilyName);
				playerList[i].setGivenName(newGivenName);
				break;
			}
		}
	}
	/*
	 * this method allows player statistic to be reset
	 * input: the user name, String type
	 */
	public void resetStats(String username)
	{
		for (int i = 0; i < playerList.length; i++)
		{
			if (playerList[i].getUsername().equals(username))
			{
				playerList[i].setGamePlayed(0);
				playerList[i].setGameWon(0);
				break;
			}
		}
	}
	/*
	 * this method is used to display player information
	 * input: the user name, String name
	 */
	public void displayPlayer(String username)
	{
		for (int i = 0; i < playerList.length; i++)
			if (playerList[i] != null && playerList[i].getUsername().equals(username))
			{
				System.out.print(playerList[i].toString());
				break;
			}
	}
	/*
	 * this method used to display player rankings
	 * display 10 player rankings according to the percentage of wining ratio
	 * input: an order, could be "desc", "asc" or null, String type
	 */
	public void rankings(String order)
	{
		// create an array to store percentages of all existent players
		int[] percentage = new int[getPlayerNum()];
		int t = 0;    // initialize an index for percentage[]
		for (int j = 0; j < playerList.length; j++)
			if (playerList[j] != null)
			{
				percentage[t] = playerList[j].getPercentage();
				t++;
			}
		Arrays.sort(percentage);    // sort by percentage
		// create an array to store at most 10 ranks that will be displayed
		final int MAX_RANK = 10;
		int[] rank = new int[MAX_RANK];    
		if (order == null || order.equals("desc"))    // display in descent order
			for (int i = 0; i < Math.min(rank.length, percentage.length); i++)
				rank[i] = percentage[percentage.length - 1 - i];
		else    // display in ascent order
			for (int i = 0; i < Math.min(rank.length, percentage.length); i++)
				rank[i] = percentage[i];
		// create an array to store ten user names that will be displayed
		String[] rankUsername = new String[MAX_RANK];
		for (int i = 0; i < Math.min(rank.length, percentage.length); i++)
		{
			for (int j = 0; j< playerList.length; j++)
			{
				if (playerList[j] != null && rank[i] == playerList[j].getPercentage() 
						&& !nameExist(rankUsername, playerList[j].getUsername()))
				{
					rankUsername[i] = playerList[j].getUsername();
					System.out.printf("%-4s | %02d games | %s %s\n", 
							rank[i] + "%", playerList[j].getGamePlayed(),
							playerList[j].getGivenName(), playerList[j].getFamilyName());
					break;
				}
			}
		}
	}
	/*
	 * this method is used to check whether a name is in a nameList
	 * input: a String array nameList and a String name
	 * output: return true if the name exists in nameList; return false otherwise
	 */
	public boolean nameExist(String[] nameList, String name)
	{
		for (int i = 0; i < nameList.length; i++)
			if (nameList[i] != null && name == nameList[i])
				return true;
		return false;
	}
	/*
	 * this method is used to start the game
	 * input: a String array input which contains 5 elements
	 */
	public void startGame(String[] input)
	{
		nimGame = new NimGame();
		nimGame.setNimGame(input, playerList);
		nimGame.play(keyboard);
	}
	/*
	 * this method is used to start the advanced game
	 * input: a String array input which contains 4 elements
	 */
	public void startAdvancedgame(String[] input)
	{
		nimGame = new NimAdvancedGame();
		nimGame.setNimGame(input, playerList);
		nimGame.play(keyboard);
	}
	/*
	 * this method is used to check whether user name already exists
	 * input: the user name, String type
	 * output: return true if already exists; return false otherwise
	 */
	public boolean usernameExist(String username)
	{
		if (getPlayerNum() == 0)
			return false;
		else
			for (int i = 0; i < playerList.length; i++)
				if (playerList[i] != null && playerList[i].getUsername().equals(username))
					return true;
		return false;
	}
	/*
	 * this method is used to sort playerList by user name in alphabetical order
	 * input: the array playerList[]
	 * output: an new array that has been sorted by user name
	 */
	public NimPlayer[] sortByUsername(NimPlayer[] playerList)
	{
		String username[] = new String[getPlayerNum()];
		NimPlayer[] sortedPlayerList = new NimPlayer[playerList.length];
		int t = 0;
		if (username.length != 0)
		{
			for (int i = 0; i < playerList.length; i++)
				if (playerList[i] != null)
				{
					username[t] = playerList[i].getUsername();
					t++;
				}
			Arrays.sort(username);    // sort by user name
			for (int i = 0; i < username.length; i++)
				for (int j = 0; j < playerList.length; j++)
					if (playerList[j] != null && 
					username[i].equals(playerList[j].getUsername()))
						sortedPlayerList[i] = playerList[j];
			return sortedPlayerList;
		}
		else
			return playerList;
	}
	/*
	 * this method is used to get the number of existent players
	 * output: the number of existent players
	 */
	public int getPlayerNum()
	{
		int count = 0;    // count players number
		for (int i = 0; i < playerList.length; i++)
			if (playerList[i] != null)
				count += 1;
		return count;
	}
	/*
	 * this method is used to check valid command
	 * input: a String
	 * output: true or false
	 */
	public boolean isCommand(String s)
	{
		return (s.equals("addplayer") || s.equals("addaiplayer") || s.equals("removeplayer") ||
				s.equals("editplayer") || s.equals("resetstats") || s.equals("displayplayer") ||
				s.equals("rankings") || s.equals("startgame") || s.equals("startadvancedgame") || 
				s.equals("exit"));
	}
}
