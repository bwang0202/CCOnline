package sample;

import extvisitor.AExtVisitor;
import extvisitor.IExtVisitorCmd;

public class InFixFormatter extends AExtVisitor</*R=*/String,/*I=*/Class<?>,/*P=*/Void,/*H=*/AAST> {
    public final static InFixFormatter Singleton = new InFixFormatter();
    private InFixFormatter() {
        super(new IExtVisitorCmd</*R=*/String,/*I=*/Class<?>,/*P=*/Void,/*H=*/AAST>() {
            public String apply(Class<?> index, AAST host, Void... params) {
                throw new AssertionError("will never happen");
            }
        });
        // set up visitor cases here
        setCmd(IntLeaf.class, new IExtVisitorCmd</*R=*/String,/*I=*/Class<?>,/*P=*/Void,/*H=*/AAST>() {
            public String apply(Class<?> index, AAST host, Void... params) {
                return String.valueOf(((IntLeaf)host).getValue());
            }
        });
        setCmd(VarLeaf.class, new IExtVisitorCmd</*R=*/String,/*I=*/Class<?>,/*P=*/Void,/*H=*/AAST>() {
            public String apply(Class<?> index, AAST host, Void... params) {
                return String.valueOf(((VarLeaf)host).getValue());
            }
        });
        setCmd(Add.class, new IExtVisitorCmd</*R=*/String,/*I=*/Class<?>,/*P=*/Void,/*H=*/AAST>() {
            public String apply(Class<?> index, AAST host, Void... params) {
                Add h = (Add)host;
                return "("+h.getLHS().execute(Singleton)+" + "+h.getRHS().execute(Singleton)+")";
            }
        });
        setCmd(Multiply.class, new IExtVisitorCmd</*R=*/String,/*I=*/Class<?>,/*P=*/Void,/*H=*/AAST>() {
            public String apply(Class<?> index, AAST host, Void... params) {
                Multiply h = (Multiply)host;
                return "("+h.getLHS().execute(Singleton)+" * "+h.getRHS().execute(Singleton)+")";
            }
        });
    }
}

