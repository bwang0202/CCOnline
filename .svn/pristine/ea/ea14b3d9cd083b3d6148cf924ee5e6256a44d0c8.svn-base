package parser.visitor.xml;

import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;
import parser.SequenceSymbol;

/**
 * Algo to run a given ACheckTagsAlgo on the n'th (starting at zero) 
 * SequenceSymbol in a linear list of chained SequenceTokens (ie. connected
 * via their second tokens).
 * Note that the given algo is typeExecuted on the n'th SequenceSymbol in the
 * chain, not the first symbol of that SequenceSymbol.   This allows the given
 * algo to continue recurring down the chain.
 * The first input is the number of the SequenceSymbol to go to, starting at zero.
 * The second input is the algo for that SequenceSymbol to typeExecute (no input params).
 */
public class CheckNthSequenceAlgo extends ACheckTagsAlgo {
	public static final CheckNthSequenceAlgo Singleton = new CheckNthSequenceAlgo();

	private CheckNthSequenceAlgo(){
		setCmd("SequenceSymbol", new IGramSymVisitorCmd<Boolean,Object>() {
			public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
				int n = (Integer) inps[0];
				if(0 == n){ // Found it!
					// Run the given algo on the host, NOT host.getSymbol1()!
					return ((SequenceSymbol)host).typeExecute((ACheckTagsAlgo) inps[1]);
				}
				else {
					// Keep counting down
					return ((SequenceSymbol)host).getSymbol2().typeExecute(CheckNthSequenceAlgo.this, --n, inps[1]);
				}
			}
		});
	}
}
