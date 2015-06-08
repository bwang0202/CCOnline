package util;

/**
 * @author Mathias Ricken
 */
public class NoOpLambda<R> implements ILambda<R, Void> {

    /**
     * Return null.
     *
     * @param nu not used
     * @return null
     */
    public R apply(Void... nu) {
        return (R) null;
    }
}
