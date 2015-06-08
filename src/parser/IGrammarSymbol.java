package parser;

import extvisitor.IExtVisitor;
import extvisitor.IExtVisitorHost;

/**
 * Represents a token (symbol) in the grammar, either terminal or non-terminal.
 */
public interface IGrammarSymbol extends IExtVisitorHost<String, IGrammarSymbol>{
	
	/**
	 * Getter fro the name of the grammar symbol
	 * @return  The name
	 */
	public abstract String getName();
	
	/**
	 * Visitor execution based on the name of the host
	 * @param algo  The visitor to run
	 * @param inps input array to use
	 * @return the results of running the visitor.
	 * @param <R> The return type
	 * @param <P> The input parameter type
	 */
	public <R, P> R execute(IExtVisitor<R, String, P, IGrammarSymbol> algo, @SuppressWarnings("unchecked") P... inps);


	/**
	 * Visitor execution based on the type of the host (class name without the rest of the pathname)
	 * @param algo  The visitor to run
	 * @param inps input array to use
	 * @return the results of running the visitor.
	 */
	public abstract <R, P> R typeExecute(IGramSymVisitor<R, P> algo, @SuppressWarnings("unchecked") P... inps);

}
