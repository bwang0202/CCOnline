package parser;

import token.ATokVisitor;

/**
 * Interface for an abstract factory for token visitors.
 */
public interface ITokVisitorFact {
  
	/**
	 * Getter for the name of the token factory, usually corresponds to the production rule name.
	 * @return The name of the factory
	 */
  public String getName();
  
  /**
   * Make a token visitor for this symbol.
   *
   * @return A token visitor
   */
  public abstract ATokVisitor<IGrammarSymbol, Object> makeVisitor();
  
  /**
   * Make a token visitor that will process the combination of this 
   * or the other given symbol 
   *
   * @param other The visitor for the other symbol in the combination 
   * @return A token visitor
   */
  public abstract ATokVisitor<IGrammarSymbol, Object> makeCombinedVisitor(ATokVisitor<IGrammarSymbol, Object> other);
}

