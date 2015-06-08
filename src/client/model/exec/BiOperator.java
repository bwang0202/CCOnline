package client.model.exec;

import hw03Common.defs.WellKnownOperator;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * An operator that operates on two operands to form a new operand
 * @author Derek
 *
 * @param <T> the class of operands accepted by this operator
 */
public class BiOperator<T extends IOperand> implements IOperator {

	/** type processed by this operator */
	private Class<T> type;
	/** function used by this operator */
	private BiFunction<T, T, T> func;
	/** the well known operator this operator is associated with */
	private WellKnownOperator name;

	/**
	 * Creates a new BiOperator
	 * @param name type processed by this operator
	 * @param type the well known operator this operator is associated with
	 * @param func function used by this operator
	 */
	public BiOperator(WellKnownOperator name, Class<T> type, BiFunction<T, T, T> func) {
		this.name = name;
		this.type = type;
		this.func = func;
		
	}
	
	/**
	 * @return the name of this operator, same as the name of its well-known operator
	 */
	public String toString() {
		return name.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @param symbolTable the table of symbols
	 * @param operands the operands to this operator, should be two
	 */
	public ExecValue execute(List<IOperand> operands) {
		if (operands.size() == 2) {
			// apply the operator
			return new ExecValue(new IOperand() {
				public IOperand run(Map<String, IOperand> table, Consumer<String> input) {
					// verify that the operands are of the correct class
					List<IOperand> values = operands.stream().map(v -> v.run(table, input)).collect(Collectors.toList());
					for (int k = 0; k < 2; k++) {
						if (!type.isInstance(values.get(k)))
							throw new RuntimeException("Invalid operand for " + this + ": " + values.get(k));
					}
					return func.apply((T) values.get(0), (T) values.get(1));
				}
				public String toString() {
					return "ExecValue of " + name + " on " + operands;
				}
			});
		} else {
			// insufficient operands, continue as-is and collect more operands later
			return new ExecValue(this, operands);
		}	
	}

}
