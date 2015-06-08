package token.tokenizer.regex;

import java.io.Reader;

import token.tokenizer.ITokenizer;

public interface ILexerFactory {

  /** Convenience overload for {@link LexerFactory#makeLexer(Reader)} */
  public ITokenizer makeLexer(String in);
  
  /**
   * Construct a new ILexer.
   * @param in - Input to be tokenized.
   * @return new ILexer for tokenizing <var>in</var>.
   */
  public ITokenizer makeLexer(java.io.Reader in);
  
  /** @return the set of names of all terminals */
  public java.util.Set<String> getTerminalSet();
  
}
