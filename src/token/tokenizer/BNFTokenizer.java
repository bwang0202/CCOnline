package token.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

import token.Token;

/**
 * Use the StreamTokenizer provided by in java.io to scan an input stream and extract an appropriate AToken.
 */
public class BNFTokenizer extends  ATokenizer {
  
  /**
   * Initialize _st to read from a input Reader 
   *
   * @param reader the input reader to use
   */
  public BNFTokenizer(Reader reader) {
    super(reader);
    
   // this.setSymbols(Arrays.asList(new String[] {"|", "\n", "::=", }));
    
    _st.resetSyntax();
    _st.eolIsSignificant(true);
    _st.slashSlashComments(false);
    _st.slashStarComments(false);
    _st.quoteChar('"');
    _st.wordChars('a', 'z');
    _st.wordChars('A', 'Z');
    _st.wordChars('0', '9');
    _st.wordChars('_','_');
    _st.whitespaceChars(0,32);
  }
  
  /**
   * Initialize _st to use a StringReader from the given text string.
   * @param text  The input text to use
   */
  public BNFTokenizer(String text) {
	  this(new StringReader(text));
  }
  
  /**
   * Use _st to scan the input, extracts, and returns an appropriate concrete AToken.
   *
   * @return next token
   * @throws IllegalArgumentException Thrown if an illegal input is encountered.
   */

  public Token makeNextToken() {
    try {
      if (StreamTokenizer.TT_EOF != _st.nextToken()) {
        switch (_st.ttype) {
          case '|':
          {
            return tokFac.makeToken("|","|");
          }
          case '\n':
          {
            return tokFac.makeToken("lf","\n");
          }
          case '"':
          {
            return tokFac.makeToken("QuotedStringToken","\""+_st.sval+"\"");
          }
          case ':':
          {
            if (_st.nextToken() != ':') {
              throw new IllegalArgumentException("Illegal token!");
            }
            if (_st.nextToken() != '=') {
              throw new IllegalArgumentException("Illegal token!");
            }
            return tokFac.makeToken("::=","::=");
          }
          case StreamTokenizer.TT_WORD:
          {
            if (_st.sval.startsWith("_")) {
              throw new IllegalArgumentException("Cannot define symbols starting with '_': reserved character");
            }
            if ((_st.sval.charAt(0) >= '0') && (_st.sval.charAt(0) <= '9')) {
              throw new IllegalArgumentException("Cannot define symbols starting with '0' .. '9': reserved characters");                                
            }
            return tokFac.makeToken("Id",_st.sval);
          }
          default:
            throw new IllegalArgumentException("Illegal token (ttype=" + new Character((char)_st.ttype) + ", sval=" + _st.sval + ", nval=" + _st.nval + ")!");
        }
      }
      else {
        closeReader();
        System.err.println("BNFTokenizer.getNextToken: EOF Token");
        return Token.EOF;
      }
    }
    catch (IOException e) {
      System.err.println("BNFTokenizer.getNextToken: Exception = "+e.getMessage());
      return null;
    }
    
  }


}
