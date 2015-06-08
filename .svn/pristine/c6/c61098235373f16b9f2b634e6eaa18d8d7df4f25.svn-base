package parser;

/**
 * ToString visitor for AGramSymbol objects that returns a detailed tree-like representation of the grammar symbol (parse) tree
 * This is a visitor based on the host's TYPE, so call this visitor from the host's typeExecute() method, not the regular execute() method!
 * @author swong
 * @author mgricken
 *
 */
public class AGramSym_ToString extends AGramSymVisitor<String, Void> {

	public static final AGramSym_ToString Singleton = new AGramSym_ToString();

	private AGramSym_ToString() {
		super(new IGramSymVisitorCmd<String,Void>() {
			public String apply(String index, IGrammarSymbol host, Void... params) {
				throw new IllegalStateException("AGramSym_ToString.default: Unknown host type: index = "+index+" host = "+host);
			}
		});
		setCmd("TerminalSymbol",new IGramSymVisitorCmd<String,Void>() {
			public String apply(String index, IGrammarSymbol host, Void... nu) {
				// terminal case
				TerminalSymbol h = (TerminalSymbol)host;
				String name = h.toString();
				if(name.equals("\n")) name = "LF";
				return h.getClass().getName()+": "+name;
			}
		}); 

		setCmd("MTSymbol", new IGramSymVisitorCmd<String,Void>() {
			public String apply(String index, IGrammarSymbol host, Void... nu) {
				// empty case
				return host.getClass().getName();
			}
		});
		setCmd("SequenceSymbol", new IGramSymVisitorCmd<String,Void>() {
			public String apply(String index, IGrammarSymbol host, Void... nu) {
				// sequence case
				SequenceSymbol h = (SequenceSymbol)host;
				return h.getClass().getName()+" ["+host.getName()+"]\n"+
				h.getSymbol1().typeExecute(toStringHelp,"|  ")+"\n"+
				h.getSymbol2().typeExecute(toStringHelp,"   ");
			}
		});

	}

	/**
	 * Helper algo
	 */
	private AGramSymVisitor<String,String> toStringHelp = new AGramSymVisitor<String, String>(
			new IGramSymVisitorCmd<String,String>() {
				public String apply(String index, IGrammarSymbol host, String... params) {
					throw new IllegalStateException("AGramSym_ToString.toStringHelp.default: Unknown host type: index = "+index+" host = "+host);
				}
			}) { 
		{
			setCmd("TerminalSymbol", new IGramSymVisitorCmd<String,String>() {
				public String apply(String index, IGrammarSymbol host, String... params) {
					// terminal case
					TerminalSymbol h = (TerminalSymbol)host;
					String name = h.toString();
					if(name.equals("\n")) name = "LF";
					return "|_ "+h.getClass().getName()+": "+name;
				}
			});
			setCmd("SequenceSymbol", new IGramSymVisitorCmd<String,String>() {
				public String apply(String index, IGrammarSymbol host, String... params) {
					// sequence case
					SequenceSymbol h = (SequenceSymbol)host;
					return "|_ "+h.getClass().getName()+" ["+h.getName()+"]\n"+
					params[0]+h.getSymbol1().typeExecute(toStringHelp,params[0]+"|  ")+"\n"+
					params[0]+h.getSymbol2().typeExecute(toStringHelp,params[0]+"  ");
				}
			});
			setCmd("MTSymbol", new IGramSymVisitorCmd<String,String>() {
				public String apply(String index, IGrammarSymbol host, String... params) {
					// empty case
					return "|_ "+host.getClass().getName();
				}
			});
		}
	};

}
