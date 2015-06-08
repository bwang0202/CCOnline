package hw03Common.defs;

/**
 * Definitions of message fields that are are unique to each type of message.
 * 
 * @author Stephen Wong
 *
 */
public interface IMessageFields
{

	/**
	 * Language information message
	 * 
	 * @author Stephen Wong
	 *
	 */
	public interface ILanguageInfo
	{
		/**
		 * The unique name/identifier for the language
		 */
		public static final String LANGUAGE_NAME = "lang_name";

		/**
		 * The BNF grammar string
		 */
		public static final String BNF_GRAMMAR_STR = "bnf"; // OPTIONAL

		/**
		 * The mapping from the language's representation of the well-known
		 * operators to their corresponding well-known operators.
		 */
		public static final String OPERATIONS_MAP = "op_map"; // OPTIONAL
	}

	/**
	 * Executable program information
	 * 
	 * @author Stephen Wong
	 *
	 */
	public interface IProgramInfo
	{
		/**
		 * The name/identifier of the program that is unique with respect to the
		 * language it is written in.
		 */
		public static final String PROGRAM_NAME = "prog_name";

		/**
		 * The text of the program code.
		 */
		public static final String PROGRAM_TEXT = "program"; // OPTIONAL
	}

	/**
	 * Program execution information
	 * 
	 * @author Stephen Wong
	 *
	 */
	public interface IExecutionInfo
	{
		/**
		 * Code to run for initialization purposes. This code is run just before
		 * the program's code is run.
		 */
		public static final String INIT_CODE = "init";
	}

	/**
	 * Status message in response to any message received.
	 * 
	 * @author Stephen Wong
	 *
	 */
	public interface IStatusInfo
	{
		/**
		 * Enum status code that is unique across all possible status messages.
		 */
		public static final String STATUS_CODE = "status_code";

		/**
		 * Detail message corresponding to the status code.
		 */
		public static final String STATUS_MSG = "status_msg";

		/**
		 * The JSON encoded message to which this message is responding. The
		 * inclusion of some fields are optional, so always check if the field
		 * is present before accessing it.
		 */
		public static final String ORIGINAL_MSG = "orig_msg"; // OPTIONAL
	}
}
