package parser;

import token.ATokVisitor;
import token.Token;
import token.tokenizer.ITokenizer;
import extvisitor.IExtVisitorCmd;


/**
 * Factory for a simple non-terminal that references a single token.
 */
public class TerminalSymbolFact extends ATokVisitorFact {

	/**
	 * Constructor for the F1 factory.
	 *
	 * @param tkz tokenizer to use
	 * @param name the name that references the single token
	 */
	public TerminalSymbolFact(String name, ITokenizer tkz) {
		super(name, tkz);
	}

	
	protected IExtVisitorCmd<IGrammarSymbol, String, Object, Token> makeCmd() {
		return new IExtVisitorCmd<IGrammarSymbol, String, Object, Token>() {
			public IGrammarSymbol apply(String idx, Token host, Object... inp) {
				System.out.println("TerminalSymbolFact("+getName()+")");
				return new TerminalSymbol(getName(),(Token)host);
			}
		};
			
	}
	/**
	 * Utility method to add a new terminal token parser cmd to the given visitor
	 * @param target   The visitor to add the command to.
	 */
//	private void addCmdTo(ATokVisitor<IGrammarSymbol, Object> target) {
//		target.setCmd(_name, new IExtVisitorCmd<IGrammarSymbol, String, Object, Token>() {
//			public IGrammarSymbol apply(String idx, Token host, Object... inp) {
//				System.out.println("TerminalSymbolFact("+_name+")");
//				return new TerminalSymbol(_name,(Token)host);
//			}
//		});
//	}

	/**
	 * Make a token visitor to parse an E1a non-terminal.
	 *
	 * @return token visitor
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeVisitor() {
		return new ATokVisitor<IGrammarSymbol, Object>() {
			{
				//addCmdTo(this);
				this.setCmd(getName(), makeCmd());
			}
		};
	}

	/**
	 * Make a token visitor that will process the combination of this 
	 * or the other given symbol 
	 *
	 * @param other The visitor for the other symbol in the combination 
	 * @return A token visitor
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeCombinedVisitor(ATokVisitor<IGrammarSymbol, Object> other) {
		return new ATokVisitor<IGrammarSymbol, Object>(other) {
			{
				//addCmdTo(this);
				this.setCmd(getName(), makeCmd());
			}
		};
	}


	@Override
	public String toString() {
		return "TerminalSymbolFact("+getName()+")";
	}
}

