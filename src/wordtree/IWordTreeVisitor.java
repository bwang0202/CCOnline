package wordtree;

/**
 * Visitor to an ISymbolTree
 * @author swong
 *
 * @param <R> The return type
 * @param <P> The input parameter type
 */
public interface IWordTreeVisitor<R, P> {

	R leafTerminalCase(WordTree host, @SuppressWarnings("unchecked") P... params);
	R leafNonTerminalCase(WordTree host, @SuppressWarnings("unchecked") P... params);
	R nonLeafTerminalCase(WordTree host, @SuppressWarnings("unchecked") P... params);
	R nonLeafNonTerminalCase(WordTree host, @SuppressWarnings("unchecked") P... params);

}
