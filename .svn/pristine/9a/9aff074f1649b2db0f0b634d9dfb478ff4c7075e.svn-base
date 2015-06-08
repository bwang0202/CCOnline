package token.tokenizer;

import java.io.Reader;
import java.io.StringReader;

/**
 * A tokenizer customized by a set of keywords that are to be recognized as tokens 
 * @author swong
 *
 */
public class CustomTokenizer extends ATokenizer {

	/**
	 * The main constructor of the class that takes an InputReader and an iterable of keyword strings.
	 * @param reader   The InputReader to use
	 * @param symStrs The iterable of keywords
	 */
	public CustomTokenizer(Reader reader, Iterable<String> symStrs) {
		super(reader);
		this.setSymbols(symStrs);
	}

	/**
	 * Convenience constructor that takes a text string instead of a filename.  This constructor 
	 * simply wraps the text in a StringReader and delegates to the main constructor.
	 * @param text  The text to tokenize
	 * @param symStrs An iterable of keywords.
	 */
	public CustomTokenizer(String text, Iterable<String> symStrs) {
		this(new StringReader(text), symStrs);
	}

}
