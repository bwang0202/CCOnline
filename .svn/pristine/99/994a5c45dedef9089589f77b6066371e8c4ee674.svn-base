package parser;

import token.ATokVisitor;
import token.ITokVisitorCmd;
import token.Token;
import token.tokenizer.ITokenizer;

/**
 * Factory for Empty grammar terminals.
 */
public class MTSymbolFact extends ATokVisitorFact {
	/**
	 * Constructor for Empty grammar terminals.
	 *
	 * @param tkz tokenizer to use
	 */
	public MTSymbolFact(ITokenizer tkz) {
		super("MTSymbol", tkz);
	}



	/**
	 * Utility method to make a cmd to parse an empty terminal.
	 *
	 * @return visitor to parse an empty terminal
	 */
	private ITokVisitorCmd<IGrammarSymbol, Object> makeCmd() {
		return new ITokVisitorCmd<IGrammarSymbol, Object>() {
			public IGrammarSymbol apply(String idx, Token host, Object... inps) {
				putBackToken(host);
				System.out.println("MTSymbolFact: Pushback");
				return MTSymbol.Singleton;      }
		};
	}

	/**
	 * Make a token visitor to parse an Empty terminal.
	 * The cmd installed is the default cmd
	 * @return token visitor
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeVisitor() {
		return new ATokVisitor<IGrammarSymbol, Object>(){
			{
				setDefaultCmd(makeCmd());
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
				setDefaultCmd(makeCmd());
			}
		};
	}

	/**
	 * @return Returns "MTSymbolFact" always
	 */
	@Override
	public String toString() {
		return "MTSymbolFact";
	}
}

