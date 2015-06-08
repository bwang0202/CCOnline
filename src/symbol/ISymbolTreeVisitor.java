package symbol;

/**
 * Visitor to an ISymbolTree
 * @author swong
 *
 * @param <R> The return type
 * @param <P> The input parameter type
 */
public interface ISymbolTreeVisitor<R, P> {

	R terminalSymCase(SymbolTree host, @SuppressWarnings("unchecked") P... params);
	R nonTerminalSymCase(SymbolTree host, @SuppressWarnings("unchecked") P... params);

}
