package client.model.exec;

/**
 * A value of a String
 * @author Derek
 *
 */
public class StringValue implements IOperand {

	/**
	 * The value
	 */
	private String value;
	
	/**
	 * Creates a new StringValue
	 * @param value the string value
	 */
	public StringValue(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the string itself
	 */
	public String toString() {
		return value;
	}
	
}
