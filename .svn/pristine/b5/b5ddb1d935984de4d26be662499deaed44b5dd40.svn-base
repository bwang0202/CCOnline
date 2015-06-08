package parser;
import extvisitor.IExtVisitor;

/**
 * Abstract grammar token that has a name.
 * Executes the case in the visitor corresponding to its name.
 * Also can execute a visitor based on its class type or name.
 */
public abstract class AGrammarSymbol implements IGrammarSymbol {
	/**
	 * The name of the grammar symbol.
	 */
	private String _name;

	/**
	 * Constructor for the class
	 * @param name The name of the new grammar symbol
	 */
	public AGrammarSymbol(String name) {
		_name = name;
	}

	/**
	 * The name of the symbol
	 */
	public String getName() {
		return _name;
	}

	@Override
	public <R, P> R execute(IExtVisitor<R, String, P, IGrammarSymbol> algo, @SuppressWarnings("unchecked") P... inps){
		return algo.caseAt(_name, this, inps);
	}

	@Override
	public <R, P> R typeExecute(IGramSymVisitor<R, P> algo,
			@SuppressWarnings("unchecked") P... inps) {
		return algo.caseAt(this.getClass().getSimpleName(), this, inps);
	}
}