package token.tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

import token.Token;


/**
 * Use the StreamTokenizer provided by in java.io to scan an input stream and extract an appropriate Token.
 */
public class Tokenizer1 extends ATokenizer { 
  
  /**
   * Initialize _st to read from a input Reader 
   *
   * @param reader Input Reader to use
   */
  public Tokenizer1(Reader reader){
    super(reader);
    //this.setSymbols(Arrays.asList(new String[] {"+", "*"}));
  }
  
  /**
   * Initialize _St to read from a StringReader from the given text 
   * @param text The text input to the tokenizer
   */
  public Tokenizer1(String text) {
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
          case '*':
          {
            System.err.println("Star Token");
            //STUDENT TO COMPLETE 
            return tokFac.makeToken("*","*");
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
            throw new IllegalArgumentException("Tokenizer1: Illegal token type = " + _st.ttype);
        }
      }
      else {
        closeReader();
        System.err.println("EOF Token");
         //STUDENT TO COMPLETE 
        return Token.EOF;
        //throw new IllegalStateException("Reading past EOF!");
      }
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  
}



  
  
