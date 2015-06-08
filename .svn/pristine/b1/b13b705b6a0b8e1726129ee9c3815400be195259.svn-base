package controller;
/*
 Parsing with extended visitors that can parse BNF and dynamically create a parser for the BNF that was parsed.

In demo:
1) The left  hand text field is the name of the first file to be parsed.   
  - the "Parse Orig" button will parse a file as per a simple, hardwired grammar (inp1.txt, inp2.txt, inp3.txt, input1.txt, bad1.txt, etc).. The grammar that is used is decribed in bnf1.txt and equivalently, in bnf2.txt.
  - the "Parse XML" button will parse XML files s per a hardwired grammar.  The input files are labeled with "xml".   The grammar for the xml is in bnfxml1.txt.
    -- The "Check XML" button will run an algo on the resultant parse tree to check that all tags are matching.
  - The "Parse BNF" will parse a BNF grammar file as per a hardwired grammar.  The grammar used is given in bnfbnf1.txt.

2) The right-hand text field is for the file to be parsed by a parser generated from a parsed BNF file above.   
  - The "Check BNF" button will generate a parser from the parse tree from parsing a BNF file.   It will then run that parser on the file as specified in the right hand text field.
  - The Tokenizer drop list will select the tokenizer to use when parsing the given file.   The Tokenizer must be specified before the "Check BNF" button is clicked.
*/


import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import model.RDPModel;
import view.IModelAdapter;
import view.RDPFrame;




/**
 * Predictive recursive descent parsing.
 *
 * @author Dung X. Nguyen, Stephen Wong, and Mathias Ricken
 * @version 2.0
 */
public class RDPApp {
    
	
	private RDPFrame view;
	private RDPModel model;

    /**
     * Constructor for the application.
     */
    public RDPApp(int closeOp) {
        view = new RDPFrame(closeOp, new IModelAdapter(){

			@Override
			public String parseOrig(String filename) {
				return model.parseOrig(filename);
			}

			@Override
			public String parseXML(String filename) {
				return model.parseXML(filename);
			}

			@Override
			public String parseBNF(String filename) {
				return model.parseBNF(filename);
			}

			@Override
			public String check() {
				return model.checkXML();
			}

			@Override
			public String checkBNF(String filename) {
				return model.checkBNF(filename);
			}
        	
        });
        
        model = new RDPModel();

    }
    
    public void start() {
    	model.start();
    	view.start();
    }

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				(new RDPApp(WindowConstants.EXIT_ON_CLOSE)).start();	
			}
    		
    	});
    }
        
}