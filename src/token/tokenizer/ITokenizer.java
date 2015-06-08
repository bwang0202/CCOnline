package token.tokenizer;

import token.Token;

/**
 * Extract and return an appropriate Token from some given source.
 */
public abstract interface ITokenizer {
    /**
     * Return the next token.
     */
    public abstract Token getNextToken();

    /**
     * Put the previously consumed token back into the token stream.  An arbitrary number of tokens can be put back into the stream. 
     * @param t the token to put back
     */
    public abstract void putBack(Token t);
    
}
