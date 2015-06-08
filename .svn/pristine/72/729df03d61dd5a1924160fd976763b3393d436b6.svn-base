package token.tokenizer.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ARule {
  final String name;
  final String groupName;
  final String pattern;
  final boolean isIgnored;
  final boolean isDelimiter;
  final boolean needsUnescaping;
  
  ARule(String n, String p) {
    isIgnored = n.startsWith("*");
    isDelimiter = n.startsWith("*") || n.startsWith(".");
    needsUnescaping = n.startsWith("@");
    name = n.replaceFirst("^[.*@]", "");
    if (name.matches("[a-zA-Z][a-zA-Z0-9]*") && hasNamedGroup(name, p)) {
      groupName = name;
      pattern = p;
    }
    else {
      groupName = nextGroupName();
      pattern = String.format("(?<%s>%s)", groupName, p);
    }
  }
  
  protected abstract String nextGroupName();
  
  /** Helper for checking if the rule has a named subsequence matching its name */
  private boolean hasNamedGroup(String name, String pattern) {
    boolean result = false;
    try {
      if (pattern.contains(String.format("(?<%s>", name))) {
        // throws IllegalArgumentException if group doesn't exist
        Matcher m = Pattern.compile("$|"+pattern).matcher("");
        m.matches();
        m.group(name);
        result = true;
      }
    } catch (IllegalArgumentException ex) { }
    return result;
  }
  
}
