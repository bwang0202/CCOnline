package parser;

/**
 * Grammar terminal -- empty
 */
public class MTSymbol extends AGrammarSymbol {
    /**
     * Singleton instance.
     */
    public final static MTSymbol Singleton = new MTSymbol();

    /**
     * Private singleton constructor.
     */
    private MTSymbol() {
      super("MTSymbol");
    }

    /**
     * Return a string representation.
     *
     * @return string representation
     */
    public String toString() {
        return "";
    }

}

