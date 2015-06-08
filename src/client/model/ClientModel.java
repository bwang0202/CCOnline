package client.model;

import hw03Common.defs.IMessageFields;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONValue;
/**
 * client's model
 *
 */
public class ClientModel {
	/**
	 * adapter to client's view
	 */
	private IModelToViewAdapter adapter;
	/**
	 * Store a mapping from ip address to its corresponding server socket.
	 */
	private HashMap<String, Socket> servers = new HashMap<String, Socket>();
	/**
	 * Store a mapping from ip address to its corresponding output streams.
	 */
	private HashMap<String, PrintWriter> outstreams = new HashMap<String, PrintWriter>();
	/**
	 * 
	 * @param adapter adapter to client's view
	 */
	public ClientModel(IModelToViewAdapter adapter) {
		this.adapter = adapter;
	}
	/**
	 * start the client model, currently no-op.
	 */
	public void start() {
	}
	/**
	 * Send language in the langFile to server at the input ipAddress. The language will use operator maps stored in
	 * the specified Json file.
	 * @param langFile containing the language.
	 * @param ipAddress ip address of the server
	 * @param opMapJsonFileName name of the JSon file to parse to get the operators' map.
	 */
	@SuppressWarnings("unchecked")
	public void parseLanguage(File langFile, String ipAddress, String opMapJsonFileName) {
		
			Socket socket = servers.get(ipAddress);
			try {
				//encode in JSON and send language info
				int c;
				PrintWriter out;
				String bnf = "";
				Map<String, Object> langInfo = new LinkedHashMap<String, Object>();
				if (outstreams.get(ipAddress) == null) {
					out = new PrintWriter(socket.getOutputStream(), true);
					outstreams.put(ipAddress, out);
				} else {
					out = outstreams.get(ipAddress);
				}
				try (BufferedReader bnfIn = new BufferedReader(new FileReader(langFile))) {
					while ((c = bnfIn.read()) != -1) {
						bnf += (char)c;
					}
				}
				langInfo.put(IMessageFields.ILanguageInfo.LANGUAGE_NAME, langFile.getName()); //use a better unique id
				langInfo.put(IMessageFields.ILanguageInfo.BNF_GRAMMAR_STR, bnf);
				Map<String, String> _opMap;
				BufferedReader bnfIn2 = new BufferedReader(new FileReader(new File(opMapJsonFileName)));
				String opmapstring = "";
				int chr;
				while ((chr = bnfIn2.read()) != -1) {
					opmapstring += (char)chr;
				}
				bnfIn2.close();
				try {
					_opMap = (Map<String, String>)JSONValue.parse(opmapstring);
				} catch (Exception e) {
					adapter.outputText(opMapJsonFileName + "is not valid JSON string");
					return;
				}
				langInfo.put(IMessageFields.ILanguageInfo.OPERATIONS_MAP, _opMap); 
				System.out.println(JSONValue.toJSONString(langInfo));
				adapter.outputText(JSONValue.toJSONString(langInfo));
				out.println(JSONValue.toJSONString(langInfo));
				//add in streams to accept and parse status msg info
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		
	}
	
	/**
	 * Send the program in programFile to server. The program will be using language specified in langName.
	 * @param programFile containing the program.
	 * @param langName  language name that this program uses.
	 * @param ipAddress ip address of server.
	 */
	public void parseProgram(File programFile, String langName, String ipAddress) {
		Socket socket = servers.get(ipAddress);
		try {
			//encode in JSON and send program for execution
			int c;
			PrintWriter out;
			String progText = "";
			Map<String, Object> progInfo = new LinkedHashMap<String, Object>();
			if (outstreams.get(ipAddress) == null) {
				out = new PrintWriter(socket.getOutputStream(), true);
				outstreams.put(ipAddress, out);
			} else {
				out = outstreams.get(ipAddress);
			}
			try (BufferedReader bnfIn = new BufferedReader(new FileReader(programFile))) {
				while ((c = bnfIn.read()) != -1) {
					progText += (char)c;
				}
			}
			progInfo.put(IMessageFields.ILanguageInfo.LANGUAGE_NAME, langName);
			progInfo.put(IMessageFields.IProgramInfo.PROGRAM_NAME, programFile.getName()); //use a better unique id
			progInfo.put(IMessageFields.IProgramInfo.PROGRAM_TEXT, progText);
			out.println(JSONValue.toJSONString(progInfo));
			//add in streams to accept and parse status msg info
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	
	}

	/**
	 * Connect to server
	 * @param hostName, server's host name, usually an ip address
	 * @param portNumber, port number that the server is listening on.
	 * @return Return server's ip address on success, return null otherwise.
	 */
	public String connectSocket(String hostName, int portNumber) {
		// TODO Auto-generated method stub
		Socket serverSocket;
		try {
			serverSocket = new Socket(hostName, portNumber);
			servers.put(hostName, serverSocket);
			(new OutputThread(serverSocket, adapter)).start();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return hostName;
	}

	/**
	 * Send init code to server and tell server to run the program of the specified language with this init code.
	 * @param langName language used in the program.
	 * @param progName program name to execute.
	 * @param init     initialization code
	 * @param ipAddress server's ip.
	 */
	public void executeProgram(String langName, String progName,
			String init, String ipAddress) {
		// TODO Auto-generated method stub
		
			Socket socket = servers.get(ipAddress);
			try {
				//encode in JSON and send program for execution
				PrintWriter out;
				Map<String, Object> execMsg = new LinkedHashMap<String, Object>();
				if (outstreams.get(ipAddress) == null) {
					out = new PrintWriter(socket.getOutputStream(), true);
					outstreams.put(ipAddress, out);
				} else {
					out = outstreams.get(ipAddress);
				}
				execMsg.put(IMessageFields.ILanguageInfo.LANGUAGE_NAME, langName);
				execMsg.put(IMessageFields.IProgramInfo.PROGRAM_NAME, progName); //use a better unique id
				execMsg.put(IMessageFields.IExecutionInfo.INIT_CODE, init+"\n");
				out.println(JSONValue.toJSONString(execMsg));
				// add in streams to accept and parse status msg info
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		
		
	}

	/**
	 * Check if a file with name filename exists. returns null if it does not exist.
	 * @param filename the name of the file to check
	 * @return FileWrapper for the file. Null if the file does not exist.
	 */
	public FileWrapper loadFile(String filename) {
		// TODO Auto-generated method stub
		File newfile = new File(filename);
		if (newfile.exists()) {
			return new FileWrapper(newfile);
		}
		adapter.outputText(filename + " does not exist.");
		return null;
	}
	

}
