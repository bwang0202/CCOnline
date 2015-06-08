package sample;

import extvisitor.IExtVisitor;
import extvisitor.IExtVisitorHost;

/**
 * Top-level abstract class representing an abstract syntax tree (AST).
 * @author mgricken
 * @author swong
 *
 */
public abstract class AAST implements IExtVisitorHost<Class<?>,AAST> {
    private Class<?> idx;
    public AAST(Class<?> idx) { this.idx = idx; }
    
    public Class<?> getIndex() { return idx; }
    

	@Override
    public <R, P> R execute(IExtVisitor<R,Class<?>,P,AAST> algo,  @SuppressWarnings("unchecked") P... params) {
        return algo.caseAt(idx, this, params);
    }
}
