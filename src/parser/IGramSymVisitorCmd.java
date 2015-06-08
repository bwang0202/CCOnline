package parser;

import extvisitor.IExtVisitorCmd;

/**
 * Command used in AGramSymVisitors
 * @author swong
 *
 * @param <R>  The return type
 * @param <P>	The input paramter type
 */
public interface IGramSymVisitorCmd<R, P> extends IExtVisitorCmd<R, String, P, IGrammarSymbol>{
}