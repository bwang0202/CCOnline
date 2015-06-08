package client.model.exec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An execution value that is created and passed along a program's execution
 * @author Derek
 *
 */
public class ExecValue implements IOperand {

	/**
	 * The operator that this value has encountered
	 */
	private final IOperator op;
	
	/**
	 * The operands that this value has encountered
	 */
	private final List<IOperand> operands = new ArrayList<>();
	
	/**
	 * Creates a value containing a single operand
	 * @param operand single operand of the value
	 */
	public ExecValue(IOperand operand) {
		op = IOperator.NULL_OP;
		operands.add(operand);
	}
	
	/**
	 * Creates a value with the given operator, and no operands yet
	 * @param op operator of the value
	 */
	public ExecValue(IOperator op) {
		this.op = op;
	}
	
	/**
	 * Creates a value that combines properties of the two values
	 * @param v1 first value
	 * @param v2 second value
	 */
	public ExecValue(ExecValue v1, ExecValue v2) {
		System.out.println("Combining " + v1 + " and " + v2);
		// choose only non-null value, error if there are two
		if (v1.op == IOperator.NULL_OP) {
			this.op = v2.op;
		} else if (v2.op == IOperator.NULL_OP) {
			this.op = v1.op;
		} else {
			throw new RuntimeException("Conflicting ops: " + v1.op + ", " + v2.op);
		}
		// take all operands
		operands.addAll(v1.operands);
		operands.addAll(v2.operands);
		System.out.println("Got " + this);
		
	}
	
	/**
	 * Create an ExecValue from many operands
	 * @param operands the many operands of the value
	 */
	public ExecValue(List<IOperand> operands) {
		op = IOperator.NULL_OP;
		this.operands.addAll(operands);
	}
	
	/**
	 * Create an ExecValue from an operator and many operands
	 * @param op the operator
	 * @param operands the many operands
	 */
	public ExecValue(IOperator op, List<IOperand> operands) {
		this.op = op;
		this.operands.addAll(operands);
	}

	/**
	 * Create an empty ExecValue
	 */
	public ExecValue() {
		this.op = IOperator.NULL_OP;
	}

	/**
	 * Execute the operator using the given symbol table
	 * @return the result of the operator's execution on the operands
	 */
	public ExecValue execute() {
		return op.execute(operands); 
	}
	
	/**
	 * Returns the ExecValue created by running the AST represented by this ExecValue
	 * @param symbolTable the symbol table to run on
	 * @return the resulting value
	 */
	public IOperand run(Map<String, IOperand> symbolTable, Consumer<String> input) {
		System.out.println("Running " + this);
		for (IOperand operand : operands)
			operand.run(symbolTable, input);
		return null;
	}

	/**
	 * @return String containing the operator and operands
	 */
	public String toString() {
		return "ExecValue(" + op + ", " + operands + ")";
	}
	
}
