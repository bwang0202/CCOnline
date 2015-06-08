package parser.visitor.xml;

import parser.AGramSymVisitor;
import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;

/**
 * Abstract algo to check the tags in an XML expression. 
 * Defines the algo to return a boolean and take an Object as its input parameters.
 * Sets the default case to return false.
 */
public abstract class ACheckTagsAlgo extends AGramSymVisitor<Boolean, Object> {
	public ACheckTagsAlgo(){
		super(new IGramSymVisitorCmd<Boolean, Object>() {
			public Boolean apply(String idx, IGrammarSymbol host, Object... inps) {
				return false;
			}
		});
	}
}