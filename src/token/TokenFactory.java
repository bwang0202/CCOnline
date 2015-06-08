package token;

/**
 * Concrete factory to create tokens
 */
public class TokenFactory implements ITokenFactory {
	/**
	 * Singleton instance
	 */
	public static final TokenFactory Singleton = new TokenFactory();

	private TokenFactory(){}

	/**
	 * Creates ATokens from the given name and lexeme.
	 * The returned tokens will call the case in their visitors corresponding
	 * to their *name*.
	 * 
	 * For most tokens the name and lexeme are the same, but for some, it is not, e.g.
	 * an Id token, where the name is "Id", but the lexeme is the string value or a 
	 * number token where the name is "Num", but the lexeme is the string representation
	 * of the numerical value.  The idea is for all Id tokens and number tokens to call
	 * be processed identically by having them call the same case on their visitors.
	 * 
	 * @param name The name of the token
	 * @param lexeme The lexeme value for the token
	 * @return An Token instance with the given name and lexeme which calls the named case of its visitors.
	 */
	public Token makeToken(final String name, String lexeme) {
		return new Token(name, lexeme); 
	}
}