package parser.visitor.bnf;

import java.util.HashMap;
import java.util.Map;

import parser.AGramSymVisitor;
import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;
import parser.SequenceSymbol;

/**
 * BNF of BNF
 * 
 * S   ::= D | S1
 * S1  ::= "\n" S
 * D   ::=  Id "::=" E L
 * 
 * L   ::=  L2| Empty
 * L2  ::=  "\n"  L3
 * L3  ::=  L | D
 * 
 * E   ::=  T E1
 * E1  ::=  Empty | E1a
 * E1a ::=  "|" E
 * 
 * T   ::= T1 T2
 * T1  ::= Id| QuotedStringToken
 * T2  ::= Empty | T
 * 
 * This visitor to a grammar symbol will return a 
 * Map<String, IGrammarSymbol> that maps the name of every
 * non-terminal symbol in the grammar to an instance of 
 * the corresponding symbol.
 * 
 */
public class FindNonTerminalsAlgo extends AGramSymVisitor<Map<String,IGrammarSymbol>, Map<String, IGrammarSymbol>> {

	/**
	 * Constructor for the class
	 */
	public FindNonTerminalsAlgo() {
		super(new ErrorCmd<Map<String,IGrammarSymbol>>("S", new HashMap<String, IGrammarSymbol>()));

		setCmd("S1", (idx, host, maps) -> {
			System.out.println("An S1: " + host);
			getNthInSequence(host, 1).execute(this, maps);
			return maps[0];
		});
  
		setCmds(dAlgo.getCmds());
		
		// -------------------------------------
//		lAlgo = LAlgoMaker.Singleton.make(dAlgo);  // STUDENT COMMENT OUT THIS LINE WHEN IMPLEMENTING THE lAlgo field.
		// -------------------------------------

	}


	/**
	 * Helper visitor to parse the D grammar symbol
	 */
	private AGramSymVisitor<Map<String,IGrammarSymbol>, Map<String, IGrammarSymbol>> dAlgo = new AGramSymVisitor<Map<String,IGrammarSymbol>, Map<String, IGrammarSymbol>>(new ErrorCmd<Map<String,IGrammarSymbol>>("D", null)) {
		// Initializer block
		{
			setCmd("D", new IGramSymVisitorCmd<Map<String, IGrammarSymbol>,Map<String, IGrammarSymbol>>() {
				public Map<String, IGrammarSymbol> apply(String idx, IGrammarSymbol host, @SuppressWarnings("unchecked") Map<String, IGrammarSymbol>... maps) {
					System.out.println("FindNonTerminalsAlgo.dAlgo, processing D...");
					IGrammarSymbol nonTerminal = ((SequenceSymbol) host).getSymbol1();
					maps[0].put(nonTerminal.toString(), host);
					IGrammarSymbol L = getNthInSequence(host, 3);
					System.out.println("Sending " + L + ", " + L.getClass() + " from " + host + "(" + host.getName() + ")" + " to lAlgo");
					L.execute(lAlgo, maps);
					System.out.println("Back");
					return maps[0];
				}
			});
		}
	};

	/**
	 * STUDENT TO COMPLETE THE IMPLEMENTATION OF lAlgo _AFTER_ IMPLEMENTING dAlgo
	 * BE SURE TO COMMENT OUT THE lAlgo FACTORY LINE IN THE ABOVE CONSTRUCTOR WHEN WRITING 
	 * YOUR OWN lAlgo IMPLEMENTATION!
	 * 
	 * lAlgo is a helper visitor to process the L grammar symbol
	 */
	private AGramSymVisitor<Map<String,IGrammarSymbol>, Map<String, IGrammarSymbol>> lAlgo =  new AGramSymVisitor<Map<String,IGrammarSymbol>, Map<String, IGrammarSymbol>>(new ErrorCmd<Map<String,IGrammarSymbol>>("D", null)) {
		
		{
			setCmd("L2", (idx, host, maps) -> getNthInSequence(host, 1).execute(this, maps));
			setCmd("MTSymbol", (idx, host, maps) -> maps[0]);
			setCmd("D", (idx, host, maps) -> host.execute(dAlgo, maps));
		}
	};

	
	/**
	 * Convenience method that returns the n'th symbol in a sequence.
	 * Note that that unless the symbol is the last symbol in the 
	 * sequence, the SequenceSymbol itself will be returned.  
	 * @param h The start of the sequence. Unless n=0, this should be a SequenceSymbol
	 * @param n The element in the sequence to return.  0 = the first symbol
	 * @return A SequenceSymbol, unless n refers to the last symbol in a sequence, where it will be the symbol itself.
	 */
	private IGrammarSymbol getNthInSequence(IGrammarSymbol h, int n) {
		IGrammarSymbol s = h;
		for(int i=0; i<n;i++) {
			s = ((SequenceSymbol)s).getSymbol2();
		}
		return s;
	}
}