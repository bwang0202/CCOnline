package parser;

import token.ATokVisitor;
import token.tokenizer.ITokenizer;

/**
 * Utility factory that automatically combines together a series of 
 * CombinationFacts.  The result is to create a single factory that 
 * appears to create an arbitrarily large combination of ITokVisitorFacts.
 * Note that each Combination symbol generated in the chain is given a 
 * unique, numbered name to aid in debugging.  The first (returned) one
 * has the given name.
 */
public class MultiCombinationFact implements ITokVisitorFact {
  /**
   * The total factory, composed from all the given factories.
   */
  private ITokVisitorFact _composed;
  
  /**
   * Constructor for the multi-combination factory,
   *
   * @param name The grammar symbol's name of this instance of a combination.  
   * Individual binary combination names are made by appending a numerical value to this name.
   * @param tkz    tokenizer to use
   * @param fact1  factory for the first symbol
   * @param fact2 factory for the second symbol
   * @param facts a vararg array of additional factories of symbols
   */
   public MultiCombinationFact(String name, ITokenizer tkz, ITokVisitorFact fact1, ITokVisitorFact fact2, ITokVisitorFact...facts) {
     
     if(0 == facts.length) {
       _composed = makeComposed(name, tkz, fact1, fact2);
     }
     else {
       ITokVisitorFact temp = facts[facts.length-1];
       for(int i = facts.length-2; i >= 0 ; i--) {
         temp = makeComposed(name +(i+2), tkz, facts[i], temp);
       }
       temp = makeComposed(name+1, tkz, fact2, temp);
       _composed = makeComposed(name, tkz, fact1, temp);
     }
   }
 
   @Override
   public String getName() {
     return _composed.getName();
   }
   
   /**
    * Internal utility method to create a binary combination factory
    */
   private ITokVisitorFact makeComposed(String s, ITokenizer t, ITokVisitorFact fact1, ITokVisitorFact fact2) {
     return new CombinationFact(s, t, fact1, fact2);
   }
   
   
  /**
   * Returns the visitor made from the combination of all the visitors from all the given factories.
   */
   @Override
  public ATokVisitor<IGrammarSymbol, Object> makeVisitor() {
    return _composed.makeVisitor();
  }
  
  /**
   * Combines the visitor returned from makeVisitor with the given visitor
   */
   @Override
  public ATokVisitor<IGrammarSymbol, Object> makeCombinedVisitor(ATokVisitor<IGrammarSymbol, Object> other) {
    return _composed.makeCombinedVisitor(other);
  }  
   
   @Override
  public String toString() {
    return _composed.toString();
  }
}