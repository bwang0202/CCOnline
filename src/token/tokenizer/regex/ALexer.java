package token.tokenizer.regex;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import token.Token;
import token.TokenFactory;
import token.tokenizer.ITokenizer;
import util.FatalError;

abstract class ALexer implements ITokenizer {
  
  private final LinkedList<Token> tokenStream;
  
  /**
   * Default constructor
   * @param in - language text to be parsed
   */
  ALexer(Reader in) {
    tokenStream = tokenize(in);
  }
  
  @Override public Token getNextToken() { return tokenStream.pop(); }
  
  @Override public void putBack(Token t) { tokenStream.push(t); }
  
  protected abstract Pattern tokenPattern();
  
  protected abstract Collection<ARule> getRules();
  
  protected abstract ARule getRule(String name);
    
  // TODO: This process could be made lazy by using the Pattern.end() function
  // to check when we need to read additional input into the StringBuilder.
  private LinkedList<Token> tokenize(Reader r) {
    TokenFactory tokFact = TokenFactory.Singleton;
    int loc = 0;
    boolean needsDelimiter = false;
    LinkedList<Token> tokens = new LinkedList<Token>();
    StringBuilder in = readerToStringBuilder(r);
    do {
      boolean foundType = false;
      Matcher m = tokenPattern().matcher(in);
      if (m.lookingAt()) {
        for (ARule rule : getRules()) {
          String value = m.group(rule.groupName);
          if (value != null) {
            // Check delimiter status
            if (needsDelimiter && !rule.isDelimiter) FatalError.die("Missing delimiter at character #%d:%n%.32s", loc, in);
            if (rule.needsUnescaping) value = unescapeString(value);
            // Create & add token
            if (!rule.isIgnored) tokens.add(tokFact.makeToken(rule.name, value));
            // Consume input
            int len = m.end();
            in.delete(0, len);
            loc += len;
            needsDelimiter = !rule.isDelimiter;
            // Move on to next token
            foundType = true;
            System.out.println("Value: " + value);
            System.out.println("Rule: " + rule);
            System.out.println("Matcher: " + tokenPattern());
            break;
          }
        }
        // Make sure we found the token type
        if (!foundType) FatalError.die("Unknown token match");
      }
      else {
    	  System.err.println("No match for " + m);
    	  FatalError.die("Syntax error at character #%d:%n%.32s", loc, in);
      }
    } while (in.length() > 0);
    // Add End of Input token (needed for supporting EPSILON pushback)
    tokens.add(Token.EOF);
    return tokens;
  }
  
  /**
   * Handle basic escape sequences in a string of characters
   * Supports \r \n \t, and replaces \x with x for any other character x.
   */
  private String unescapeString(String value) {
    String s = value;
    s = s.replace("\\r", "\r").replace("\\n", "\n");
    s = s.replace("\\t", "\t").replaceAll("\\\\(.)", "$1");
    return s;
  }

  private StringBuilder readerToStringBuilder(Reader r) {
    char[] buf = new char[1024];
    StringBuilder sb = new StringBuilder();
    try {
      int n = 0;
      while (n >= 0) {
        n = r.read(buf);
        if (n > 0) sb.append(buf, 0, n);
      }
      r.close();
    }
    catch (IOException e) { FatalError.die("Error reading tokenize input: %s", e); }
    return sb;
  }
  
  @Override public String toString() {
    return String.format("<ALexer: %s>", tokenStream);
  }
  
}
