package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import parser.AGramSym_ToString;
import parser.IGramSymVisitor;
import parser.IGrammarSymbol;
import parser.ITokVisitorFact;
import parser.visitor.bnf.MakeParserFactAlgo;
import parser.visitor.xml.CheckMatchingTagsAlgo;
import token.ATokVisitor;
import token.ITokVisitor;
import token.Token;
import token.tokenizer.ITokenizer;
import token.tokenizer.regex.LexerFactory;
import util.FatalError;
import util.ILambda;

public class RDPModel {

	interface ITokenizerMaker extends ILambda<ITokenizer, Reader> { }

	/**
	 * Preloaded symbol lists for customizing tokenizer for various languages.   The key names 
	 * correspond to the concrete ATokenizer subclass that does the same thing, though the 
	 * CustomTokenizer class will be used here.
	 */
	Map<String, ITokenizerMaker> tokenizer2symbols = new HashMap<String, ITokenizerMaker>();

	/**
	 * The result of parsing an input file with a statically defined (i.e. explicitly in code) parser.
	 */
	private IGrammarSymbol parseResult;


	private void addSimpleTokenizer(final String name, final String... syms) {
		tokenizer2symbols.put(name, new ITokenizerMaker() {
			@Override
			public ITokenizer apply(Reader... readers) {
	      // return new CustomTokenizer(readers[0], syms);
			  return LexerFactory.fromKeywords(syms).makeLexer(readers[0]);
			}
		});
	}

	private void addRegexTokenizer(final String name, final String specFilePath) {
		tokenizer2symbols.put(name, new ITokenizerMaker() {
			@Override
			public ITokenizer apply(Reader... readers) {
				try {
					ITokenizer lex = new LexerFactory(specFilePath).makeLexer(readers[0]);
					System.out.println(lex.toString());
					return lex;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					FatalError.die("Spec file not found: %s", specFilePath);
				}
				/* unreachable */ return null;
			}
		});
	}

	/**
	 * Constructor for the class
	 */
	public RDPModel() {
		addSimpleTokenizer("BNFTokenizer", "|", "\n", "::=" );
		addSimpleTokenizer("Tokenizer1", "+");//, "*");
		addSimpleTokenizer("XMLTokenizer", "+", "<", "</", ">", "/");

		addRegexTokenizer("RegexBNFTokenizer", "bnf.lex.txt");
	}

	/**
	 * Formally starts the model.  No-ops for now.   
	 */
	public void start() {
	}




	/**
	 * Parse original grammar file.
	 *
	 * @param filename The input file to parse
	 * @return a result string
	 */
	public String parseOrig(String filename) {
		try {
			String resultStr = "";
			ITokenizer tkzr = tokenizer2symbols.get("Tokenizer1").apply(new FileReader(filename));
			ITokVisitorFact origParseFact = ParserFactFactory.Singleton.makeOrigParser(filename, tkzr);
			resultStr += "parserFact = \n"+origParseFact+"\n";

			ITokVisitor<IGrammarSymbol, Object> parser = origParseFact.makeVisitor();
			System.err.println("Parser visitor = " + parser);

			parseResult = tkzr.getNextToken().execute(parser);
			System.err.println("Result = " + parseResult);

			resultStr += parseResult.toString()+"\n";
			return resultStr;
		}
		catch (Exception e1) {
			StringBuffer sb = new StringBuffer();
			sb.append(e1.toString());
			sb.append('\n');
			StackTraceElement[] ste = e1.getStackTrace();
			for (int i = 0; i < ste.length; ++i) {
				sb.append('\t');
				sb.append(ste[i].toString());
				sb.append('\n');
			}
			e1.printStackTrace();
			return sb.toString();
			//outputTA.setCaretPosition(0);
		}
	}

	/**
	 * Parse XML file.
	 *
	 * @param filename The name of input file
	 * @return a result string
	 */
	public String parseXML(String filename) {
		try {
			ITokenizer tkzr = tokenizer2symbols.get("XMLTokenizer").apply(new FileReader(filename));
			ITokVisitorFact xmlParseFact = ParserFactFactory.Singleton.makeXMLParser(filename, tkzr);

			ITokVisitor<IGrammarSymbol, Object> parser = xmlParseFact.makeVisitor();
			System.err.println("Parser visitor = " + parser);

			parseResult = tkzr.getNextToken().execute(parser);
			System.err.println("Result = " + parseResult);

			return parseResult.toString()+"\n";
		}
		catch (Exception e1) {
			StringBuffer sb = new StringBuffer();
			sb.append(e1.toString());
			sb.append('\n');
			StackTraceElement[] ste = e1.getStackTrace();
			for (int i = 0; i < ste.length; ++i) {
				sb.append('\t');
				sb.append(ste[i].toString());
				sb.append('\n');
			}
			e1.printStackTrace();
			return sb.toString();
		}
	}


	/**
	 * Check the XML for syntactic soundness
	 * @return A result string
	 */
	public String checkXML() {
		System.out.println("Checking XML on result = "+parseResult);
		boolean isOk = parseResult.typeExecute(CheckMatchingTagsAlgo.Singleton);
		return "\n"+"Check result: "+ isOk;
	}

	
	Set<String> keywordSet = new HashSet<String>();
	/**
	 * Parse the given BNF file
	 * @param filename  The file to parse
	 * @return a result string
	 */
	public String parseBNF(String filename) {
		try {
			ITokenizer tkzr = tokenizer2symbols.get("BNFTokenizer").apply(new FileReader(filename));
			keywordSet.clear();  // Reset the keywords!
			
			ITokVisitorFact bnfParseFact = ParserFactFactory.Singleton.makeBNFParser(filename, tkzr, keywordSet);

			String resultStr = "bnfParseFact = \n"+bnfParseFact+"\n";

			ITokVisitor<IGrammarSymbol, Object> parser = bnfParseFact.makeVisitor();
			System.err.println("Parser visitor = " + parser);

			parseResult = tkzr.getNextToken().execute(parser);
			System.err.println("Result = \n" + parseResult);
			
			System.err.println("RDPModel.parseBNF(): keywords = "+keywordSet);


			System.out.println("Parse result for " + filename + ": " + resultStr);
			resultStr += parseResult.toString()+"\n";
			System.out.println("RDPModel.parseBNF(): result =\n"+parseResult.typeExecute(AGramSym_ToString.Singleton));  // print detailed version of parse tree
			return resultStr;
		}
		catch (Exception e1) {
			StringBuffer sb = new StringBuffer();
			sb.append(e1.toString());
			sb.append('\n');
			StackTraceElement[] ste = e1.getStackTrace();
			for (int i = 0; i < ste.length; ++i) {
				sb.append('\t');
				sb.append(ste[i].toString());
				sb.append('\n');
			}
			e1.printStackTrace();
			return sb.toString()+"\n";
		}
	}


	/**
	 * Assumes that a BNF file was already parsed!
	 * 
	 * Finds all the non-terminals in the parsed BNF 
	 * Then creates a parser visitor factory and runs it on 
	 * the file specified in filename using the selected tokenizerClass.
	 * @param tokenizerClass The name of the tokenizer to use.
	 * @filemane the file to test to dynamically generated parser on
	 * @return a result string
	 */
	public String checkBNF(final String filename) {
		System.out.println("RDPModel.checkBNF(): result =\n"+parseResult.typeExecute(AGramSym_ToString.Singleton));  // print detailed version of parse tree

		IGramSymVisitor<Map<String,IGrammarSymbol>, Map<String, IGrammarSymbol>> findNonTermsAlgo = new parser.visitor.bnf.FindNonTerminalsAlgo();
		System.out.println("Parse result: " + parseResult);
		@SuppressWarnings("unchecked")
		Map<String,IGrammarSymbol> nonTerminalsMap = parseResult.execute(findNonTermsAlgo, new HashMap<String, IGrammarSymbol>());

		String resultStr = "\nNon-Terminal Symbols: ";

		for(Map.Entry<String, IGrammarSymbol> entry : nonTerminalsMap.entrySet()) {
			resultStr += entry.getValue()+" ";
		}
		resultStr += "\n";

		System.out.println("RDPModel.checkBNF(): "+resultStr);
		// COMMENT OUT THE REST OF THIS METHOD WHEN TESTING FindNonTerminalsAlgo
		try {
			System.out.println("Keywords: " + keywordSet);
			//ITokenizer tkzr2 = tokenizer2symbols.get(tokenizerClass).apply(new FileReader(filename));
			ITokenizer tkzr2 = LexerFactory.fromKeywords(keywordSet.toArray(new String[keywordSet.size()])).makeLexer(new FileReader(filename));
			Token token = tkzr2.getNextToken();
			while(token != Token.EOF) {  // print out all the tokens in the tokenizer 
				System.out.println("token = "+token+" "+token.getName());
				token= tkzr2.getNextToken();
			}
			//tkzr2 = tokenizer2symbols.get(tokenizerClass).apply(new FileReader(filename));   // reset the tokenizer
			tkzr2 = LexerFactory.fromKeywords(keywordSet.toArray(new String[keywordSet.size()])).makeLexer(new FileReader(filename));

			System.out.println("RDPModel.checkBNF(): Starting MakeParserFactAlgo...");
			ITokVisitorFact parserFact = parseResult.execute(new MakeParserFactAlgo(tkzr2, nonTerminalsMap));
			resultStr += "parserFact = \n"+parserFact+"\n";
			System.out.println("Parser fact: " + parserFact);
			ITokVisitor<IGrammarSymbol, Object> parser = parserFact.makeVisitor();
			System.out.println(((ATokVisitor<IGrammarSymbol, Object>) parser).getCmds());
			token = tkzr2.getNextToken();
			System.out.println("First token: " + token);
			IGrammarSymbol parseResult2 = token.execute(parser);
			System.err.println("Result = \n" + parseResult2);

			resultStr += parseResult2.toString()+"\n";
			return resultStr;
		}
		catch(Exception expt) {
			System.err.println("checkBNF():  Exception = "+expt);
			expt.printStackTrace();
			return "checkBNF():  Exception = "+expt;
		}
		// COMMENT OUT TO HERE
	}
}
