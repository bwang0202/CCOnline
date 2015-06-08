package token;

import extvisitor.AExtVisitor;
import extvisitor.IExtVisitorCmd;

/**
 * A visitor to an Token. 
 */
public abstract class ATokVisitor<R,P> extends AExtVisitor<R, String, P, Token> implements ITokVisitor<R, P> {
	
	/**
	 * Constructor for the class
	 */
	public ATokVisitor() {
		super( new IExtVisitorCmd<R, String, P, Token>() {
			@Override
			public R apply(String idx, Token host, @SuppressWarnings("unchecked") P... inps) {
				throw new IllegalArgumentException("ATokVisitor: Unknown token encountered: "+ idx);
			}
		});
	}

	/**
	 * Constructor that copies another visitor... Does so by sharing commands
	 * @param other The visitor to copy.
	 */
	public ATokVisitor(ATokVisitor<R,P> other) {
		super(other);
	}  
}