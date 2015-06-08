package client.model.exec;

import hw03Common.defs.WellKnownOperator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONValue;

import server.LanguageParsingUnit;
import server.ProgramParsingUnit;
import server.model.IServerModelToViewAdapter;
import debug.Logger;

/**
 * Exists to quickly test parsing and executing without going through the client-server communication API
 * @author Derek
 *
 */
public class ExecTester {

	public static void main(String[] args) throws FileNotFoundException {
		Logger.setOut();
		
		File opMapFile = new File("tinycmap.txt");
		
		@SuppressWarnings("unchecked")
		Map<String, String> opMapRaw = (Map<String, String>) JSONValue.parse(new FileReader(opMapFile));
		System.out.println(opMapRaw);
		
		Map<String, WellKnownOperator> opMap = new HashMap<>();
		for (Map.Entry<String, String> entry : opMapRaw.entrySet()) {
			opMap.put(entry.getKey(), WellKnownOperator.valueOf(entry.getValue()));
		}
		
		// create a model that prints to System.out
		LanguageParsingUnit unit = new LanguageParsingUnit(new IServerModelToViewAdapter() {
			
			@Override
			public void outputText(String text) {
				System.out.println(text);
			}
			
			@Override
			public void addProgram(String progName) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addLanguage(String langName) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addConnectedClient(String ipAddress) {
				// TODO Auto-generated method stub
				
			}
		}, new FileReader("tinyc.txt"), opMap);
		
		//unit.parseProgram(new File("quadratic.txt"), opMap);
		ProgramParsingUnit progUnit = new ProgramParsingUnit(unit, new FileReader("tinycprogram.txt"));
		StringBuilder builder = new StringBuilder();
		
		progUnit.executeProgram(progUnit.parseProgram(new StringReader("= a 5 = b 2 = c 0")), builder);
		
		System.out.println(builder);
		
	}
	
}
