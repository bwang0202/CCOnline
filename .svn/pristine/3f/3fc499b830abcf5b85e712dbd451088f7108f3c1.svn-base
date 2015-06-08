package model;

import java.io.FileNotFoundException;
import java.util.Set;

import parser.CombinationFact;
import parser.ITokVisitorFact;
import parser.MTSymbolFact;
import parser.MultiSequenceFact;
import parser.ProxyFact;
import parser.SequenceFact;
import parser.TerminalSymbolFact;
import token.Token;
import token.tokenizer.ITokenizer;


/**
 * Factory with methods to create various parsing visitor factories for various predtermined grammars
 * @author swong
 *
 */
public class ParserFactFactory {

	/**
	 * Singleton instance
	 */
	public static final ParserFactFactory Singleton = new ParserFactFactory();
	private ParserFactFactory() {
	}

	/**
	 * Make original grammar parser.
	 *  
	 *  E ::= F E1
	 *  F ::= F1 | F2
	 *  F1 ::= NumToken
	 *  F2 ::= IdToken
	 *  E1 ::= E1a | Empty
	 *  E1a ::= + E
	 * 
	 */
	public ITokVisitorFact makeOrigParser(String filename, ITokenizer tkzr) {

		ProxyFact eFact_Proxy = new ProxyFact(); // use this wherever E appears in the grammar, except the very beginning.

		ITokVisitorFact eFact = new SequenceFact("E", tkzr,
				new CombinationFact("F", tkzr, 
						new TerminalSymbolFact("Num", tkzr), 
						new TerminalSymbolFact("Id", tkzr)),
						new CombinationFact("E1", tkzr, 
								new SequenceFact("E1a", tkzr, 
										new TerminalSymbolFact("+", tkzr), 
										eFact_Proxy), 
										new MTSymbolFact(tkzr)));

		eFact_Proxy.setFact(eFact);  // close the loop


		System.err.println("Parser Factory = " + eFact);

		return eFact;
	}


	/**
	 * Make simple XML parser.
	 *
	 * TaggedElt ::= < Id > AXML </ Id > 
	 * AXML  ::=  NEXML | Empty   
	 * NEXML ::=  AElement AXML 
	 * AElement ::=  Id | TaggedElt 
	 * 
	 * Notes:
	 * "</" is a single token above, not two tokens
	 * 
	 * @param filename The input file to parse
	 * @return a factory for making the parsing visitor
	 */
	public ITokVisitorFact makeXMLParser(String filename, ITokenizer tkzr) {

		ITokVisitorFact leftBracketFact = new TerminalSymbolFact("<", tkzr);
		ITokVisitorFact rightBracketFact = new TerminalSymbolFact(">", tkzr);
		//    ITokVisitorFact forwardSlashFact = new TerminalSymbolFact("\"/\"", tok);
		ITokVisitorFact idFact = new TerminalSymbolFact("Id", tkzr);
		ITokVisitorFact leftBracketForwardSlashFact = new TerminalSymbolFact("</", tkzr);

		ProxyFact taggedElt_Proxy = new ProxyFact();

		ProxyFact aXML_Proxy = new ProxyFact();

		ITokVisitorFact aXML = new CombinationFact("AXML", tkzr,
				new MultiSequenceFact("NEXML", tkzr,
						new CombinationFact("AElement", tkzr,
								idFact, taggedElt_Proxy),
								aXML_Proxy), 
								new MTSymbolFact(tkzr));
		aXML_Proxy.setFact(aXML);

		ITokVisitorFact taggedElt = new MultiSequenceFact("EFac", tkzr,
				leftBracketFact, idFact, rightBracketFact,
				aXML, 
				leftBracketForwardSlashFact, idFact, rightBracketFact);

		taggedElt_Proxy.setFact(taggedElt);

		System.err.println("Parser Factory = " + taggedElt);

		return taggedElt;
	}
// "S, S1, D, L, L2, L3, E, E1, E1a, T, T1, T2"
	
	/**
	 * Make a parser for BNF
	 * BNF of BNF
	 * 
	 * S   ::= D | S1
	 * S1  ::= "\n" S
	 * D   ::=  Id "::=" E L
	 * 
	 * L   ::=  L2 | Empty
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
	 * @param filename The file to read in
	 * @return a factory to make a parsing visitor
	 * @throws FileNotFoundException
	 */
	public ITokVisitorFact makeBNFParser(String filename, final ITokenizer tkzInitial, final Set<String> keywordSet) {

		// Alternate code for decorating the tokenizer rather than decorating the TerminalSymbolFact:
	  
	    // Decorate the tokenizer to find the keywords,
	    // which are all the QuotedStringTokens in the BNF input
	    ITokenizer tkz = new ITokenizer() {
	      @Override public Token getNextToken() {
	        Token t = tkzInitial.getNextToken();
	        System.err.println("Token: " + t + ", " + t.getName());
	        if (t.getName().equals("QuotedStringToken")) {
	        	keywordSet.add(t.getLexeme());
	        }
	        return t;
	      }
	      @Override public void putBack(Token t) { tkzInitial.putBack(t); }
	    };

	    // all nonterminals
//	    String linefeed = "\n";
//	    String alt = "|";
//	    String produces = "::=";
	    String linefeed = "Linefeed";
	    String alt = "Alt";
	    String produces = "Produces";
		ITokVisitorFact newLine = new TerminalSymbolFact(linefeed, tkz);
		ITokVisitorFact assignment = new TerminalSymbolFact(produces, tkz);
		ITokVisitorFact id = new TerminalSymbolFact("Id", tkz);
		ITokVisitorFact bar = new TerminalSymbolFact(alt, tkz);
		ITokVisitorFact quotedString = new TerminalSymbolFact("QuotedStringToken", tkz);
		
		// empty
		// proxies
		ProxyFact S = new ProxyFact();
		ProxyFact S1 = new ProxyFact();
		ProxyFact D = new ProxyFact();
		ProxyFact L = new ProxyFact();
		ProxyFact L2 = new ProxyFact();
		ProxyFact L3 = new ProxyFact();
		ProxyFact E = new ProxyFact();
		ProxyFact E1 = new ProxyFact();
		ProxyFact E1a = new ProxyFact();
		ProxyFact T = new ProxyFact();
		ProxyFact T1 = new ProxyFact();
		ProxyFact T2 = new ProxyFact();
		

		//filling the proxies
		
//		 * S   ::= D | S1
//		 * S1  ::= "\n" S
//		 * D   ::=  Id "::=" E L
		
		S.setFact(new CombinationFact("S", tkz, D, S1));
		S1.setFact(new SequenceFact("S1", tkz, newLine, S));
		D.setFact(new MultiSequenceFact("D", tkz, id, assignment, E, L));

//		 * L   ::=  L2 | Empty
//		 * L2  ::=  "\n"  L3
//		 * L3  ::=  L | D
		
		L.setFact(new CombinationFact("L", tkz, L2, new MTSymbolFact(tkz)));
		L2.setFact(new SequenceFact("L2", tkz, newLine, L3));
		L3.setFact(new CombinationFact("L3", tkz, L, D));

//		 * E   ::=  T E1
//		 * E1  ::=  Empty | E1a
//		 * E1a ::=  "|" E

		E.setFact(new SequenceFact("E", tkz, T, E1));
		E1.setFact(new CombinationFact("E1", tkz, new MTSymbolFact(tkz), E1a));
		E1a.setFact(new SequenceFact("E1a", tkz, bar, E));

//		 * T   ::= T1 T2
//		 * T1  ::= Id| QuotedStringToken
//		 * T2  ::= Empty | T

		T.setFact(new SequenceFact("T", tkz, T1, T2));
		T1.setFact(new CombinationFact("T1", tkz, id, quotedString));
		T2.setFact(new CombinationFact("T2", tkz, new MTSymbolFact(tkz), T));
		
		// return start token factory
		return S;
	}

}
