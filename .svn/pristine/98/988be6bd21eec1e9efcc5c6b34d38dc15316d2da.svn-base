package parser;
import token.Token;
import token.tokenizer.ITokenizer;
import util.ILambda;

/**
 * Abstract factory for a token visitor.
 */
public abstract class ATokVisitorFact implements ITokVisitorFact {

	//protected String _name;

	/**
	 * Tokenizer.
	 */
	private ITokenizer _tokenizer;

	/**
	 * Constructor for the abstract factory.
	 * Used when the name of the factory is already known. 
	 * @param name the name of the 
	 * @param tkz tokenizer to use
	 */
	public ATokVisitorFact(final String name, ITokenizer tkz) {
		//_name = name;
		this(new ILambda<String, Void>() {

			@Override
			public String apply(Void... param) {
				return name;
			}
			
		}, tkz);
		//_tokenizer = tkz;
	}
	
	/**
	 * Constructor for the abstract factory.
	 * Used when the name of the factory must be lazily generated, e.g. when a proxy is involved. 
	 * @param getNameCmd   The command to generate the name
	 * @param tkz  The tokenizer to use
	 */
	public ATokVisitorFact(ILambda<String, Void> getNameCmd, ITokenizer tkz) {
		this._getNameCmd = getNameCmd;
		this._tokenizer = tkz;
	}
	
	private ILambda<String, Void> _getNameCmd;

	/**
	 * The name of this factory, which should correspond to the associated production rule name
	 */
	public String getName() {
		//return _name;
		return _getNameCmd.apply();
	}
	
	/**
	 * Return the next token.
	 *
	 * @return next token
	 */
	protected final Token nextToken() {
		return _tokenizer.getNextToken();
	}

	/**
	 * Put the given token back into the tokenizer.
	 * @param t the token to put back
	 */
	protected final void putBackToken(Token t) {
		_tokenizer.putBack(t);
	}

}

