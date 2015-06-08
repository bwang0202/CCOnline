package parser;

import java.util.Objects;

import token.ATokVisitor;
import token.tokenizer.ITokenizer;

/**
 * Factory for a binary Combination grammar non-terminal objects.
 */
public class CombinationFact extends ATokVisitorFact {

	/**
	 * Factory for first grammar non-terminals.
	 */
	private ITokVisitorFact _fact1;

	/**
	 * Factory for second grammar non-terminals.
	 */
	private ITokVisitorFact _fact2;

	/**
	 * Constructor for the F factory,
	 *
	 * @param tkz    tokenizer to use
	 * @param f1Fact factory for F1 grammar non-terminals
	 * @param f2Fact factory for F2 grammar non-terminals
	 */
	public CombinationFact(String name, ITokenizer tkz, ITokVisitorFact fact1, ITokVisitorFact fact2) {
		super(name, tkz);
		if (name.equals("E1"))
			System.out.println("Found E1");
		Objects.requireNonNull(fact1);
		Objects.requireNonNull(fact2);
		_fact1 = fact1;
		_fact2 = fact2;
	}

	/**
	 * Make a token visitor to parse an F non-terminal.
	 *
	 * @return token visitor
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeVisitor() {
		System.out.println("At " + getName() + ", asking for things from " + _fact1.getName() + " and " + _fact2.getName());
		ATokVisitor<IGrammarSymbol, Object> madeVisitor = _fact2.makeVisitor();
		return _fact1.makeCombinedVisitor(madeVisitor);
	}

	/**
	 * Make a token visitor that delegates to the given visitor in a chain of responsibility
	 *
	 * @param other visitor to serve as other in the chain
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeCombinedVisitor(ATokVisitor<IGrammarSymbol, Object> other) {
		return _fact1.makeCombinedVisitor(_fact2.makeCombinedVisitor(other));
	}

	/**
	 * Used to prevent recursive printing when same factory appears more than once
	 */
	private boolean firstTime = true;

	/**
	 * Recursively prints the composed factories only the first time that this is called, otherwise just prints their names.
	 */
	@Override
	public String toString() {
		if(firstTime) {
			firstTime = false;
			String result = "CombinationFact("+getName()+": "+_fact1+", "+_fact2+")";
			firstTime = true;
			return result;
		}
		else {
			return "CombinationFact("+getName()+": "+_fact1.getName()+", "+_fact2.getName()+")";
		}
	}
}

