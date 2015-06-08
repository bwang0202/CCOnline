package hw03Common.defs;

/**
 * Defines the well known operators available on the computer server. The
 * toString() of an enum is its name. Use the static method
 * WellKnownOperator.valueOf(aString) to retrieve the enum value from its string
 * representation.
 * 
 * @author Stephen Wong
 *
 */
public enum WellKnownOperator
{
	/**
	 * Assigns the second argument to the first argument. Returns the first
	 * argument.
	 */
	ASSIGN("a = b", Object.class, Object.class, Object.class),
	/**
	 * Returns the second string concatenated to the end of the first string.
	 */
	CONCATENATION("ab", String.class, String.class, String.class),
	/**
	 * Returns the first number divided by the second number.
	 */
	DIVISION("a/b", Double.class, Number.class, Number.class),
	/**
	 * Returns the first number to the power of the second number.
	 */
	EXPONENTIATION("a^b", Double.class, Number.class, Number.class),
	/**
	 * Returns the larger of the first or second number.
	 */
	MAX("max(a,b)", Number.class, Number.class, Number.class),
	/**
	 * Returns the smaller of the first or second number.
	 */
	MIN("min(a,b)", Number.class, Number.class, Number.class),
	/**
	 * Returns the remainder from dividing the first number by the second
	 * number.
	 */
	MODULO("a%b", Double.class, Number.class, Number.class),
	/**
	 * Returns the first number multiplied by the second number.
	 */
	MULTIPLICATION("a*b", Number.class, Number.class, Number.class),
	/**
	 * Prints the given string to the output. Returns the input string.
	 */
	PRINT("'a'", String.class, String.class),
	/**
	 * Returns the second number subtracted from the first number.
	 */
	SUBTRACTION("a-b", Number.class, Number.class, Number.class),
	/**
	 * Returns the first number added to the second number.
	 */
	SUMMATION("a+b", Number.class, Number.class, Number.class);

	private final String desc;
	private final Class<?> resultType;
	private final Class<?>[] paramTypes;

	/**
	 * Constructor for the enum
	 * 
	 * @param desc
	 *            Description of the operator where 'a' is the first parameter,
	 *            'b' is the second, etc.
	 * @param resultType
	 *            The type of the result
	 * @param paramTypes
	 *            The types of the input parameter in the order in which they
	 *            appear, i.e. (a, b, ...)
	 */
	private WellKnownOperator(String desc, Class<?> resultType, Class<?>... paramTypes) {
		this.desc = desc;
		this.resultType = resultType;
		this.paramTypes = paramTypes;
	}

	/**
	 * Accessor for the description of the enum value
	 * 
	 * @return The description of this enum.
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * Accessor for the result type of the operator to which this enum refers.
	 * 
	 * @return The type of the result of the operation.
	 */
	public Class<?> getResultType()
	{
		return resultType;
	}

	/**
	 * Accessor for the input parameter types of the operator to which this enum
	 * refers.
	 * 
	 * @return The types of the input parameters of the operation, in order (a,
	 *         b, ...).
	 */
	public Class<?>[] getParamTypes()
	{
		return paramTypes;
	}

};
