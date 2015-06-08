package parser;

import extvisitor.AExtVisitor;

/**
 * Command-based abstract visitor to an IGrammarSymbol that uses IGramSymVisitorCmd<R, P> commands and takes the default command in its constructor.
 * @author swong
 *
 * @param <R>   The return type
 * @param <P>   The input parameter type
 */
public abstract class AGramSymVisitor<R, P> extends AExtVisitor<R, String, P, IGrammarSymbol> implements IGramSymVisitor<R, P> {

	/**
	 * Constructor for the class
	 * @param cmd The default command to use.
	 */
	public AGramSymVisitor(IGramSymVisitorCmd<R, P> cmd){
		super(cmd);
	}

}