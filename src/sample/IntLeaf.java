package sample;

public class IntLeaf extends ALeaf<Integer> {
    private int _value;

    public IntLeaf(int val) {
        super(IntLeaf.class);
        _value = val;
    }

    public Integer getValue() {
        return _value;
    }
}

