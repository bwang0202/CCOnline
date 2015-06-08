package parser;

import token.ATokVisitor;
import token.tokenizer.ITokenizer;

/**
 * Utility factory that automatically chains together a series of 
 * SequenceFactories.  The result is to create a single factory that 
 * appears to create an arbitrarily long sequence of ITVFactories.
 * Note that each SequenceSymbol generated in the chain is given a 
 * unique, numbered name to aid in debugging.  The first (returned) one
 * has the given name.
 * SequenceTokens only ever call the "Sequence" case of their visitors,
 * so the individual tokens are indistinguishable to the visitors.
 */
public class MultiSequenceFact implements ITokVisitorFact {
	private ITokVisitorFact _composed;


	/**
	 * Constructor for the sequence factory,
	 *
	 * @param name The grammar token's name of this instance of a sequence.  Individual sequence names are made by appending a numerical value to this name.
	 * @param tkz    tokenizer to use
	 * @param fact1  factory for the first non-terminals
	 * @param fact2 factory for the second non-terminals
	 * @param facts a vararg array of additional factories of non-terminals
	 */
	public MultiSequenceFact(String name, ITokenizer tkz, ITokVisitorFact fact1, ITokVisitorFact fact2, ITokVisitorFact...facts) {

		if(0 == facts.length) {
			_composed = makeComposed(name, tkz, fact1, fact2);
		}
		else {
			ITokVisitorFact temp = facts[facts.length-1];
			for(int i = facts.length-2; i >= 0 ; i--) {
				temp = makeComposed(name +(i+2), tkz, facts[i], temp);
			}
			temp = makeComposed(name+1, tkz, fact2, temp);
			_composed = makeComposed(name, tkz, fact1, temp);
		}
	}

	/**
	 * Utility method to make a binary sub-sequence out of two factories
	 * @param name The (sub)name used for this sub-sequence 
	 * @param tkzr The tokenizer to use
	 * @param fact1  First factory	
	 * @param fact2	Second factory
	 * @return A sequence factory of the two factories.
	 */
	private ITokVisitorFact makeComposed(String name, ITokenizer tkzr, ITokVisitorFact fact1, ITokVisitorFact fact2) {
		return new SequenceFact(name, tkzr, fact1, fact2);
	}

	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeVisitor() {
		return _composed.makeVisitor();
	}
	
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeCombinedVisitor(ATokVisitor<IGrammarSymbol, Object> other) {
		return _composed.makeCombinedVisitor(other);
	}

	@Override
	public String getName() {
		return _composed.getName();
	}  
	/**
	 * Returns the toString of the top-level sequence
	 */
	@Override
	public String toString() {
		return _composed.toString();
	}

}