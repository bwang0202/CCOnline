package client.model.exec;

import java.util.List;


/**
 * An operator that can act on one or more operands and create a resulting operand
 * @author Derek
 *
 */
public interface IOperator {

	/**
	 * The null operator, never effectively executes on any operands
	 */
	IOperator NULL_OP = new IOperator() {

		@Override
		/**
		 * Returns a new ExecValue of this and the old operands, no change
		 * @param symbolTable the symbol table
		 * @param operands the operands to operate on
		 * @return effectively same ExecValue
		 */
		public ExecValue execute(List<IOperand> operands) {
			return new ExecValue(this, operands);
		}
		
		/**
		 * @return "NULL Operator"
		 */
		public String toString() {
			return "NULL Operator";
		}
	};

	/**
	 * Executes this operator on the given operands
	 * @param operands the operands to execute on
	 * @return the result
	 */
	ExecValue execute(List<IOperand> operands);

}
