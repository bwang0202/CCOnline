package client.view;

import java.io.File;

import client.model.FileWrapper;

/**
 * Adapter between the client view and the model
 * @author Fabio-CSM
 *
 */
public interface IViewToModelAdapterNew {

	/**
	 * The method which send a language to be parsed by the server.
	 * @param file The file which contains the language.
	 * @param ipAddress The server ip address.
	 * @param opMapFileName The operation map of the language
	 */
	void parseLanguage(File file, String ipAddress, String opMapFileName);
	
	/**
	 * The method which send a program to be parsed by the server.
	 * @param file The file which contains the program.
	 * @param langName The language name which the server must use to do the parse.
	 * @param ipAddress The server IP address.
	 */
	void parseProgram(File file, String langName, String ipAddress);

	/**
	 * The method which send the initialization code to be run by the server given a language name and program name.
	 * @param langName The language name which the initialization code belongs. 
	 * @param progName The program name which the initialization code belongs.
	 * @param init The initialization code.
	 * @param ipAddress The server IP address
	 */
	void executeProgram(String langName, String progName, String init, String ipAddress);

	/**
	 * The method which connect the client to the server.
	 * @param hostName  The server IP address.
	 * @param portNumber The port which the server is listening.
	 * @return The IP address of the server.
	 */
	String connectSocket(String hostName, int portNumber);
	
	/**
	 * The method which loads the language file into the application.
	 * @param filename The name of the file to be loaded.
	 * @return The file wrapper which contains the file and prints the file name.
	 */
	FileWrapper loadLanguage(String filename);
	
	/**
	 * The method which loads the program into the application.
	 * @param filename The name of the file to be loaded.
	 * @return The file wrapper which contains the file and prints the file name.
	 */
	FileWrapper loadProgram(String filename);
	
	/**
	 * The method which loads the operation map into the application.
	 * @param filename The name of the file to be loaded.
	 * @return The file wrapper which contains the file and prints the file name.
	 */
	FileWrapper loadOpMap(String filename);
	
}
