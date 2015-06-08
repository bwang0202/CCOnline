package token;

import extvisitor.IExtVisitorCmd;

/**
 * A command for use in ATokVisitors
 */
public interface ITokVisitorCmd<R, P> extends IExtVisitorCmd<R, String, P, Token> {
}