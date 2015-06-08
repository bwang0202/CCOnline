package parser.visitor.xml;

import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;
import parser.SequenceSymbol;
/**
 * Map the given ACheckTagsAlgo across all the tokens in a chain (list) of SequenceTokens.
 * The algo is typeExecuted on the FIRST token of each SequenceSymbol and the 
 * result of all the executions is AND'ed together as the final result.
 * That is, the final result is true, only if all the elements individually 
 * calculate to true.
 */
public class ProcessListAlgo extends ACheckTagsAlgo {
	public static final ProcessListAlgo Singleton = new ProcessListAlgo();

	private ProcessListAlgo(){
		setCmd("SequenceSymbol",new IGramSymVisitorCmd<Boolean,Object>() {
			public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
				boolean result = ((SequenceSymbol)host).getSymbol1().typeExecute((ACheckTagsAlgo)inps[0]);
				return result && ((SequenceSymbol)host).getSymbol2().typeExecute(ProcessListAlgo.this, inps);
			}
		});
		setCmd("MTSymbol",new IGramSymVisitorCmd<Boolean,Object>() {
			public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
				return true;   // Empty token at end of chain (list) defaults to true.
			}
		});
	}  
}

