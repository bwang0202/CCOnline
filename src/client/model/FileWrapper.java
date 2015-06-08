package client.model;

import java.io.File;
/**
 * A wrapper for File.
 * So the toString of a file will directly return File.getName();
 * @author Bojun
 *
 */
public class FileWrapper {
	/**
	 * The wrapped file
	 * 
	 */
	private File _file;
	/**
	 * The constructor for the file wrapper.
	 * @param file The file to be wrapped
	 */
	public FileWrapper(File file){
		_file = file;
	}
	/**
	 * Get the file into the wrapper.
	 * @return The file into the wrapper
	 */
	public File getFile(){
		return _file;
	}
	/**
	 * Override toString so that it returns name of the file, as in File.getName();
	 * @return the file name.
	 */
	public String toString(){
		return _file.getName();
	}
}
