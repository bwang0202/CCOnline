package extvisitor;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation of IExtVisitor that adds an invariant implementation of 
 * storing commands associated with each case in a dictionary indexed by the 
 * case's associated index value.   
 * When a particular case is called, the associated command is retrieved and 
 * executed.  The return value is the return value from the command.
 * If no associated command is found, then a default command is executed.
 * @param <R> The type of the return value
 * @param <I> The type of the index value
 * @param <P> The type of the input parameters
 * @param <H> The type of the host, restricted to being a subclass of IExtVisitorHost<I, H>
 */
public abstract class AExtVisitor<R, I, P, H extends IExtVisitorHost<I, H>> implements IExtVisitor<R,I,P, H> {
	/**
	 * The dictionary used to store the commands
	 */
	private Map<I, IExtVisitorCmd<R, I, P, H>> cmds = new Hashtable<I, IExtVisitorCmd<R, I, P, H>>();

	/**
	 * The default command to use if no command is associated with a case index value.
	 */
	private IExtVisitorCmd<R, I, P, H> defaultCmd;

	/**
	 * Constructor that takes a default command to use.
	 * @param defaultCmd  The default command to use.
	 */
	public AExtVisitor(IExtVisitorCmd<R, I, P, H> defaultCmd) {
		this.defaultCmd = defaultCmd;
	}

	/**
	 * Constructor that takes a value that the default command will return.  
	 * A default command is created will return the given value and do nothing else.
	 * @param noOpResult The value for the default command to return.
	 */
	public AExtVisitor(final R noOpResult) {
		this(new IExtVisitorCmd<R, I, P, H>() {
			@Override
			public R apply(I index, H host, @SuppressWarnings("unchecked") P... params) {
				return noOpResult;
			}
		});
	}

	/**
	 * Copy constructor for the class.   Copies all cmds with their index keys 
	 * including the default cmd.
	 * @param other The visitor from which the cmds will be copied.
	 */
	public AExtVisitor(AExtVisitor<R, I, P, H> other) {
		cmds = new Hashtable<I, IExtVisitorCmd<R, I, P, H>>(other.cmds);
		setDefaultCmd(other.getDefaultCmd());
	}


	/**
	 * Associates the given index value with the given command
	 * @param idx The index value to use associate with the command.
	 * @param cmd The command associated with the index value
	 */
	public void setCmd(I idx, IExtVisitorCmd<R, I, P, H> cmd) {
		cmds.merge(idx, cmd, (cmd1, cmd2) -> { throw new RuntimeException("Conflicting commands in " + this + " for " + idx + ": " + cmd1 + ", " + cmd2); });
	}

	/**
	 * Retrieve the command associated with given index value.
	 * null is returned if no command is associated with the index value.
	 * @param idx  An index value
	 * @return The IExtVisitorCmd associated with the index value or null
	 */
	public IExtVisitorCmd<R, I, P, H> getCmd(I idx) {
		return cmds.get(idx);
	}

	/**
	 * Returns the set of key-value pairs (Map.Entry) of all the indices and their 
	 * associated cmds in the visitor.   
	 * This method is used when you need to process every case in the visitor without 
	 * knowing a priori what cases are available.
	 * @return A set of Map.Entry values with the index and cmd pair for every case.
	 */
	public Set<Map.Entry<I,IExtVisitorCmd<R, I, P, H>>> getCmds() {
		return cmds.entrySet();
	}

	/**
	 * Adds a set of index-cmd pairs (Map.Entry) to the visitor.  
	 * Overwrites any existing cmds with the same index.
	 * @param cmds The Set of index-cmd pairs to add.
	 */
	public void setCmds(Set<Map.Entry<I,IExtVisitorCmd<R, I, P, H>>> cmds) {
		for(Map.Entry<I,IExtVisitorCmd<R, I, P, H>> entry : cmds) {
			setCmd(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Retrieve the current default command
	 * @return The current default command
	 */
	public IExtVisitorCmd<R, I, P, H> getDefaultCmd(){
		return defaultCmd;
	}

	/**
	 * Set the default command
	 * @param defaultCmd The new default command
	 */
	public void setDefaultCmd(IExtVisitorCmd<R, I, P, H> defaultCmd) {
		this.defaultCmd = defaultCmd;
	}

	/**
	 * Concrete implementation of the parameterized case method that takes 
	 * the index value, retrieves an associated IExtVisitor command and 
	 * executes the command with the given host and input parameters.   
	 * The result from the command execution is returned.   If not associated
	 * command is found, then the current default command is executed.
	 * @param idx The index value for the case
	 * @param host The visitor's host.
	 * @param params Vararg input parameters for the case
	 * @return The result from executing the associated or default command
	 */
	@Override
	public R caseAt(I idx, H host, @SuppressWarnings("unchecked") P ... params) {
		IExtVisitorCmd<R, I, P, H> cmd = cmds.get(idx);
		if(cmd == null) return defaultCmd.apply(idx, host, params);
		else return cmd.apply(idx, host, params);
	}

}