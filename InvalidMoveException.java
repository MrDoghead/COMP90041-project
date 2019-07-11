/*
 * *****************************************************************************
 * @author Dongnan Cao
 * Id: 970205
 * username: dongnanc
 * Description: This is the eighth class for COMP90041 projectB.
 *              This class describes the invalid move exception.
 * Written: 15/05/2019
 * Last updated: 15/05/2019
 * ****************************************************************************
 */
public class InvalidMoveException extends Exception{
	int currentStone;
	int upperBound;
	
	public InvalidMoveException()
	{
		super();
	}
	public InvalidMoveException(int currentStone, int upperBound)
	{
		super();
		this.currentStone = currentStone;
		this.upperBound = upperBound;
	}
	/*
	 * override the getMessage method
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return "Invalid move. You must remove between 1 and " 
				+ Math.min(currentStone, upperBound) + " stones.\n";
	}

}
