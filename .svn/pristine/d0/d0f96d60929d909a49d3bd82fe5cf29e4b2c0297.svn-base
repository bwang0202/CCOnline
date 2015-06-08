package parser;

import token.ATokVisitor;

/**
 * A proxy (decorator) for a grammar token parsing factory used when the instance of 
 * a desired factory cannot be guaranteed available until execution time, 
 * such as when setting up a grammar with a loop in it.
 * Using this proxy class eliminates the need for an extraneous settor 
 * method and a special constructor for specific factories involved in a 
 * grammar loop just so that the loop can be closed.
 * Install this proxy in place of the proxied factory before the
 * proxied factory has been instantiated and then call the settor
 * below to set the reference to the proxied factory after the proxied factory
 * has been instantiated.
 */
public class ProxyFact implements ITokVisitorFact {

	/**
	 * Constructor for the class
	 */
	public ProxyFact() {
	}
	
	/**
	 * Proxied factory (decoree)
	 */
	private ITokVisitorFact _fact;

	/**
	 * Settor for the proxied factory
	 * @param fact  The factory to proxy
	 */
	public void setFact(ITokVisitorFact fact) {
		_fact = fact;
	}

	/**
	 * Delegates to the proxied factory
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeVisitor() {
		System.out.println("At " + getName() + ", asking for something from " + _fact.getName());
		return _fact.makeVisitor();
	}

	/**
	 * Delegates to the proxied factory
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeCombinedVisitor(ATokVisitor<IGrammarSymbol, Object> other){
		return _fact.makeCombinedVisitor(other);
	}

	@Override
	public String getName() {
		return _fact.getName();
	}
	
	/**
	 * Used to prevent recursive printing when same factory appears more than once
	 */
	private boolean firstTime = true;

	/**
	 * Recursively prints the composed factories only the first time that this is called, otherwise just prints their names.
	 */
	@Override
	public String toString() {
		if(null == _fact) {
			return "ProxyFact(null)";
		}
		else if(firstTime) {
			firstTime = false;
			String result  = "ProxyFact("+_fact+")";
			firstTime = true;
			return result;
		}
		else {
			return "ProxyFact("+_fact.getName()+")";
		}
	}
}

