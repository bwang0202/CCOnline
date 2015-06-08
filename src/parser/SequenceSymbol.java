package parser;

/**
 * Grammar non-terminal -- SeqeuenceToken ::= Token1 Token2
 * Binary sequence
 */
public class SequenceSymbol extends AGrammarSymbol {
	/**
	 * Token1 object.
	 */
	private IGrammarSymbol _symbol1;

	/**
	 * Token2 object.
	 */
	private IGrammarSymbol _symbol2;

	/**
	 * Constructor for sequence symbol.
	 * @param name name of the symbol
	 * @param symbol1 grammar symbol 1
	 * @param symbol2 grammar symbol 2
	 */
	public SequenceSymbol(String name, IGrammarSymbol symbol1, IGrammarSymbol symbol2) {
		super(name);  
		_symbol1 = symbol1;
		_symbol2 = symbol2;
	}

	/**
	 * Return a string representation consisting of a String representation of 
	 * each of the internal components of E.
	 *
	 * @return string representation
	 */
	@Override
	public String toString() {
		return _symbol1.toString() +" "+ _symbol2.toString();
	}

	/**
	 * getter for gammar symbol 1
	 * @return grammar symbol 1
	 */
	public IGrammarSymbol getSymbol1() {
		return _symbol1;
	}

	/**
	 * Getter for grammar symbol
	 * @return grammar symbol
	 */
	public IGrammarSymbol getSymbol2() {
		return _symbol2;
	}

}

