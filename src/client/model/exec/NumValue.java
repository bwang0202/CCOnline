package client.model.exec;

/**
 * A number value.
 * @author Derek
 *
 */
public class NumValue implements IOperand {

	/**
	 * The value
	 */
	private double value;

	/**
	 * Creates a new number value
	 * @param value the value
	 */
	public NumValue(double value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public double getNum() {
		return value;
	}
	
	/**
	 * @return String value of the value
	 */
	public String toString() {
		return String.valueOf(value);
	}

}
