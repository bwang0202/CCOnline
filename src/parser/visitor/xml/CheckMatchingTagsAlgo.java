package parser.visitor.xml;

import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;
import parser.SequenceSymbol;

/**
 * Algo to check the semantics of a parsed XML file.  
 * Checks if the start tags match their end tags.
 * A simple Id token returns true.
 * An MTSymbol token returns true.
 * In general, valid XML produces a sequence of tokens as per the 
 * TaggedElt production rule: TaggedElt ::= < Id > AXML </ Id > 
 * TaggedElt is a chain of 6 SequenceTokens: 
 * below, each [A, B] is a binary SequenceSymbol: 
 * [<, [Id, [>, [AXML, [</, [Id, >]]]]]]  
 * Note that AXML is always a Sequence whose last token is MTSymbol.
 * 
 * This is a type-driven algorithm and thus, MUST be called from the host's typeExecute() method!
 */
public class CheckMatchingTagsAlgo extends ACheckTagsAlgo {
	public static final CheckMatchingTagsAlgo Singleton = new CheckMatchingTagsAlgo();

	/**
	 * Utility algo to jump to the n'th SequenceSymbol in a chain.
	 */
	private CheckNthSequenceAlgo nthSeq = CheckNthSequenceAlgo.Singleton;

	/**
	 * Utility algo to process every element in a chain of SequenceTokens
	 */
	private ACheckTagsAlgo processList = ProcessListAlgo.Singleton;


	/**
	 * Constructor for the class
	 */
	private CheckMatchingTagsAlgo() {

		setCmd("TerminalSymbol", new IGramSymVisitorCmd<Boolean,Object>() {
			public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
				return true; 
			}
		});

		setCmd("SequenceSymbol", new IGramSymVisitorCmd<Boolean,Object>() {
			public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
				return  ((SequenceSymbol)host).getSymbol2().typeExecute(nthSeq, 0, new ACheckTagsAlgo(){
					{
						setCmd("SequenceSymbol", new IGramSymVisitorCmd<Boolean,Object>() {
							public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
								final String startTag = ((SequenceSymbol)host).getSymbol1().toString();
								return ((SequenceSymbol)host).getSymbol2().typeExecute(nthSeq, 1, new ACheckTagsAlgo(){
									{
										setCmd("SequenceSymbol", new IGramSymVisitorCmd<Boolean,Object>() {
											public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
												return ((SequenceSymbol)host).getSymbol1().typeExecute(processList, CheckMatchingTagsAlgo.this) 
														&& ((SequenceSymbol)host).getSymbol2().typeExecute(nthSeq, 1, new ACheckTagsAlgo(){
															{
																setCmd("SequenceSymbol", new IGramSymVisitorCmd<Boolean,Object>() {
																	public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
																		String endTag = ((SequenceSymbol)host).getSymbol1().toString();
																		return startTag.equals(endTag);
																	}
																});
															}
														});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});

		setCmd("MTSymbol", new IGramSymVisitorCmd<Boolean,Object>() {
			public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
				return true;
			}
		});
	}

}