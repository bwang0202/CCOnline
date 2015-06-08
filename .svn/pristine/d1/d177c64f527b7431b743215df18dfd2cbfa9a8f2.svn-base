package server;

import hw03Common.defs.IMessageFields;
import hw03Common.defs.WellKnownOperator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import server.model.IServerModelToViewAdapter;
import client.model.exec.ExecValue;
/**
 * Thread class for handling multi user connecting at the same time.
 * @author Bojun
 *
 */
public class MultiUserThread extends Thread {
	/**
	 * socket of server.
	 */
	private Socket _socket = null;
	/**
	 * adapter to view of server's mvc
	 */
	private IServerModelToViewAdapter adapter;
	/**
	 * model whose language is parsed is stored here, indexed by lang_name
	 */
	private HashMap<String, LanguageParsingUnit> parsedLanguage = new HashMap<>();
	/**
	 * model whose both program and program's language are parsed is stored here, indexed by lang_name + prog_name;
	 */
	private HashMap<String, ProgramParsingUnit> parsedProgram = new HashMap<>();

	/**
	 * Build a new thread for each connected client.
	 * @param socket client socket
	 * @param adapter adapter to server view.
	 */
    public MultiUserThread(Socket socket, IServerModelToViewAdapter adapter) {
        super("MultiUserThread");
        this.adapter = adapter;
        this.adapter.outputText("building new thread");
        _socket = socket;
    }
    /**
     * repeated read lines from client and parse it as JSON objects.
     */
    public void run() {

        try (
            PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    _socket.getInputStream()));
        ) {
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
				processLine(inputLine, out);
			}
            _socket.close();
        } catch (IOException e) {
        	e.printStackTrace();
        	try {
				_socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
    /**
     * 
     * @param inputLine the input line to parse into an JSON object.
     * @param out output stream to write result.
     */
    public void processLine(String inputLine, PrintWriter out) {
       
    	//JSon send the whole json object in one line
    	JSONObject map = (JSONObject)(JSONValue.parse(inputLine));
    
    	String bnf = (String) map.get(IMessageFields.ILanguageInfo.BNF_GRAMMAR_STR);
		if (bnf != null) {  //lang_info object
//                	adapter.outputText("op_map in json object : " + map.get("op_map"));
    		processLanguageMessage(out, map, bnf);
    		
    	} else {
			String program = (String) map.get(IMessageFields.IProgramInfo.PROGRAM_TEXT);
			if (program != null) { //prog_info object
				processProgramMessage(out, map, program);
				
			} else {
				String initCode = (String) map.get(IMessageFields.IExecutionInfo.INIT_CODE);
				if (initCode != null) { //exec msg
					processExecutionMessage(out, map, initCode);
				} else {
					sendStatusMsg(out, 1, "JSON message received did not contain any field unique to a particular message type", map);
					adapter.outputText("JSON object wrong!");
				}
			}
      
        }
    }
    /**
     * Process the execution message.
     * @param out output stream to write result to.
     * @param map the original received JSON object.
     * @param initCode a string of init code.
     */
	private void processExecutionMessage(PrintWriter out, JSONObject map,
			String initCode) {
		String langName = (String) map.get(IMessageFields.ILanguageInfo.LANGUAGE_NAME);
		if (langName == null) {
			sendStatusMsg(out, 001, "Missing language name for execution", map);
		}
		StringBuilder sb = new StringBuilder();
		ExecValue initExecutor;
		if (parsedLanguage.get(langName) == null) {
			sendStatusMsg(out, 302, "Could not find language called " + langName, map);
			return;
		}
		String progName = (String) map.get(IMessageFields.IProgramInfo.PROGRAM_NAME);
		ProgramParsingUnit _model = parsedProgram.get(langName + progName);
		if (_model == null) {
			sendStatusMsg(out, 303, "Could not find program " + progName + " in language " + langName, map);
			return;
		}
		try {
			initExecutor = _model.parseProgram(new StringReader(initCode));
		} catch (Exception e) {
			e.printStackTrace();
			sendStatusMsg(out, 301, e.toString(), map);
			return;
		}
		sendStatusMsg(out, 300, "init OK\n", map);
		try {
			_model.executeProgram(initExecutor, sb); //init code , and op_map, and sb
		} catch (Exception e) {
			e.printStackTrace();
			sendStatusMsg(out, 401, e.toString(), map);
			return;
		}
		sendStatusMsg(out, 400, sb.toString(), map);
	}
	/**
	 * process program message.
	 * @param out output stream to write result to.
	 * @param map the original received JSON object.
	 * @param program name of the program.
	 */
	private void processProgramMessage(PrintWriter out, JSONObject map,
			String program) {
		String programName = (String)map.get(IMessageFields.IProgramInfo.PROGRAM_NAME);
		String langName = (String)map.get(IMessageFields.ILanguageInfo.LANGUAGE_NAME);
		if (langName == null) {
			sendStatusMsg(out, 001, "Missing language name for program", map);
			return;
		}
		if (programName == null) {
			sendStatusMsg(out, 001, "Missing program name for program", map);
			return;
		}
		LanguageParsingUnit langUnit = parsedLanguage.get(langName);
		if (langUnit == null) {
			sendStatusMsg(out, 202, langName + " language does not exist\n", map);
			return;
		}
		ProgramParsingUnit _model;
		String programText = (String)program;
		try {
			adapter.outputText(programText);
		} catch (Exception e) {
			e.printStackTrace();
			sendStatusMsg(out, 202, e.toString(), map);
			return;
		}
		try {
			_model = new ProgramParsingUnit(langUnit, new StringReader(programText));
		} catch (Exception e) {
			e.printStackTrace();
			sendStatusMsg(out, 201, e.toString(), map);
			return;
		}
		parsedProgram.put(langName+programName, _model);
		sendStatusMsg(out, 200, "parse program ok\n", map);
		adapter.addProgram(programName);
	}
	/**
	 * process language message.
	 * @param out output stream to write result to.
	 * @param map the original received JSON object.
	 * @param bnf string representation of bnf.
	 */
	private void processLanguageMessage(PrintWriter out, JSONObject map,
			String bnf) {
		String langName = (String) map.get(IMessageFields.ILanguageInfo.LANGUAGE_NAME);
		if (langName == null) {
			sendStatusMsg(out, 001, "Missing language name", map);
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> opMapRaw = (Map<String, String>)(map.get(IMessageFields.ILanguageInfo.OPERATIONS_MAP));
		System.out.println(opMapRaw.toString());
		Map<String, WellKnownOperator> opMap = new HashMap<>();
		try {
			for (Map.Entry<String, String> entry : opMapRaw.entrySet()) {
					opMap.put(entry.getKey(), WellKnownOperator.valueOf(entry.getValue().toUpperCase()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		
			sendStatusMsg(out, 102, e.toString(), map);
			return;
		}

		//save bnf to a file so it can call parseLanguage directly
		LanguageParsingUnit _model;
		try {
			_model = new LanguageParsingUnit(adapter, new StringReader(bnf), opMap);
		} catch (Exception e) {
			e.printStackTrace();
			sendStatusMsg(out, 101, e.toString(), map);
			return;
		}
		parsedLanguage.put(langName, _model);
		sendStatusMsg(out, 100, "parse language ok \n", map);
		adapter.addLanguage(langName);
	}
    /**
     * 
     * @param out output stream to write result to.
     * @param code number representation of the status_code
     * @param statusMsg the status_msg
     * @param map the original received JSON object.
     */
    private void sendStatusMsg(PrintWriter out, int code, String statusMsg, JSONObject map) {
    	Map<String, Object> status_msg = new LinkedHashMap<String, Object>();
    	
		status_msg.put(IMessageFields.IStatusInfo.STATUS_CODE, code); 
		status_msg.put(IMessageFields.IStatusInfo.STATUS_MSG, statusMsg);
		List<String> optionalKeys = Arrays.asList(IMessageFields.ILanguageInfo.LANGUAGE_NAME,
				IMessageFields.IProgramInfo.PROGRAM_NAME,
				IMessageFields.IExecutionInfo.INIT_CODE);
		for (String key : optionalKeys) {
			Object value = map.get(key);
			if (value != null)
				status_msg.put(key, value);
		}
		System.out.println("sending msg: " + status_msg.toString());
		out.println(JSONValue.toJSONString(status_msg));
    }
}
