package server.model;

/**
 * The server model to view adapter
 * @author Fabio-CSM
 *
 */
public interface IServerModelToViewAdapter {
	/**
	 * The method which prints in the console the given text.
	 * @param text The text to print
	 */
	public void outputText(String text);
	
	/**
	 * The method which add the client IP address to the list
	 * @param ipAddress The client IP address
	 */
	public void addConnectedClient(String ipAddress);
	
	/**
	 * The method which add the received language to the list
	 * @param langName  The received language name
	 */
	public void addLanguage(String langName);
	
	/**
	 * The method which add the program name to the list
	 * @param progName The received program name
	 */
	public void addProgram(String progName);
}
