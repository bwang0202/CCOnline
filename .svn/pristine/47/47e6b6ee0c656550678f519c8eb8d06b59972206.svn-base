package client.model.exec;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A value represented by a symbol used to index into the symbol table
 * @author Derek
 *
 */
public class SymbolValue implements IOperand {

	/**
	 * The symbol id
	 */
	private String symbol;

	/**
	 * Creates a new SymbolValue
	 * @param symbol the symbol for this value
	 */
	public SymbolValue(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the symbol
	 */
	public String toString() {
		return symbol;
	}

	@Override
	/**
	 * @param the symbol table
	 * @return the value of this symbol in the table
	 */
	public IOperand run(Map<String, IOperand> symbolTable, Consumer<String> output) {
		return Objects.requireNonNull(symbolTable.get(symbol), () -> "Could not find " + symbol + " in table " + symbolTable);
	}
}
