/*
 * *****************************************************************************
 * @author Dongnan Cao
 * Id: 970205
 * username: dongnanc
 * Description: This is the fourth class for COMP90041 projectC. 
 *              This class is the main class, describing the system of the game.
 * Written: 13/05/2019
 * Last updated: 20/05/2019
 * ****************************************************************************
	NimAIPlayer.java
	
	This class is provided as a skeleton code for the tasks of 
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
*/

public class NimAIPlayer extends NimPlayer implements Testable {
	// you may further extend a class or implement an interface
	// to accomplish the tasks.	

	public NimAIPlayer()
	{
//		super("AI", "AI", "AI");
	}
	public NimAIPlayer(String username, String familyName, String givenName)
	{
		super(username, familyName, givenName);
	}
	/*
	 * this method allows AI player to remove stones in advanced game
	 * input: boolean[] available, String lastMove
	 * output: String move
	 */
	public String advancedMove(boolean[] available, String lastMove) 
	{
		// the implementation of the victory
		// guaranteed strategy designed by you
		System.out.println(getGivenName() + "'s turn - which to remove?");
		String move = "";
		
		// count the remaining stone number
		int count = 0;
		for (int i = 0; i <  available.length; i++)
			if (available[i])
				count++;
		
		if (lastMove.equals(""))    // if AI plays first
		{
			if ((available.length % 2) == 0)    // if total number is even
				move = available.length / 2 + " 2";
			else
				move = (available.length / 2 + 1) + " 1";
		}
		else
		{
			if (count == 1)    // only one stone left
			{
				for(int i = 0; i < available.length; i++)
					if (available[i])
						move = (i + 1) + " 1";
			}
			else if (count ==2)    // only two adjacent stones left
			{
				for (int j = 0; j < available.length; j++)
					if (available[j] && available[j + 1])
						move = (j + 1) + " 2";
			}
			else
			{
				String[] last = lastMove.split(" ");
				int pos = Integer.parseInt(last[0]);
				int num = Integer.parseInt(last[1]);
				if (num == 2)
				{
					if (available[available.length - pos])
						move = (available.length - pos) + " " + num;
					else
						for(int k = 0; k < available.length; k++)
							if (available[k])
							{
								move = (k + 1) + " 1";
								break;
							}
				}
				else if (num == 1)
				{ 
					if (available[available.length - pos])
						move = (available.length - pos + 1) + " " + num;
					else
						for(int k = 0; k < available.length; k++)
							if (available[k])
							{
								move = (k + 1) + " 1";
								break;
							}
				}
			}
		}
		System.out.println();
		return move;
	}
	/*
	 * this method allows AI player to remove stones in nimgame
	 * input: int currentStone, int upperBound
	 * output: int remove
	 */
	public int removeStone(int currentStone, int upperBound)
	{
		System.out.println(getGivenName() + "'s turn - remove how many?");
		for (int i = 1; i <= currentStone && i <= upperBound; i++)
			if((currentStone - i - 1) % (upperBound + 1) == 0)
			{
				System.out.println();
				return i;
			}
		int max = Math.min(currentStone, upperBound);
		int remove = (int)(Math.random() * max) + 1;
		System.out.println();
		return remove;
	}
}
