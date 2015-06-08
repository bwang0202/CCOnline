package extvisitor;

/**
 * Interface that defines a command used by AExtVisitor that has specific 
 * types for its return value, R, its index value, I, its input parameters, P, 
 * and its host, H.   The host is restricted to being a subclass of 
 * IExtVisitorHost who takes the same index value and who accepts a visitor 
 * that takes this same host type.
 * An IExtVisitorCmd is associated with every recognized case of an AExtVisitor,
 * including the default case.
 * @param <R> The type of the return value
 * @param <I> The type of the index value
 * @param <P> The type of the input parameters
 * @param <H> The type of the host, restricted to being a subclass of IExtVisitorHost<I, H>
 */
public abstract interface IExtVisitorCmd<R, I, P, H extends IExtVisitorHost<I,H>> {  
  /**
   * The method that is run by AExtVisitor when the case associated with this 
   * command is executed.
   * @param idx The index value for the case for which this command is associated. 
   * @param host The host for the visitor
   * @param params Vararg input parameters
   * @return The value returned by the running this command.
   */
  public abstract R apply(I index, H host, @SuppressWarnings("unchecked") P... params);
}