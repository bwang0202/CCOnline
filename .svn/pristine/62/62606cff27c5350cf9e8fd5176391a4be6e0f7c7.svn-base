package client.model.exec;

import java.util.Map;
import java.util.function.Consumer;

/**
 * An operand in the execution of a program
 * @author Derek
 *
 */
public interface IOperand {

	/**
	 * Gets the value of this operand, given the symbol table
	 * @param symbolTable the symbol table of the program
	 * @param output place to put output.
	 * @return this operand's value
	 */
	default IOperand run(Map<String, IOperand> symbolTable, Consumer<String> output) {
		return this; // by default, this is its own value
	}

}
