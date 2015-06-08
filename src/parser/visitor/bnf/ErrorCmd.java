package parser.visitor.bnf;

import java.util.Map;

import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;

public class ErrorCmd<R> implements IGramSymVisitorCmd<R, Map<String, IGrammarSymbol>> {
	private String symbolName;
	private R returnValue;

	ErrorCmd(String symbolName, R returnValue) {
		this.symbolName = symbolName;
		this.returnValue = returnValue;
	}

	public R apply(String idx, IGrammarSymbol host, @SuppressWarnings("unchecked") Map<String, IGrammarSymbol>... inps) {
		System.err.println("FindNonTerminalsAlgo.ErrorCmd: Invalid symbol encountered when processing "+symbolName+" idx = "+idx+" symbol = "+host.getName());
		return returnValue;  
	}   
}