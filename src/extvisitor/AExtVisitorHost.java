package extvisitor;

/**
 * Abstract extended visitor host that embodies the concrete behavior of calling 
 * the case on its visitor with the given index.
 * @param <I> The type of the index value used by this host
 */
public abstract class AExtVisitorHost<I>  implements IExtVisitorHost<I, AExtVisitorHost<I> > {
  /**
   * The index value that defines this host
   */
  private I idx;
  
  /**
   * Constructor for the class
   * @param idx The index value to use
   */
  public AExtVisitorHost(I idx) {
    this.idx = idx;
  }

  /**
   * Get the index value used by this host
   * @return The index value used by this host
   */
  public I getIndex() {
    return idx;
  }
  
  /**
   * Executes (accepts) the visitor, calling the case associated with this host's index value.
   * @param <R> The type of the return value
   * @param <P> The type of the vararg input parameter
   * @param algo The visitor to execute
   * @param params The input parameters supplied to the algo when its appropriate case is called.
   * @return The return value from executing the appropriate case on the visitor.
   */
  @Override
  public <R, P> R execute(IExtVisitor<R, I, P, AExtVisitorHost<I>> algo,  @SuppressWarnings("unchecked") P... params) {
    return algo.caseAt(idx, this, params);
  }
  
}