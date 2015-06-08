package parser;

import token.Token;

/**
 * Grammar terminal symbol
 */
public class TerminalSymbol extends AGrammarSymbol {
	/**
	 * Terminal token.
	 */
	private Token _token;
	
	
	public String getLexeme() {
		return _token.getLexeme();
	}


	/**
	 * Constructor for the Word grammar non-terminal.
	 *
	 * @param name number 
	 * @param token grammar token
	 */
	public TerminalSymbol(String name, Token token) {
		super(name);  
		_token = token;
	}

	/**
	 * Return a string representation.
	 *
	 * @return string representation
	 */
	@Override
	public String toString() {
		return _token.toString();
	}

}

