package token.tokenizer.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import token.tokenizer.ITokenizer;
import util.ConfigLineReader;

public class LexerFactory implements ILexerFactory {
  
  private static <T extends Comparable<? super T>> List<T> reverseSorted(T[] a) {
    List<T> l = new ArrayList<T>(Arrays.asList(a));
    Collections.sort(l);
    Collections.reverse(l);
    return l;
  }
  
  /** Create a simple tokenizer from a list of keywords */
  public static LexerFactory fromKeywords(String... keywords) {
    LinkedHashMap<String, String> rawRules = new LinkedHashMap<>();
    // Custom keywords
    for (String k : reverseSorted(keywords)) {
      String pattern;
      // word-type keyword
      if (k.matches("\\w+")) pattern = k + "(?!\\w)";
      // delimiter-type keyword
      else {
        pattern = Pattern.quote(k);
        k = "." + k;
      }
      rawRules.put(k, pattern);
    }
    // Standard rules
    rawRules.put("*Whitespace", "\\s");
    rawRules.put("Num", "-?(\\d*\\.\\d+|\\d+\\.?)");
    rawRules.put("@QuotedStringToken", "\"(?<QuotedStringToken>([^\\\\\"]|\\\\.)*)\"");
    rawRules.put("Id", "[a-zA-Z]\\w*");
    return new LexerFactory(rawRules);
  }
  
  private final Map<String, ARule> rules;
  
  private final Pattern tokenPattern;
  
  /** Convenience constructor */
  public LexerFactory(String specFilePath) throws FileNotFoundException {
    this(new File(specFilePath));
  }
  
  /** Convenience constructor */
  public LexerFactory(File specFile) throws FileNotFoundException {
    this(new FileReader(specFile));
  }
  
  /** Convenience constructor */
  public LexerFactory(Reader spec) {
    this(new ConfigLineReader().parse(new BufferedReader(spec), ":"));
  }
  
  /** Construct a lexer factory from a given lexer specification. */
  public LexerFactory(Map<String, String> rawRules) {
    rules = new LinkedHashMap<String, ARule>();
    tokenPattern = parseRules(rawRules);
  }
  
  /** Convenience overload for {@link LexerFactory#makeLexer(Reader)} */
  public ITokenizer makeLexer(String in) {
    return makeLexer(new StringReader(in));
  }
  
  /**
   * Construct a new ITokenizer.
   * @param in - Input to be tokenized.
   * @return new ITokenizer for tokenizing <var>in</var>.
   */
  public ITokenizer makeLexer(Reader in) {
    return new ALexer(in) {
      @Override protected Pattern tokenPattern() { return tokenPattern; }
      @Override protected Collection<ARule> getRules() { return rules.values(); }
      @Override protected ARule getRule(String name) { return rules.get(name); }
    };
  }
  
  /** @return the set of names of all terminals */
  public java.util.Set<String> getTerminalSet() {
    Set<String> terminals = new HashSet<String>();
    for (ARule r : rules.values()) if (!r.isIgnored) terminals.add(r.name);
    return terminals;
  }
  
  /** Constructor helper for parsing the rules */
  private Pattern parseRules(Map<String, String> rawRules) {
    StringBuilder patternBuilder = new StringBuilder();
    for (Map.Entry<String, String> rawRule : rawRules.entrySet()) {
      String rawPattern = rawRule.getValue().trim();
      ARule rule = new XRule(rawRule.getKey(), rawPattern);
      if (!rules.isEmpty()) patternBuilder.append('|');
      patternBuilder.append(rule.pattern);
      rules.put(rule.name, rule);
    }
    return Pattern.compile(patternBuilder.toString());
  }
  
  /** Counter used for generating unique group names */
  private long gnCount = 0;
  
  private class XRule extends ARule {
    private String n;
	XRule(String n, String p) {
    	super(n, p);
		this.n = n;
    }
    @Override protected String nextGroupName() {
      return "G" + (gnCount ++);
    }
    
    public String toString() {
    	return "XRule: " + n;
    }
  }
  


}
