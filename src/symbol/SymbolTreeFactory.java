package symbol;

import java.util.ArrayList;
import java.util.List;


/**
 * Factory for turning a iterables of strings into a list of SymbolTrees
 * @author swong
 *
 */
public class SymbolTreeFactory {
	
	/**
	 * Singleton instance
	 */
	public static final SymbolTreeFactory Singleton = new SymbolTreeFactory();
	
	private SymbolTreeFactory() {}

	/**
	 * Makes a list of SymbolTrees from an iterable of strings.
	 * @param syms
	 * @return
	 */
	public List<SymbolTree> makeSymbolTrees(Iterable<String> syms) {
		
		SymbolTree resultTree = new SymbolTree('?');  // dummy tree used for it internal operations on its children list
		
		for(String str: syms) {
			addStr(resultTree, str);
		}
		return resultTree.execute(new ISymbolTreeVisitor<List<SymbolTree>, Void>() {

			@Override
			public List<SymbolTree> terminalSymCase(SymbolTree host,
					Void... params) {
				return  new ArrayList<SymbolTree>();
			}

			@Override
			public List<SymbolTree> nonTerminalSymCase(SymbolTree host,
					Void... params) {
				return host.getRest();
			}
		});
	}
	
	/**
	 * Utility method that recursively adds every char in a string to the given SymbolTree as its child hierarchy.
	 * @param st   The SymbolTree to add the string to
	 * @param str  The string to decompose into a child hierarchy of the given tree.
	 */
	private void addStr(SymbolTree st, String str) {
		if(str.length() > 0) {
			addStr(st.addToRest(str.charAt(0)), str.substring(1));
		}
	}
	
}
