package wordtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents an n-ary tree of single character symbols used in the tokenizer.
 * @author swong
 *
 */
public class WordTree {

	/**
	 * Represents the internal state of the tree.
	 * @author swong
	 *
	 */
	private interface IWordTreeState {
		/**
		 * Get the list of children trees
		 * @return
		 */
		List<WordTree> getRest();
		/**
		 * Call the appropriate case of the given visitor with the given parameters
		 * @param host  The current context of the state
		 * @param v  The visitor to run
		 * @param params  Input parameters for the visitor
		 * @return  The results of running the visitor
		 */
		<R, P> R execute(WordTree host, IWordTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params);

		/**
		 * Add the given char to the tree as a child tree if it doesn't already exist.
		 * @param c   The char to add
		 * @return  The new child tree if the given char didn't already exist as a child or the existing child tree if it did exist.
		 */
		WordTree addToRest(char c);
		void setAsTerminal();
	}

	/**
	 * The current state of the tree
	 */
	private IWordTreeState state = new IWordTreeState() {  // leafNonTerminal
		/**
		 * Call the terminalSymCase of the given visitor with the given parameters.
		 */
		@Override
		public <R, P> R execute(WordTree host, IWordTreeVisitor<R, P> v,
				@SuppressWarnings("unchecked") P... params) {
			return v.leafNonTerminalCase(host, params);
		}
		
		/**
		 * Always throws a NoSuchElementException
		 */
		@Override
		public List<WordTree> getRest()  {
			throw new NoSuchElementException("WordTree.leafNonTerminalState: Cannot get the rest of a leaf node.");
		}
		
		@Override
		public void setAsTerminal() {
			state = new IWordTreeState() {   // leafTerminalState
				@Override
				public <R, P> R execute(WordTree host,
						IWordTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params) {
					return v.leafTerminalCase(host, params);
				}
				
				@Override
				public List<WordTree> getRest() {
					throw new NoSuchElementException("WordTree.leafTerminalState: Cannot get the rest of a leaf node.");
				}

				@Override
				public WordTree addToRest(char c) {   // leafTerminal --> nonLeafTerminal 
					final List<WordTree> restList = new ArrayList<WordTree>();
					WordTree newWT = new WordTree(c);
					restList.add(newWT);
					state = new IWordTreeState() {   // nonLeafTerminal

						@Override
						public <R, P> R execute(WordTree host,
								IWordTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params) {
							return v.nonLeafTerminalCase(host, params);
						}
						
						@Override
						public List<WordTree> getRest() {
							return restList;
						}


						@Override
						public WordTree addToRest(char c) {
							for(WordTree st: restList) {
								if(c == st.getValue()) {
									return st;
								}
							}
							WordTree newWT = new WordTree(c);
							restList.add(newWT);
							return newWT;
						}

						@Override
						public void setAsTerminal() {  // nonLeafTerminal --> nonLeafTerminal
							// No-op.  Already terminal
						}
						
					};
					
					return newWT;
				}

				@Override
				public void setAsTerminal() {  // leafTerminal --> leafTerminal 
					// No-op.   Already terminal.
				}
				
			};
		}



		/**
		 * Add the given char as a child, mutating the tree into a non-terminal state.
		 */
		@Override
		public WordTree addToRest(final char c) { // leafNonTerminal --> nonLeafNonTerminal
			final List<WordTree> restList = new ArrayList<WordTree>();
			WordTree newWT = new WordTree(c);
			restList.add(newWT);

			state = new IWordTreeState() {  //nonLeafNonTerminal State
				/**
				 * Calls the nonTerminalSymCase of the given visitor with the given parameters.
				 */
				@Override
				public <R, P> R execute(WordTree host,
						IWordTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params) {
					return v.nonLeafNonTerminalCase(host, params);
				}
				
				/**
				 * Returns the list of the children trees
				 */
				@Override
				public List<WordTree> getRest() {
					return restList;
				}




				/**
				 * Adds another char as a child if it does not already exist in the tree.
				 */
				@Override
				public WordTree addToRest(char c) {  // nonLeafNonTerminal --> nonLeafNonTerminal
					for(WordTree st: restList) {
						if(c == st.getValue()) {
							return st;
						}
					}
					WordTree newWT = new WordTree(c);
					restList.add(newWT);
					return newWT;
				}


				@Override
				public void setAsTerminal() { // nonLeafNonTerminal --> nonLeafTerminal
					state = new IWordTreeState() {   // nonLeafTerminal

						@Override
						public List<WordTree> getRest() {
							return restList;
						}

						@Override
						public <R, P> R execute(WordTree host,
								IWordTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params) {
							return v.nonLeafTerminalCase(host, params);
						}

						@Override
						public WordTree addToRest(char c) {
							for(WordTree st: restList) {
								if(c == st.getValue()) {
									return st;
								}
							}
							WordTree newWT = new WordTree(c);
							restList.add(newWT);
							return newWT;
						}

						@Override
						public void setAsTerminal() {  // nonLeafTerminal --> nonLeafTerminal
							// No-op.  Already terminal
						}
						
					};
				}

			};
			return newWT;
		}


	};

	/**
	 * The root char value for this tree.
	 */
	private char charValue;



	/**
	 * Constructor for the class, which is always constructed in the termninal symbol state.
	 * @param c  The root value char to use
	 */
	public WordTree(char c) {
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
	public WordTree addToRest(char c) {
		return state.addToRest(c);
	}
	
	public void setAsTerminal() {
		state.setAsTerminal();
	}

	/**
	 * Get the list of the children trees
	 * @return A List of SymbolTrees
	 */
	public List<WordTree> getRest() {
		return state.getRest();
	}

	/**
	 * Executes the given visitor with the given parameters 
	 * @param v  The visitor to execute
	 * @param params  The input parameters to use
	 * @return The result of running the visitor.
	 */
	public <R, P> R execute(IWordTreeVisitor<R, P> v, @SuppressWarnings("unchecked") P... params) {
		return state.execute(this,  v, params);
	}



	/**
	 * Utility visitor to perform the toString traversal of the tree.
	 */
	private final IWordTreeVisitor<String, Void> toStringAlgo = new IWordTreeVisitor<String, Void>() {

		IWordTreeVisitor<String, String> helper = new IWordTreeVisitor<String, String>() {

			@Override
			public String leafNonTerminalCase(WordTree host, String... prefixes) {
				return "|_ "+host.getValue();
			}

			@Override
			public String leafTerminalCase(WordTree host, String... params) {
				return leafNonTerminalCase(host, params) + " *";
			}
			
			@Override
			public String nonLeafTerminalCase(WordTree host, String... prefixes) {
				return leafCase(host, prefixes[0], " *");
			}



			@Override
			public String nonLeafNonTerminalCase(WordTree host, String... prefixes) {
				return leafCase(host, prefixes[0], "  ");
			}
			
			private String leafCase(WordTree host, String prefix, String marker) {
				String result =  "|_ "+host.getValue()+ marker;
				for(int i=0; i<host.getRest().size()-1; i++) {
					result += "\n"+prefix+host.getRest().get(i).execute(this,prefix+"|  ");
				}
				result += "\n"+prefix+host.getRest().get(host.getRest().size()-1).execute(this,prefix+"   ");
				return result;
			}

		};

		@Override
		public String leafNonTerminalCase(WordTree host, Void... params) {
			return host.getValue() + "";
		}
		
		@Override
		public String leafTerminalCase(WordTree host, Void... params) {
			return host.getValue() + " *";
		}
		
		@Override
		public String nonLeafNonTerminalCase(WordTree host, Void... params) {
			return nonLeafCase(host, "");
		}

		@Override
		public String nonLeafTerminalCase(WordTree host, Void... params) {
			return nonLeafCase(host, " *");
		}
		
		private String nonLeafCase(WordTree host, String marker) {
			String result =  ""+host.getValue() + marker;
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
		WordTree st = new WordTree('a');
		//st.setAsTerminal();
		st.addToRest('b').setAsTerminal();
		st.getRest().get(0).addToRest('d');
//		st.addToRest('c');		



		System.out.println("st(a) = \n"+st);

		List<String> syms =  Arrays.asList(new String[]{"abc", "abd", "xyzzy", "amo", "abcd", "xyzq", "xg"});		
		List<WordTree> stList = WordTreeFactory.Singleton.makeSymbolTrees(syms);

		for(WordTree s: stList) {
			System.out.println(s.toString());
		}

	}
}
