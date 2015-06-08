package sample;

public abstract class ALeaf<V> extends AAST {
    public ALeaf(Class<?> idx) { super(idx); }
    public abstract V getValue();
}

