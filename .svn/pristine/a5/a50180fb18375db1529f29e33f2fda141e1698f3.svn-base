package token.tokenizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import token.ITokenFactory;
import token.Token;
import token.TokenFactory;
import util.ILambda;
import wordtree.IWordTreeVisitor;
import wordtree.WordTree;
import wordtree.WordTreeFactory;


/**
 * Use the StreamTokenizer provided by in java.io to scan an input stream and extract an appropriate Token.
 * Is customizable through the setSymbols() method which takes an Iterable<String>'s containing the keywords 
 * to recognize as special tokens.
 *  
 */
public abstract class ATokenizer implements ITokenizer {
	/**
	 * StreamTokenizer to use.
	 */
	protected StreamTokenizer _st;

	/**
	 * Reader to use.
	 */
	private Reader _reader;

	/**
	 * Factory for the tokens
	 */
	protected ITokenFactory tokFac = TokenFactory.Singleton;


	/**
	 * Initialize _st to read from a input Reader file with the given input file name.
	 *
	 * @param inputFileName the name of the input text file
	 * @throws FileNotFoundException
	 */
	public ATokenizer(Reader reader)  {
		_reader = reader;

		_st = new StreamTokenizer(_reader);

		//    _st.resetSyntax();
		//    _st.eolIsSignificant(false);
		_st.eolIsSignificant(false);
		_st.slashSlashComments(false);
		_st.slashStarComments(false);
		_st.quoteChar('"');
		_st.wordChars('a', 'z');
		_st.wordChars('A', 'Z');
		_st.wordChars('0', '9');
		_st.wordChars('_','_');
		_st.whitespaceChars(0,32);
	}

	public ATokenizer(String text) {
		this(new StringReader(text));
	}

	/**
	 * Stack of tokens that were put back into the stream.  Multiple pushbacks are allowed and are accumulated on the stack.
	 */
	private Stack<Token> tokenStack = new Stack<Token>();


	//private List<SymbolTree> symbols  = new ArrayList<SymbolTree>(); //SymbolTreeFactory.Singleton.makeSymbolTrees(Arrays.asList(new String[] {"+", "<", "</", ">", "/" }));
	private List<WordTree> symbols  = new ArrayList<WordTree>();
	/**
	 * Close the tokenizer's Reader
	 */
	protected void closeReader() {
		try {
			_reader.close();
		} catch (IOException e) {
			System.err.println("ATokenizer.closeReader():  Exception while closing the reader: "+ e);
			e.printStackTrace();
		}
	}

	/**
	 * Get the next token in the stream.
	 * @return the next token in the stream.
	 */
	public Token getNextToken() { 
		if(tokenStack.isEmpty()) return makeNextToken();
		else return tokenStack.pop();
	}


	/**
	 * Push the given token back into the stream.   
	 * Multiple pushbacks are allowed to enable popping out of deeply nested recursions.
	 * @param t The token to push back into the stream.
	 */
	public void putBack(Token t) {
		tokenStack.push(t);
	}


	protected abstract interface ITTypeValue {
		public int getTTtype();
		public Object getValue();
	}

	private ITTypeValue makeTTypeValue(final int ttype, final Object value){
		return new ITTypeValue() {

			@Override
			public int getTTtype() {
				return ttype;
			}

			@Override
			public Object getValue() {
				return value;
			}

		};
	}

	private Stack<ITTypeValue> tTypeValueStack = new Stack<ITTypeValue>();

	protected ITTypeValue popTTypeValue() {
		if(tTypeValueStack.isEmpty()) {
			try {
				_st.nextToken();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(_st.ttype){
			case StreamTokenizer.TT_NUMBER:
			{
				return makeTTypeValue(_st.ttype, _st.nval);
			}
			case StreamTokenizer.TT_WORD:
			{
				return makeTTypeValue(_st.ttype,_st.sval);
			}
			default:
				return makeTTypeValue(_st.ttype,(char)_st.ttype);
			}
		}
		else {
			return tTypeValueStack.pop();
		}
	}

	protected void pushTTypeValue(ITTypeValue ttv) {
		tTypeValueStack.push(ttv);
	}

	/**
	 * SEts the given symbol strings to be recognized tokens 
	 * KNOWN LIMITATION:  If "abc" is a special token, then "a" and "ab" are also special tokens.
	 * @param symStrs
	 */
//	protected void setSymbols(Iterable<String> symStrs) {
//		symbols = SymbolTreeFactory.Singleton.makeSymbolTrees(symStrs);
//
//		// Make sure that every char in the symbol trees is marked as an ordinary character to the StreamTokenizer.
//		for(SymbolTree st:symbols) {
//			if ('\n' == st.getValue() || '\r'==st.getValue()) {
//				_st.eolIsSignificant(true);
//			}
//			st.execute(new ISymbolTreeVisitor<Void, Void>(){
//
//				@Override
//				public Void terminalSymCase(SymbolTree host, Void... params) {
//					_st.ordinaryChar(host.getValue());
//
//					return null;
//				}
//
//				@Override
//				public Void nonTerminalSymCase(SymbolTree host, Void... params) {
//					_st.ordinaryChar(host.getValue());
//					for(SymbolTree nextST: host.getRest()) {
//						nextST.execute(this);
//					}
//					return null;
//				}
//
//			});
//		}
//	}


	protected void setSymbols(Iterable<String> symStrs) {
		symbols = WordTreeFactory.Singleton.makeSymbolTrees(symStrs);
		for(WordTree wt:symbols) {
			if ('\n' == wt.getValue() || '\r'==wt.getValue()) {
				_st.eolIsSignificant(true);
			}
			wt.execute(new IWordTreeVisitor<Void, Void>(){

				@Override
				public Void leafTerminalCase(WordTree host, Void... params) {
					_st.ordinaryChar(host.getValue());
					return null;
				}

				@Override
				public Void leafNonTerminalCase(WordTree host, Void... params) {
					// ERROR!  Should never get here b/c after reading keywords into wordtrees, all leaf nodes should be terminal. 
					throw new IllegalArgumentException("ATokenizer.setSymbols.leafNonTerminalCase: Leaf node of keyword tree should never be non-terminal!");
				}

				@Override
				public Void nonLeafTerminalCase(WordTree host, Void... params) {
					_st.ordinaryChar(host.getValue());
					for(WordTree nextWT: host.getRest()) {
						nextWT.execute(this);
					}
					return null;
				}

				@Override
				public Void nonLeafNonTerminalCase(WordTree host,
						Void... params) {
					// terminal and non-terminal leaf cases are the same
					return nonLeafTerminalCase(host);
				}
				
			});
		}
	}
	private IWordTreeVisitor<Token, Void> wtAlgo = new IWordTreeVisitor<Token, Void>() {

		/**
		 * cmds[0] => construct the terminal word using the given string and return the token made from it.
		 * cmds[1] => Perform any additional token pushing and return the token made from the closest (largest) terminal word. 
		 */
		IWordTreeVisitor<Token, ILambda<Token, String>> helper = new IWordTreeVisitor<Token, ILambda<Token, String>>() {

			
			@Override
			public Token leafTerminalCase(WordTree host, @SuppressWarnings("unchecked") ILambda<Token, String>... cmds) {
				return cmds[0].apply(""+host.getValue());  // make token from this terminal word 
			}

			@Override
			public Token leafNonTerminalCase(WordTree host, @SuppressWarnings("unchecked") ILambda<Token, String>... cmds) {
				// ERROR!  Should never get here b/c after reading keywords into wordtrees, all leaf nodes should be terminal. 
				throw new IllegalArgumentException("ATokenizer.wtAlgo.helper.leafNonTerminalCase: Leaf node of keyword tree should never be non-terminal!");
			}

			@SuppressWarnings("unchecked")
			@Override
			public Token nonLeafTerminalCase(final WordTree host, final ILambda<Token, String>... cmds) {
				// check the children symbols
				final ITTypeValue nextTTV = popTTypeValue();
				for(WordTree nextWT: host.getRest()){
					if(nextTTV.getTTtype() == nextWT.getValue()) {
						System.out.println("ATokenizer.wtAlgo.helper.nonLeafTerminalCase: matching ttype found in symbols: "+nextTTV.getTTtype()+" == "+nextWT.getValue());
						return nextWT.execute(this, new ILambda<Token, String>(){
							@Override
							public Token apply(String... wordAccs) {
								return cmds[0].apply(host.getValue()+wordAccs[0]);   // accumulate terminal word and return resultant token
							}				
						}, new ILambda<Token, String>() {
							@Override
							public Token apply(String... nu) {
								pushTTypeValue(nextTTV); // push the next token back
								return cmds[0].apply(""+host.getValue());   //  make token from this terminal word 
							}				
						});
					}
				}
				pushTTypeValue(nextTTV);  // push the next token back
				return cmds[0].apply(""+host.getValue());  //  make token from this terminal word 
			}

			@SuppressWarnings("unchecked")
			@Override
			public Token nonLeafNonTerminalCase(final WordTree host, final ILambda<Token, String>... cmds) {
				// check the children symbols
				final ITTypeValue nextTTV = popTTypeValue();
				for(WordTree nextWT: host.getRest()){
					if(nextTTV.getTTtype() == nextWT.getValue()) {
						System.out.println("ATokenizer.wtAlgo.helper.nonLeafNonTerminalCase: matching ttype found in symbols: "+nextTTV.getTTtype()+" == "+nextWT.getValue());
						return nextWT.execute(this, new ILambda<Token, String>(){

							@Override
							public Token apply(String... params) {
								return cmds[0].apply(host.getValue()+params[0]); // accumulate terminal word and return resultant token
							}

						}, new ILambda<Token, String>() {

							@Override
							public Token apply(String... nu) {
								pushTTypeValue(nextTTV);  // push the next token back
								return cmds[1].apply();  // push back any previous tokens and return the closest terminal word token
							}

						});
					}
				}
				pushTTypeValue(nextTTV); // push the next token back
				return cmds[1].apply(); // push back any previous tokens and return the closest terminal word token
			}


		};

		@Override
		public Token leafTerminalCase(WordTree host, Void... nu) {
			// make the token
			String name = ""+host.getValue();
			//HACK -- only works if "\n" is in the symbol list but not combined with any other symbols.
			if(name.equals("\n")) name= "lf";
			Token newToken =  tokFac.makeToken(name, ""+host.getValue());
			System.out.println("ATokenizer.wtAlgo.leafTerminalCase(): making token: "+ newToken);
			return newToken;
		}

		@Override
		public Token leafNonTerminalCase(WordTree host, Void...nu) {
			// ERROR!  Should never get here b/c after reading keywords into wordtrees, all leaf nodes should be terminal. 
			throw new IllegalArgumentException("ATokenizer.wtAlgo.leafNonTerminalCase: Leaf node of keyword tree should never be non-terminal!");
		}

		@SuppressWarnings("unchecked")
		@Override
		public Token nonLeafTerminalCase(final WordTree host, Void... nu) {
			// check the children symbols
			final ITTypeValue nextTTV = popTTypeValue();
			for(WordTree nextWT: host.getRest()){
				if(nextTTV.getTTtype() == nextWT.getValue()) {
					System.out.println("ATokenizer.makeNextToken(): matching ttype found in symbols: "+nextTTV.getTTtype()+" == "+nextWT.getValue());
					return nextWT.execute(helper, new ILambda<Token, String>(){
						@Override
						public Token apply(String... params) {
							String name = host.getValue()+params[0];  // accumulate the terminal word
							return  tokFac.makeToken(name, name);   // make the token from the accumulated terminal word
						}				
					}, new ILambda<Token, String>() {
						@Override
						public Token apply(String... nu) {
							pushTTypeValue(nextTTV);    // push the next token back
							String name = ""+host.getValue();   
							return  tokFac.makeToken(name, name);   // make the token from this terminal word
						}				
					});
				}
			}
			pushTTypeValue(nextTTV);  // push the next token back
			String name = ""+host.getValue();
			Token newToken =  tokFac.makeToken(name, name);   //make the token from this terminal word
			System.out.println("ATokenizer.w.Algo.nonLeafTerminalCase(): making token: "+ newToken);
			return newToken;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Token nonLeafNonTerminalCase(final WordTree host, Void... nu) {
			
			// check the children symbols
			final ITTypeValue nextTTV = popTTypeValue();
			for(WordTree nextWT: host.getRest()){
				if(nextTTV.getTTtype() == nextWT.getValue()) {
					System.out.println("ATokenizer.makeNextToken(): matching ttype found in symbols: "+nextTTV.getTTtype()+" == "+nextWT.getValue());
					return nextWT.execute(helper, new ILambda<Token, String>(){
						@Override
						public Token apply(String... params) {
							String name = host.getValue()+params[0];  // accumulate the terminal word
							return  tokFac.makeToken(name, name);   // make the token from the accumulated terminal word
						}				
					}, new ILambda<Token, String>() {
						@Override
						public Token apply(String... nu) {
							// NOT RIGHT!!  Need to find next terminal token.
							// want to keep nextTTV and pop the next token and run it against the whole symbols list.
							// if not in symbols or if a token gets made, then need to take accumulated TTypeValues and make an Id token.
							// this might need to be moved outside of this algo.
							pushTTypeValue(nextTTV);    // push the next token back
							String name = ""+host.getValue();   
							return  tokFac.makeToken("Id", name);   // make the token from this terminal word
						}				
					});
				}
			}
			pushTTypeValue(nextTTV);  // push the next token back
			String name = ""+host.getValue();
			Token newToken =  tokFac.makeToken("Id", name);   //make the token from this terminal word
			System.out.println("ATokenizer.wtAlgo.nonLeafNonTerminalCase(): making token: "+ newToken);
			return newToken;
		}
	};

//	ISymbolTreeVisitor<Token, String> stAlgo= new ISymbolTreeVisitor<Token, String>(){
//
//		@Override
//		public Token terminalSymCase(SymbolTree host,
//				String... symAccs) {
//			// make the token
//			String name = symAccs[0]+host.getValue();
//			//HACK -- only works if "\n" is in the symbol list but not combined with any other symbols.
//			if(name.equals("\n")) name= "lf";
//			Token newToken =  tokFac.makeToken(name, symAccs[0]+host.getValue());
//
//
//			System.out.println("ATokenizer.makeNextToken().terminalSymCase(): making token: "+ newToken);
//			return newToken;
//		}
//
//		@Override
//		public Token nonTerminalSymCase(SymbolTree host,
//				String... symAccs) {
//			// check the children symbols
//			ITTypeValue nextTTV = popTTypeValue();
//			for(SymbolTree nextST: host.getRest()){
//				if(nextTTV.getTTtype() == nextST.getValue()) {
//					System.out.println("ATokenizer.makeNextToken(): matching ttype found in symbols: "+nextTTV.getTTtype()+" == "+nextST.getValue());
//					return nextST.execute(this, symAccs[0]+host.getValue());
//				}
//			}
//			pushTTypeValue(nextTTV);
//			Token newToken =  tokFac.makeToken(symAccs[0]+host.getValue(), symAccs[0]+host.getValue());   // make token from symbols accumulated so far.
//			System.out.println("ATokenizer.makeNextToken().nonTerminalSymCase(): making token: "+ newToken);
//			return newToken;								
//		}
//
//	};

	/**
	 * Use _st to scan the input, extracts, and returns an appropriate concrete Token.
	 *
	 * @return next token
	 * @throws IllegalArgumentException Thrown if an illegal input is encountered.
	 */
	protected Token makeNextToken() {

		ITTypeValue ttv = popTTypeValue();

		if (StreamTokenizer.TT_EOF != ttv.getTTtype()) {
//			for(SymbolTree st: symbols ) {
//				if(ttv.getTTtype() == st.getValue()) {
//					System.out.println("ATokenizer.makeNextToken(): matching ttype found in symbols: "+ttv.getTTtype()+" == "+st.getValue());
//					return st.execute(stAlgo, "");  // start with empty string since going into top level symbol first					
//
//				}
//			}
			for(WordTree wt: symbols ) {
				if(ttv.getTTtype() == wt.getValue()) {
					System.out.println("ATokenizer.makeNextToken(): matching ttype found in symbols: "+ttv.getTTtype()+" == "+wt.getValue());
					return wt.execute(wtAlgo); 				

				}
			}

			switch (ttv.getTTtype()) {
			case '"':
			{
				return tokFac.makeToken("QuotedStringToken",_st.sval); //"\""+_st.sval+"\"");
			}	
			case StreamTokenizer.TT_NUMBER: {
				System.err.println("Num Token: "+ttv.getValue());
				return tokFac.makeToken("Num","" + ttv.getValue());
			}
			case StreamTokenizer.TT_WORD: {
				System.err.println("Id Token: "+ttv.getValue());
				if (_st.sval.startsWith("_")) {
					throw new IllegalArgumentException("Cannot define symbols starting with '_': reserved character");
				}
				if ((_st.sval.charAt(0) >= '0') && (_st.sval.charAt(0) <= '9')) {
					throw new IllegalArgumentException("Cannot define symbols starting with '0' .. '9': reserved characters");                                
				}
				return tokFac.makeToken("Id",ttv.getValue().toString());
			}
			default: {
				throw new IllegalArgumentException(getClass().getName()+": Illegal token type = "+ttv.getValue());
			}
			}
		}
		else {
			closeReader();
			System.err.println("EOF Token");
			return Token.EOF;
		}

	}


}





