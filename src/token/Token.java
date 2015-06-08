package token;

import extvisitor.AExtVisitor;
import extvisitor.IExtVisitor;
import extvisitor.IExtVisitorHost;

/**
 * Abstract token class.
 */
public  class Token implements IExtVisitorHost<String, Token> {

  public static final Token EOF = new Token("EOF", "End Of File");
  
	/**
	 * The name of this token
	 */
	private String _name;

	/**
	 * The _lexeme this token is representing.
	 */
	private String _lexeme;

	/**
	 * Token constructor.
	 *
	 * @param name The name associated with this token.  This is the index value when it executes its visitors.
	 * @param lexeme _lexeme this token is representing.
	 */
	public Token(String name, String lexeme) {
		System.out.println("Creating token " + name + ", " + lexeme);
		_name = name;
		_lexeme = lexeme;
	}


	/**
	 * Executes (accepts) the visitor, calling the case associated with this host's index value.
	 * @param <R> The type of the return value
	 * @param <P> The type of the vararg input parameter
	 * @param algo The visitor to execute
	 * @param params The input parameters supplied to the algo when its appropriate case is called.
	 * @return The return value from executing the appropriate case on the visitor.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public <R, P> R execute(IExtVisitor<R, String, P, Token> algo,  @SuppressWarnings("unchecked") P... params) {
		System.err.println("Token " + this + " executing, "+algo+".caseAt("+_name+")  # cases = "+((AExtVisitor)algo).getCmds());
		//for(java.util.Map.Entry<String,IExtVisitorCmd<parser.IGrammarSymbol, String, Object, Token >> x:((ATokVisitor<parser.IGrammarSymbol, Object >)algo).getCmds()) System.out.println("index = "+ x.getKey());
		return algo.caseAt(_name, this, params);
	}

	/**
	 * Return a string representation of the token.
	 *
	 * @return string representation
	 */
	@Override
	public String toString() {
		//if( _lexeme.equals("\n")) return "LF";
		//else 
			return _lexeme;
	}
	
	/**
	 * Accessor for the name of the token
	 * @return The name of the token
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * Accessor for the token's lexeme
	 * @return The token's lexeme
	 */
	public String getLexeme() {
		return _lexeme;
	}
}

