package parser;

import extvisitor.IExtVisitor;
/**
 * A visitor to an IGrammarSymbol
 * @author swong
 *
 * @param <R>   The return type
 * @param <P>	The input parameter type
 */
public interface IGramSymVisitor<R, P> extends IExtVisitor<R, String, P, IGrammarSymbol> {
}