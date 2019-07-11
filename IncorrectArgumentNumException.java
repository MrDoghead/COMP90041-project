/*
 * *****************************************************************************
 * @author Dongnan Cao
 * Id: 970205
 * username: dongnanc
 * Description: This is the seventh class for COMP90041 projectB.
 *              This class describes the exception of incorrect number of arguments.
 * Written: 15/05/2019
 * Last updated: 15/05/2019
 * ****************************************************************************
 */
public class IncorrectArgumentNumException extends Exception{
	public IncorrectArgumentNumException()
	{
		super();
	}
	/*
	 * override the getMessage method
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage()
	{
		return "Incorrect number of arguments supplied to command.";
	}

}
