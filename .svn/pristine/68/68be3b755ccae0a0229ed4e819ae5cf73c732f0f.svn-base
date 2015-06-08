package server;

import hw03Common.defs.WellKnownOperator;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.ParserFactFactory;
import parser.AGramSym_ToString;
import parser.IGramSymVisitor;
import parser.IGrammarSymbol;
import parser.ITokVisitorFact;
import parser.visitor.bnf.MakeParserFactAlgo;
import server.model.IServerModelToViewAdapter;
import token.ATokVisitor;
import token.ITokVisitor;
import token.Token;
import token.tokenizer.ITokenizer;
import token.tokenizer.regex.LexerFactory;
/**
 * A parsing unit stores all necessary information to parse a particular program for a particular language.
 * 
 *
 */
public class LanguageParsingUnit {
	/**
	 * an adapter to communicate to view.
	 */
	@SuppressWarnings("unused")
	private final IServerModelToViewAdapter adapter;
	/**
	 * key word set for this lang.
	 */
	private final LexerFactory lexerFactory;
	/**
	 * parse result for this program, using this language.
	 */
	private final IGrammarSymbol parseResult;
	/**
	 * non-terminal maps for this language.
	 */
	private final Map<String,IGrammarSymbol> nonTerminalsMap;
	
	/**
	 * The map of operation names to their standard definitions
	 */
	private final Map<String, WellKnownOperator> opMap;
	/**
	 * 
	 * @param adapter adapter to view.
	 * @param langReader reader for language file or language string
	 * @param opMap operator maps.
	 */
	@SuppressWarnings("unchecked")
	public LanguageParsingUnit(IServerModelToViewAdapter adapter, Reader langReader, Map<String, WellKnownOperator> opMap) {
		this.adapter = adapter;
		this.opMap = opMap;
		
		ITokenizer tkzr = getRegexTokenizer(langReader);
		Set<String> keywordSet = new HashSet<>();
		ITokVisitorFact bnfParseFact = ParserFactFactory.Singleton.makeBNFParser("lang.txt", tkzr, keywordSet);

		String resultStr = "bnfParseFact = \n"+bnfParseFact+"\n";

		ITokVisitor<IGrammarSymbol, Object> parser = bnfParseFact.makeVisitor();
		adapter.outputText("Parser visitor = " + parser);
		parseResult = tkzr.getNextToken().execute(parser);
		adapter.outputText("Result = \n" + parseResult);
		adapter.outputText("RDPModel.parseBNF(): keywords = "+keywordSet);

//
//			System.out.println("Parse result for " + langFile.getName() + ": " + resultStr);
//			adapter.outputText("Parse result for " + langFile.getName() + ": " + resultStr);
		
		resultStr += parseResult.toString()+"\n";
		adapter.outputText("RDPModel.parseBNF(): result =\n"+parseResult.typeExecute(AGramSym_ToString.Singleton));
		// find non-terminals
		

		IGramSymVisitor<Map<String,IGrammarSymbol>, Map<String, IGrammarSymbol>> findNonTermsAlgo = new parser.visitor.bnf.FindNonTerminalsAlgo();
		adapter.outputText("Parse result: " + parseResult);
		nonTerminalsMap = parseResult.execute(findNonTermsAlgo, new HashMap<String, IGrammarSymbol>());

		resultStr = "\nNon-Terminal Symbols: ";

		for(Map.Entry<String, IGrammarSymbol> entry : nonTerminalsMap.entrySet()) {
			resultStr += entry.getKey()+" ";
		}
		resultStr += "\n";

		adapter.outputText("RDPModel.checkBNF(): "+resultStr);
		
		// parse input file
		adapter.outputText("BNF keywords: " + keywordSet);
		
		lexerFactory = LexerFactory.fromKeywords(keywordSet.toArray(new String[keywordSet.size()]));
		
	}
	/**
	 * return a tokenizer
	 * @param reader A reader that will read characters out.
	 * @return a tokenizer
	 */
	private ITokenizer getRegexTokenizer(Reader reader) {
		String regexFile = "bnf.lex.txt";
		try {
			ITokenizer lex = new LexerFactory(regexFile).makeLexer(reader);
			System.out.println(lex.toString());
			return lex;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 * @param reader A reader that will read characters out.
	 * @return parsed grammar symbol for the program.
	 */
	public IGrammarSymbol parseProgram(Reader reader) {
		//tkzr2 = tokenizer2symbols.get(tokenizerClass).apply(new FileReader(filename));   // reset the tokenizer
		ITokenizer tkzr2 = lexerFactory.makeLexer(reader);

		System.out.println("RDPModel.checkBNF(): Starting MakeParserFactAlgo...");
		ITokVisitorFact parserFact = parseResult.execute(new MakeParserFactAlgo(tkzr2, nonTerminalsMap));
		System.out.println("Parser fact: " + parserFact);
		ATokVisitor<IGrammarSymbol, Object> parser = parserFact.makeVisitor();
		System.out.println(((ATokVisitor<IGrammarSymbol, Object>) parser).getCmds());
		Token token = tkzr2.getNextToken();
		System.out.println("First token: " + token);
		return token.execute(parser);
	}
	/**
	 * getter for operator mapping.
	 * @return operator mapping.
	 */
	public Map<String, WellKnownOperator> getOpMap() {
		return opMap;
	}
}
