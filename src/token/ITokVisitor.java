package token;

import extvisitor.IExtVisitor;

/**
 * Interface for a token visitor.
 */
public interface ITokVisitor<R, P> extends IExtVisitor<R, String, P, Token>{

}

