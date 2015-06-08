package token.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

import token.Token;



/**
 * Use the StreamTokenizer provided by in java.io to scan an input stream and extract an appropriate Token.
 */
public class XMLTokenizer extends ATokenizer {

	/**
	 * Initialize _st to read from a input Reader 
	 *
	 * @param reader the input Reader to use
	 */
	public XMLTokenizer(Reader reader)  {
		super(reader);
		_st.ordinaryChar('/');

		//this.setSymbols(Arrays.asList(new String[] {"+", "<", "</", ">", "/" }));
	}

	/**
	 * Initialize _st to read from StringReader from the given text 
	 * @param text  The input text to tokenize.
	 */
	public XMLTokenizer(String text) {
		this(new StringReader(text));
	}


	/**
	 * Use _st to scan the input, extracts, and returns an appropriate concrete Token.
	 *
	 * @return next token
	 * @throws IllegalArgumentException Thrown if an illegal input is encountered.
	 */
	protected Token makeNextToken() {

		try {
			if (StreamTokenizer.TT_EOF != _st.nextToken()) {
				switch (_st.ttype) {

				case '+':
				{
					System.err.println("Plus Token");
					//STUDENT TO COMPLETE 
					return tokFac.makeToken("+","+");
				}
				case '<':
				{
					System.err.println("Found < Token");
					Token next = getNextToken();
					if(next.getLexeme().equals("/") ){
						System.err.println("</ Token");
						//STUDENT TO COMPLETE 
						return tokFac.makeToken("</","</");

					}
					else {
						_st.pushBack();   // Ok only for this LL(2) situation. Need more robust pushback mechansim for LL(k)

						System.err.println("Left Bracket Token");
						//STUDENT TO COMPLETE 
						return tokFac.makeToken("<","<");
					}
				}
				case '>':
				{
					System.err.println("Right Bracket Token");
					//STUDENT TO COMPLETE 
					return tokFac.makeToken(">",">");
				}
				case '/':
				{
					System.err.println("Forward Slash Token");
					//STUDENT TO COMPLETE 
					return tokFac.makeToken("/","/");
				}

				case StreamTokenizer.TT_NUMBER:
				{
					System.err.println("Num Token: "+_st.nval);
					//STUDENT TO COMPLETE 
					return tokFac.makeToken("Num","" + _st.nval);
				}
				case StreamTokenizer.TT_WORD:
				{
					System.err.println("Id Token: "+_st.sval);
					//STUDENT TO COMPLETE 
					return tokFac.makeToken("Id",_st.sval);
				}
				default:
				{
					throw new IllegalArgumentException("XMLTokenizer: Illegal token type = "+_st.ttype);
				}
				}
			}
			else {
				closeReader();
				System.err.println("EOF Token");
				//STUDENT TO COMPLETE 
				return Token.EOF;
				//throw new IllegalStateException("Reading past EOF!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}





