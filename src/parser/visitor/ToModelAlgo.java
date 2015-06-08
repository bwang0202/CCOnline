package parser.visitor;

import parser.AGramSymVisitor;
import parser.IGramSymVisitorCmd;
import parser.IGrammarSymbol;
import parser.SequenceSymbol;
import sample.AAST;
import sample.Add;
import sample.IntLeaf;
import sample.Multiply;
import sample.VarLeaf;

/**
 * Turn the parse tree into an AST that is more easy to use.
 5.0+3.0+7.0
 parser.SequenceSymbol
 |_ parser.TerminalSymbol: 5.0
 |_ parser.SequenceSymbol
   |_ parser.TerminalSymbol: +
   |_ parser.SequenceSymbol
     |_ parser.TerminalSymbol: 3.0
     |_ parser.SequenceSymbol
       |_ parser.TerminalSymbol: +
       |_ parser.SequenceSymbol
         |_ parser.TerminalSymbol: 7.0
         |_ parser.MTSymbol
 */
public class ToModelAlgo extends AGramSymVisitor</*R=*/AAST, /*P=*/Void> {
    public static final ToModelAlgo Singleton = new ToModelAlgo();
    private ToModelAlgo() {
        super(new IGramSymVisitorCmd<AAST,Void>() {
            public AAST apply(String idx, IGrammarSymbol host, Void... inps) {
                throw new IllegalArgumentException("Parse tree does not represent a correct arithmetic expression.");
            }
        });
        setCmd("Sequence",new IGramSymVisitorCmd<AAST,Void>() {

            public AAST apply(String idx, IGrammarSymbol host, Void... inps) {
                SequenceSymbol sh = (SequenceSymbol)host;
                return sh.getSymbol2().execute(SequenceHelper.Singleton,sh.getSymbol1().execute(NumOrIdHelper.Singleton));
            }
        });
    }
    
    // |_ parser.SequenceSymbol
    //   |_ parser.TerminalSymbol: +
    //   |_ parser.SequenceSymbol
    //     |_ parser.TerminalSymbol: 3.0
    //     |_ ...
    //or
    // |_ parser.MTSymbol
    // inps[0] is the AAST from the left side of the + or *
    private static class SequenceHelper extends AGramSymVisitor</*R=*/AAST, /*P=*/AAST> {
        public static final SequenceHelper Singleton = new SequenceHelper();
        private SequenceHelper() {
            super(new IGramSymVisitorCmd<AAST,AAST>() {
                public AAST apply(String idx, IGrammarSymbol host, AAST... inps) {
                    throw new IllegalArgumentException("Parse tree does not represent a correct arithmetic expression.");
                }
            });
            // legal cases:
            // - SequenceSymbol
            // - MTSymbol
            setCmd("Sequence",new IGramSymVisitorCmd<AAST,AAST>() {
                public AAST apply(String idx, IGrammarSymbol host, AAST... inps) {
                    SequenceSymbol sh = (SequenceSymbol)host;
                    return sh.getSymbol1().execute(OperatorHelper.Singleton,
                                                   inps[0],
                                                   sh.getSymbol2().execute(ToModelAlgo.Singleton));
                }
            });
            setCmd("MTSymbol",new IGramSymVisitorCmd<AAST,AAST>() {
                public AAST apply(String idx, IGrammarSymbol host, AAST... inps) {
                    return inps[0];
                }
            });
        }        
    }    
    
    // |_ parser.SequenceSymbol
    //   |_ parser.TerminalSymbol: +
    //   |_ parser.SequenceSymbol
    //     |_ parser.TerminalSymbol: 3.0
    //     |_ ...
    //or
    // |_ parser.MTSymbol
    private static class NumOrIdHelper extends AGramSymVisitor</*R=*/AAST, /*P=*/Void> {
        public static final NumOrIdHelper Singleton = new NumOrIdHelper();
        private NumOrIdHelper() {
            super(new IGramSymVisitorCmd<AAST,Void>() {
                public AAST apply(String idx, IGrammarSymbol host, Void... inps) {
                    throw new IllegalArgumentException("Parse tree does not represent a correct arithmetic expression.");
                }
            });
            // legal cases:
            // - Num
            // - Id
            setCmd("Num",new IGramSymVisitorCmd<AAST,Void>() {
                public AAST apply(String idx, IGrammarSymbol host, Void... inps) {
                    return new IntLeaf(new Double(host.toString()).intValue());
                }
            });
            setCmd("Id",new IGramSymVisitorCmd<AAST,Void>() {
                public AAST apply(String idx, IGrammarSymbol host, Void... inps) {
                    return new VarLeaf(host.toString());
                }
            });
        }        
    }    
    
    // parser.TerminalSymbol: +
    // or
    // parser.TerminalSymbol: *
    // inps[0] is the AAST from the left side of the + or *
    // inps[1] is the AAST from the left side of the + or *
    private static class OperatorHelper extends AGramSymVisitor</*R=*/AAST, /*P=*/AAST> {
        public static final OperatorHelper Singleton = new OperatorHelper();
        private OperatorHelper() {
            super(new IGramSymVisitorCmd<AAST,AAST>() {
                public AAST apply(String idx, IGrammarSymbol host, AAST... inps) {
                    throw new IllegalArgumentException("Parse tree does not represent a correct arithmetic expression.");
                }
            });
            // legal cases:
            // - +
            // - *
            setCmd("+",new IGramSymVisitorCmd<AAST,AAST>() {
                public AAST apply(String idx, IGrammarSymbol host, AAST... inps) {
                    return new Add(inps[0], inps[1]);
                }
            });
            setCmd("*",new IGramSymVisitorCmd<AAST,AAST>() {
                public AAST apply(String idx, IGrammarSymbol host, AAST... inps) {
                    return new Multiply(inps[0], inps[1]);
                }
            });
        }        
    }    
}
