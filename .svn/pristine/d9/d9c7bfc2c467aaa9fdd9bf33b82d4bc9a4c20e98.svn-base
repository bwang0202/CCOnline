package symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents an n-ary tree of single character symbols used in the tokenizer.
 * @author swong
 *
 */
public class SymbolTree {

	/**
	 * Represents the internal state of the tree.
	 * @author swong
	 *
	 */
	private interface ISymbolTreeState {
		/**
		 * Get the list of children trees
		 * @return
		 */
		List<SymbolTree> getRest();
		/**
		 * Call the appropriate case of the given visitor with the given parameters
		 * @param host  The current context of the state
		 * @param v  The visitor to run
		 * @param params  Input parameters for the visitor
		 * @return  The results of running the visitor
		 */
		<R, P> R execute(SymbolTree host, ISymbolTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params);
		
		/**
		 * Add the given char to the tree as a child tree if it doesn't already exist.
		 * @param c   The char to add
		 * @return  The new child tree if the given char didn't already exist as a child or the existing child tree if it did exist.
		 */
		SymbolTree addToRest(char c);
	}
	
	/**
	 * The terminal symbol state where there are no more children trees.
	 */
	private final ISymbolTreeState TERMINAL_STATE = new ISymbolTreeState() {

		/**
		 * Always throws a NoSuchElementException
		 */
		@Override
		public List<SymbolTree> getRest()  {
			throw new NoSuchElementException("Cannot get rest of a terminal symbol");
		}

		/**
		 * Call the terminalSymCase of the given visitor with the given parameters.
		 */
		@Override
		public <R, P> R execute(SymbolTree host, ISymbolTreeVisitor<R, P> v,
				@SuppressWarnings("unchecked") P... params) {
			return v.terminalSymCase(host, params);
		}

		/**
		 * Add the given char as a child, mutating the tree into a non-terminal state.
		 */
		@Override
		public SymbolTree addToRest(final char c) {
			final List<SymbolTree> restList = new ArrayList<SymbolTree>();
			SymbolTree newST = new SymbolTree(c);
			restList.add(newST);
			
			state = new ISymbolTreeState() {
				
				/**
				 * Returns the list of the children trees
				 */
				@Override
				public List<SymbolTree> getRest() {
					return restList;
				}


				/**
				 * Calls the nonTerminalSymCase of the given visitor with the given parameters.
				 */
				@Override
				public <R, P> R execute(SymbolTree host,
						ISymbolTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params) {
					return v.nonTerminalSymCase(host, params);
				}

				/**
				 * Adds another char as a child if it does not already exist in the tree.
				 */
				@Override
				public SymbolTree addToRest(char c) {
					for(SymbolTree st: restList) {
						if(c == st.getValue()) {
							return st;
						}
					}
					SymbolTree newST = new SymbolTree(c);
					restList.add(newST);
					return newST;
				}
				
			};
			return newST;
		}
	};
	
	/**
	 * The root char value for this tree.
	 */
	private char charValue;
	
	/**
	 * The current state of the tree
	 */
	private ISymbolTreeState state = TERMINAL_STATE;

	/**
	 * Constructor for the class, which is always constructed in the termninal symbol state.
	 * @param c  The root value char to use
	 */
	public SymbolTree(char c) {
		this.charValue = c;
	}
	
	/**
	 * Getter for the root value char
	 * @return The root value char
	 */
	public char getValue() {
		return charValue;
	}
	
	/**
	 * Add the given char to the tree as a child tree if it doesn't already exist.
	 * @param c   The char to add
	 * @return  The new child tree if the given char didn't already exist as a child or the existing child tree if it did exist.
	 */
	public SymbolTree addToRest(char c) {
		return state.addToRest(c);
	}

	/**
	 * Get the list of the children trees
	 * @return A List of SymbolTrees
	 */
	public List<SymbolTree> getRest() {
		return state.getRest();
	}
	
	/**
	 * Executes the given visitor with the given parameters 
	 * @param v  The visitor to execute
	 * @param params  The input parameters to use
	 * @return The result of running the visitor.
	 */
	public <R, P> R execute(ISymbolTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params) {
		return state.execute(this,  v, params);
	}



	/**
	 * Utility visitor to perform the toString traversal of the tree.
	 */
	private final ISymbolTreeVisitor<String, Void> toStringAlgo = new ISymbolTreeVisitor<String, Void>() {

		ISymbolTreeVisitor<String, String> helper = new ISymbolTreeVisitor<String, String>() {

			@Override
			public String terminalSymCase(SymbolTree host, String... prefixes) {
				return "|_ "+host.getValue();
			}

			@Override
			public String nonTerminalSymCase(SymbolTree host, String... prefixes) {
				String result =  "|_ "+host.getValue();
				for(int i=0; i<host.getRest().size()-1; i++) {
					result += "\n"+prefixes[0]+host.getRest().get(i).execute(this,prefixes[0]+"|  ");
				}
				result += "\n"+prefixes[0]+host.getRest().get(host.getRest().size()-1).execute(this,prefixes[0]+"   ");
				return result;
			}
			
		};
		
		@Override
		public String terminalSymCase(SymbolTree host, Void... params) {
			return ""+host.getValue();
		}

		@Override
		public String nonTerminalSymCase(SymbolTree host, Void... params) {
			String result =  ""+host.getValue();
			for(int i=0; i<host.getRest().size()-1; i++) {
				result += "\n"+host.getRest().get(i).execute(helper,"|  ");
			}
			result += "\n"+host.getRest().get(host.getRest().size()-1).execute(helper,"   ");
			return result;
		}
		
	};

	@Override
	public String toString(){
		return this.execute(toStringAlgo);
	}
	
	
	/**
	 * For testing purposes only
	 * @param args
	 */
	public static void main(String[] args) {
		SymbolTree st = new SymbolTree('a');
		st.addToRest('b');
		st.getRest().get(0).addToRest('d');
		st.addToRest('c');		
		


		//System.out.println("st(a) = \n"+st);
		
		List<String> syms =  Arrays.asList(new String[]{"abc", "abd", "xyzzy", "amo", "abcd", "xyzq", "xg"});		
		List<SymbolTree> stList = SymbolTreeFactory.Singleton.makeSymbolTrees(syms);
		
		for(SymbolTree s: stList) {
			System.out.println(s.toString());
		}
		
	}
}
