package wordtree;

import java.util.ArrayList;
import java.util.List;


/**
 * Factory for turning a iterables of strings into a list of SymbolTrees
 * @author swong
 *
 */
public class WordTreeFactory {
	
	/**
	 * Singleton instance
	 */
	public static final WordTreeFactory Singleton = new WordTreeFactory();
	
	private WordTreeFactory() {}

	/**
	 * Makes a list of SymbolTrees from an iterable of strings.
	 * @param syms
	 * @return
	 */
	public List<WordTree> makeSymbolTrees(Iterable<String> syms) {
		
		WordTree resultTree = new WordTree('?');  // dummy tree used for it internal operations on its children list
		
		for(String str: syms) {
			addStr(resultTree, str);
		}
		return resultTree.execute(new IWordTreeVisitor<List<WordTree>, Void>() {

			@Override
			public List<WordTree> leafNonTerminalCase(WordTree host,
					Void... params) {
				return  new ArrayList<WordTree>();
			}
			
			@Override
			public List<WordTree> leafTerminalCase(WordTree host,
					Void... params) {
				return  leafNonTerminalCase(host, params);
			}

			@Override
			public List<WordTree> nonLeafNonTerminalCase(WordTree host,
					Void... params) {
				return host.getRest();
			}

			@Override
			public List<WordTree> nonLeafTerminalCase(WordTree host,
					Void... params) {
				return nonLeafNonTerminalCase(host, params);
			}
		});
	}
	
	/**
	 * Utility method that recursively adds every char in a string to the given SymbolTree as its child hierarchy.
	 * @param st   The SymbolTree to add the string to
	 * @param str  The string to decompose into a child hierarchy of the given tree.
	 */
	private WordTree addStr(WordTree st, String str) {
		//st.setAsTerminal();
		if(str.length() > 0) {
			return addStr(st.addToRest(str.charAt(0)), str.substring(1));
		}
		st.setAsTerminal();
		return st;
	}
	
}
