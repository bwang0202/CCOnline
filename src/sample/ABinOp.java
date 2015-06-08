package sample;

public class ABinOp extends AAST {
    private AAST _lhs;
    private AAST _rhs;

    public ABinOp(Class<?> idx, AAST lhs, AAST rhs) {
        super(idx);
        _lhs = lhs;
        _rhs = rhs;
    }

    public AAST getLHS() {
        return _lhs;
    }

    public AAST getRHS() {
        return _rhs;
    }
}
