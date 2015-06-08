package parser.visitor.bnf;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import parser.AGramSymVisitor;
import parser.CombinationFact;
import parser.IGramSymVisitor;
import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;
import parser.ITokVisitorFact;
import parser.MTSymbolFact;
import parser.ProxyFact;
import parser.SequenceFact;
import parser.SequenceSymbol;
import parser.TerminalSymbol;
import parser.TerminalSymbolFact;
import token.tokenizer.ITokenizer;
import util.ILambda;


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
 * T1  ::= Id | QuotedStringToken
 * T2  ::= Empty | T
 * 
 * This class is a visitor that processes a parse tree (IGrammarSymbol) of the BNF for a grammar and produces a parser visitor factory
 * that will parse a token stream conforming to that grammar. 
 * 
 * The input parameter to this visitor is a mapping that maps the string 
 * representation of a non-terminal symbol to an instance of that symbol.  
 * This tells the algorithm what all the non-terminal symbols are and 
 * therefore, what all the terminal symbols are.
 * 
 * The return value is an ITokVisitorFact that will produce a visitor 
 * capable of parsing the grammar as described by the host parse tree.
 * 
 * This visitor is a type-based visitor and thus must be run through the typeExecute() method of the IGrammarSymbol host!
 */
public class MakeParserFactAlgo extends AGramSymVisitor<ITokVisitorFact, Void> {
	/**
	 * Utility class for default error processing
	 */
	private static class ErrorCmd<R, P> implements IGramSymVisitorCmd<R, P> {
		private String symbolName;

		ErrorCmd(String symbolName, R returnValue) {
			this.symbolName = symbolName;
		}

		@Override
		public R apply(String idx, IGrammarSymbol host, @SuppressWarnings("unchecked") P... inps) {
//			System.err.println("MakeParserFactAlgo: Invalid symbol encountered when processing "+symbolName+" symbol = "+host);
//			return returnValue;  
			throw new RuntimeException("MakeParserFactAlgo: Invalid index " + idx + " encountered when processing "+symbolName+" symbol = "+host);
		}   
	}


	/**
	 * The tokenizer to use
	 */
	private ITokenizer tkzr;
	
	/**
	 * Mapping of non-terminal names to their corresponding grammar symbol objects in a parse tree.
	 */
	private Map<String,IGrammarSymbol> nonTerminals;
	
	/**
	 * Map of all non-terminals to their corresponding proxy objects.   Used for closing loops in the grammar.
	 */
	private Map<String,ProxyFact> proxies = new HashMap<String, ProxyFact>();

	/**
	 * Constructor for the class
	 * @param tkzr  The tokenizer to use
	 * @param nonTerminals The map of non-terminal symbols in the grammar
	 */
	public MakeParserFactAlgo(ITokenizer tkzr, Map<String,IGrammarSymbol> nonTerminals) {
		super(new ErrorCmd<ITokVisitorFact, Void>("S", null));   
		this.tkzr = tkzr;
		this.nonTerminals = nonTerminals;

		System.out.println("Nonterminals: " + nonTerminals);
		
		for (String nonTerminal : nonTerminals.keySet()) {
			proxies.put(nonTerminal, new ProxyFact());
		}

		setCmd("S1", (idx, host, nu) -> {
			System.out.println("An S1: " + host);
			return getNthInSequence(host, 1).execute(this, nu);
		});
//		setCmds(dAlgo.getCmds());
		setCmd("D", (idx, host, nu) -> host.execute(dAlgo, nu));
		

	}

	/**
	 * Helper visitor for the D grammar token 
	 */
	private AGramSymVisitor<ITokVisitorFact, Void> dAlgo = new  AGramSymVisitor<ITokVisitorFact, Void>(new ErrorCmd<ITokVisitorFact, Void>("D", null)){
		//Initializer block
		{
			setCmd("D", new IGramSymVisitorCmd<ITokVisitorFact,Void>() {
				public ITokVisitorFact apply(String idx, IGrammarSymbol host, Void... nu) {
					SequenceSymbol D = (SequenceSymbol) host;
					IGrammarSymbol id = getNthInSequence(host, 0);
					System.out.println("ID: " + id);
					//SequenceSymbol e = (SequenceSymbol) getNthInSequence(host, 2);
					IGrammarSymbol E = ((SequenceSymbol) getNthInSequence(host, 2)).getSymbol1();
					System.out.println("e: " + E.getName() + ", " + E);
					IGrammarSymbol L = getNthInSequence(host, 3);
					System.out.println("L: " + L.getName() + ", " + L);
					ITokVisitorFact eFactory = E.execute(eAlgo, (TerminalSymbol) D.getSymbol1());
					@SuppressWarnings("unused")
					ITokVisitorFact lFactory = L.execute(lAlgo);
					Objects.requireNonNull(eFactory);
						String name = ((SequenceSymbol)id).getSymbol1().toString();
						System.out.println("Id: " + name);
					try {
						proxies.get(name).setFact(eFactory);
					} catch (NullPointerException exc) {
						throw new IllegalStateException(name + " not a proxy name among " + proxies.keySet());
					}
					System.out.println("Set factory for " + id.toString());
					return eFactory;
				}
			});
		}
	};

	/**
	 * Helper visitor for the E grammar symbol
	 */
	private IGramSymVisitor<ITokVisitorFact, TerminalSymbol> eAlgo = new  AGramSymVisitor<ITokVisitorFact, TerminalSymbol>(new ErrorCmd<ITokVisitorFact, TerminalSymbol>("E", null)){
		//Initializer block
		{
			setCmd("E", new IGramSymVisitorCmd<ITokVisitorFact,TerminalSymbol>() {
				public ITokVisitorFact apply(String idx, IGrammarSymbol host, final TerminalSymbol... termSyms) {
					SequenceSymbol tSymbol = (SequenceSymbol)((SequenceSymbol)host).getSymbol1();
					IGrammarSymbol e1Symbol = ((SequenceSymbol)host).getSymbol2();

					ITokVisitorFact tFact = tSymbol.execute(tAlgo, termSyms);

					// Delegate to E1
					ITokVisitorFact eFact = e1Symbol.execute(new  AGramSymVisitor<ITokVisitorFact, ITokVisitorFact>(new ErrorCmd<ITokVisitorFact, ITokVisitorFact>("E1", tFact)){

						//Initializer block
						{
							setCmd("E1a", new IGramSymVisitorCmd<ITokVisitorFact,ITokVisitorFact>() {
								public ITokVisitorFact apply(String idx, IGrammarSymbol e1aHost, ITokVisitorFact... tFacts) {
									ITokVisitorFact e1aFact = ((SequenceSymbol)e1aHost).getSymbol2().execute(eAlgo, termSyms);
									return new CombinationFact("E1", tkzr, tFacts[0], e1aFact);  // return combination
								}
							});
							setCmd("MTSymbol", new IGramSymVisitorCmd<ITokVisitorFact,ITokVisitorFact>() {
								public ITokVisitorFact apply(String idx, IGrammarSymbol e1aHost, ITokVisitorFact... tFacts) {
									return tFacts[0];
								}
							});
						}
					}, tFact);
					return eFact;
				}
			});
		}  
	};


	/**
	 * Helper visitor for the T grammar symbol in the grammar
	 */
	private IGramSymVisitor<ITokVisitorFact, TerminalSymbol> tAlgo = new  AGramSymVisitor<ITokVisitorFact, TerminalSymbol>(new ErrorCmd<ITokVisitorFact, TerminalSymbol>("T", null)){
		//Initializer block
		{
			setCmd("T", new IGramSymVisitorCmd<ITokVisitorFact,TerminalSymbol>() {
				public ITokVisitorFact apply(String idx, IGrammarSymbol host, final TerminalSymbol... termSyms) {

					// make factory from T1
					ITokVisitorFact t1Fact = ((SequenceSymbol)host).getSymbol1().execute(new  AGramSymVisitor<ITokVisitorFact, Void>(new ErrorCmd<ITokVisitorFact, Void>("T1", null)){
						{
							setCmd("Id", new IGramSymVisitorCmd<ITokVisitorFact,Void>() {
								public ITokVisitorFact apply(String idx, IGrammarSymbol host, Void... nu) {
									System.out.println("Searching map " + nonTerminals + " for " + host);
									IGrammarSymbol s = nonTerminals.get(host.toString());
									if(null == s) { 
										System.out.println("Searching map " + nonTerminals + " for " + host + ", not found");
										// terminal symbol
										// Need to check if it is an "Empty" Id terminal token (i.e. a terminal value called "Empty" in the BNF)
										if("Empty".equals(host.toString())) {
											return new MTSymbolFact(tkzr);
										}
										else {
											return new TerminalSymbolFact(((TerminalSymbol)host).getLexeme(), tkzr);
										}
									}
									else {
										System.out.println("Found " + host.toString());
										// non-terminal symbol
										// non-terminals and proxies queried separately to allow for future changes
										// to code to allow non-terminals not in loops to have to have a proxy.
										return Objects.requireNonNull(proxies.get(host.toString()));  // return the proxy
									}
								}
							});

							setCmd("QuotedStringToken", new IGramSymVisitorCmd<ITokVisitorFact,Void>() {
								public ITokVisitorFact apply(String idx, IGrammarSymbol host, Void... nu) {
									// quoted string tokens are never non-terminals
									return new TerminalSymbolFact(((TerminalSymbol)host).getLexeme(), tkzr);

								}
							});
						}
					});
					// Delegate to T2 since it depends on whether or not the sequence terminates.
					return ((SequenceSymbol)host).getSymbol2().execute(new  AGramSymVisitor<ITokVisitorFact, ITokVisitorFact>(new ErrorCmd<ITokVisitorFact, ITokVisitorFact>("T2", t1Fact)){
						{
							setCmd("T", new IGramSymVisitorCmd<ITokVisitorFact,ITokVisitorFact>() {
								public ITokVisitorFact apply(String idx, IGrammarSymbol host, ITokVisitorFact... facts) {
									// Need to lazily generate the SequenceFact's name because the incoming fact[0] 
									//may be a proxy that has not yet been set, so currently it's toString returns only
									// ProxyFact(null).
									ILambda<String, Void> getNameCmd = new ILambda<String, Void>(){
										@Override
										public String apply(Void... param) {
											return termSyms[0].getLexeme(); // = dSymbol.getLexeme() = name of the non-terminal of the production
										}
										
									};
									//return new SequenceFact("T_"+facts[0], tkzr, facts[0], host.execute(tAlgo));
									return new SequenceFact(getNameCmd, tkzr, facts[0], host.execute(tAlgo, termSyms));
								}
							});        
							setCmd("MTSymbol", new IGramSymVisitorCmd<ITokVisitorFact,ITokVisitorFact>() {
								public ITokVisitorFact apply(String idx, IGrammarSymbol host, ITokVisitorFact... facts) {
									return facts[0];
								}
							});        
						}
					}, t1Fact);
				}
			});
		}
	};

	
	/**
	 * Helper visitor for the L grammar symbol
	 */
	private AGramSymVisitor<ITokVisitorFact, Void> lAlgo = new  AGramSymVisitor<ITokVisitorFact, Void>(new ErrorCmd<ITokVisitorFact, Void>("L", null)){
		//Initializer block
		{
			setCmd("L2", new IGramSymVisitorCmd<ITokVisitorFact,Void>() {
				public ITokVisitorFact apply(String idx, IGrammarSymbol l2Host, Void... nu) {
					System.err.println("Processing L2: "+l2Host);
					return ((SequenceSymbol)l2Host).getSymbol2().execute(new AGramSymVisitor<ITokVisitorFact, Void>(new ErrorCmd<ITokVisitorFact, Void>("L3", null)) {
						//Initializer block
						{
							setCmds(lAlgo.getCmds());
							setCmds(dAlgo.getCmds());
						}
					});
				}
			});

			setCmd("MTSymbol", new IGramSymVisitorCmd<ITokVisitorFact,Void>() {
				public ITokVisitorFact apply(String idx, IGrammarSymbol l2Host, Void... nu) {
					System.err.println("lAlgo processing MTSymbol.");
					return null;
				}
			});
		}
	};

	/**
	 * Utility method to get the n'th Grammar symbol in a SequenceSymbol
	 * @param h
	 * @param n
	 * @return
	 */
	private IGrammarSymbol getNthInSequence(IGrammarSymbol h, int n) {
		IGrammarSymbol s = h;
		for(int i=0; i<n;i++) {
			s = ((SequenceSymbol)s).getSymbol2();
		}
		return s;
	}
}
