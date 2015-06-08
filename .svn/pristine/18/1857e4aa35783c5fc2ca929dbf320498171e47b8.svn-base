package parser;

import java.util.Map;
import java.util.Objects;

import token.ATokVisitor;
import token.ITokVisitorCmd;
import token.Token;
import token.tokenizer.ITokenizer;
import util.ILambda;
import util.NoOpLambda;
import extvisitor.IExtVisitorCmd;

/**
 * A factory to create visitors to parse a binary sequence of grammar tokens.
 * To easily create a sequence of two or more grammar tokens, use 
 * MultiSequenceFact rather than multiple SequenceFacts.
 * Sequences have lazy tails to avoid problems with recursive dependencies.
 */
public class SequenceFact extends ATokVisitorFact {


	/**
	 * Factory for the first grammar non-terminals.
	 */
	private ITokVisitorFact _fact1;

	/**
	 * Visitor for the first grammar non-terminals.
	 */
	private ATokVisitor<IGrammarSymbol, Object> _parse1;

	/**
	 * Factory for the second grammar non-terminals.
	 */
	private ITokVisitorFact _fact2;

	/**
	 * Visitor for the second grammar non-terminals.
	 */
	private ATokVisitor<IGrammarSymbol, Object> _lazyParse2;
	
	private ATokVisitor<IGrammarSymbol, Object> _parse2() {
	  _tailInitializer.apply();
	  return _lazyParse2;
	}

	/**
	 * Initializer lambda for this factory.  This will instantiate the first composee visitor
	 * and then mutate into a no-op so that the instantiation won't take place again.
	 */
	private ILambda<Void, Void> _headInitializer = new ILambda<Void, Void>() {
		public Void apply(Void... nu) {
			System.out.println("Applying head initializer for " + System.identityHashCode(SequenceFact.this));
			// change state to no-op
			_headInitializer = new NoOpLambda<Void>();

			// initialize
			_parse1 = Objects.requireNonNull(_fact1.makeVisitor());
			System.out.println("Finished head initializer for " + System.identityHashCode(SequenceFact.this));
			//_parse2 = _fact2.makeVisitor();
			return null;
		}
	};
	
  /**
   * Initializer lambda for this factory.  This will instantiate the second composee visitor
   * and then mutate into a no-op so that the instantiation won't take place again.
   */
	private ILambda<Void, Void> _tailInitializer = new ILambda<Void, Void>() {
    public Void apply(Void... nu) {
      // change state to no-op
      _tailInitializer = new NoOpLambda<Void>();

      // initialize
      _lazyParse2 = _fact2.makeVisitor();
      return null;
    }
  };

	/**
	 * Constructor for the sequence factory,
	 * Used when the name of the Sequence is already available.
	 * @param name The grammar token's name of this instance of a sequence.
	 * @param tkz    tokenizer to use
	 * @param fact1  factory for the first non-terminals
	 * @param fact2 factory for the second non-terminals
	 */
	public SequenceFact(final String name, ITokenizer tkz, ITokVisitorFact fact1, ITokVisitorFact fact2) {
		this(new ILambda<String, Void>() {
			@Override
			public String apply(Void... param) {
				return name;
			}
		}, tkz, fact1, fact2);
//		super(name, tkz);
//		_fact1 = fact1;
//		_fact2 = fact2;
		
	}

	/**
	 * Constructor for the sequence factory.
	 * Used when the name of the Sequence must be lazily created, e.g. when a Proxy is involved.
	 * @param getNameCmd The command to generate the name of the Sequence Factory
	 * @param tkz    tokenizer to use
	 * @param fact1  factory for the first non-terminals
	 * @param fact2 factory for the second non-terminals
	 */
	public SequenceFact(ILambda<String, Void> getNameCmd, ITokenizer tkz, ITokVisitorFact fact1, ITokVisitorFact fact2) {
		super(getNameCmd, tkz);
		_fact1 = fact1;
		_fact2 = fact2;
	}
	
	
	/**
	 * Make a token visitor to parse an sequence non-terminal.   The composee visitors are lazily instantiated,
	 * That is, not instantiated until this method or makeCombinedVisitor() is called.   After that, 
	 * the composed visitors are eager and no further instantiations are required or desired. 
	 *  This method works by replacing every cmd in the first visitor with a new one that, for every replaced cmd, 
	 *  first runs the original first visitor cmd and then the second visitor to create a SequenceSymbol output.
	 * @return token visitor
	 * @return A token parsing visitor 
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeVisitor() {
		System.out.println(_headInitializer + " " + System.identityHashCode(this));
	  _headInitializer.apply();
	  Objects.requireNonNull(_parse1);
		return new ATokVisitor<IGrammarSymbol, Object>(_parse1) {
			{
				// Decorate every command that was copied from _parse1
				for(Map.Entry<String, IExtVisitorCmd<IGrammarSymbol, String, Object, Token>> entry : getCmds()) {
					final IExtVisitorCmd<IGrammarSymbol, String, Object, Token> origParse1Cmd = entry.getValue();
					entry.setValue(new ITokVisitorCmd<IGrammarSymbol, Object>() {
						public IGrammarSymbol apply(String idx, Token host, Object... inps) {
							System.out.println("Sequence: "+getName());
							return new SequenceSymbol(getName(), origParse1Cmd.apply(idx, host, inps),  nextToken().execute(_parse2(), inps));
						}
					});
				}

				final IExtVisitorCmd<IGrammarSymbol, String, Object, Token> oldDefaultCmd = getDefaultCmd();
				setDefaultCmd(new IExtVisitorCmd<IGrammarSymbol, String, Object, Token>() {
					public IGrammarSymbol apply(String idx, Token host, Object... inps) {
						return new SequenceSymbol(getName(),(IGrammarSymbol)oldDefaultCmd.apply(idx, host, inps), (IGrammarSymbol) nextToken().execute(_parse2(), inps));
					}
				});
			}
		};
	}

	/**
	 * Make a token visitor that will process the combination of this sequence
	 * or the other given symbol 
	 *
	 * @param other The visitor for the other symbol in the combination 
	 * @return A token visitor
	 */
	@Override
	public ATokVisitor<IGrammarSymbol, Object> makeCombinedVisitor(ATokVisitor<IGrammarSymbol, Object> other) {
		_headInitializer.apply();

		final ATokVisitor<IGrammarSymbol, Object> v = makeVisitor();
		// Copy all cmds from other to v
		v.setCmds(other.getCmds());  // LL(1) guarantees no conflicts!
		v.setDefaultCmd(other.getDefaultCmd());   // Not clear if there is a well defined precedence to enforce here.
		return v;
	}

	/**
	 * Used to prevent recursive printing when same factory appears more than once
	 */
	private boolean firstTime = true;

	/**
	 * Recursively prints the composed factories only the first time that this is called, otherwise just prints their names.
	 */
	@Override
	public String toString() {
		if(firstTime) {
			firstTime = false;
			String result = "SequenceFact("+getName()+": "+_fact1+", "+_fact2+")";
			firstTime = true;
			return result;
		}
		else {
			return "SequenceFact("+getName()+": "+_fact1.getName()+", "+_fact2.getName()+")";
		}
	}
}

