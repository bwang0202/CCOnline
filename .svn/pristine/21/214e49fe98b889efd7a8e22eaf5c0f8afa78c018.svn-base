package sample;

import extvisitor.AExtVisitor;
import extvisitor.IExtVisitorCmd;
/**
 * ToString algo for an AAST
 * Generic params:  R = String, I = Class<?>, P = Void, H = AAST
 * @author swong
 *
 */
public class AAST_ToString extends AExtVisitor<String, Class<?>,Void,AAST> {
    public final static AAST_ToString Singleton = new AAST_ToString();
    private AAST_ToString() {
        super(new IExtVisitorCmd<String,Class<?>,Void,AAST>() {

			@Override
			public String apply(Class<?> index, AAST host, Void... nu) {
				throw new IllegalArgumentException("AAST_ToString: AST tree does not represent a supported arithmetic expression.");
			}
        	
        });
        
        // set up visitor cases here
        setCmd(IntLeaf.class, new IExtVisitorCmd<String,Class<?>,Void,AAST>() {
            public String apply(Class<?> index, AAST host, Void... nu) {
                return String.valueOf(((IntLeaf)host).getValue());
            }
        });
        setCmd(VarLeaf.class, new IExtVisitorCmd<String,Class<?>,Void,AAST>() {
            public String apply(Class<?> index, AAST host, Void... nu) {
                return String.valueOf(((VarLeaf)host).getValue());
            }
        });
        setCmd(Add.class, new IExtVisitorCmd<String,Class<?>,Void,AAST>() {
            public String apply(Class<?> index, AAST host, Void... nu) {
                Add h = (Add)host;
                return "+\n"+h.getLHS().execute(helper, "|  ")+"\n"+h.getRHS().execute(helper, "   ");
            }
        });
        setCmd(Multiply.class, new IExtVisitorCmd<String,Class<?>,Void,AAST>() {
            public String apply(Class<?> index, AAST host, Void... nu) {
                Multiply h = (Multiply)host;
                return "*\n"+h.getLHS().execute(helper, "|  ")+"\n"+h.getRHS().execute(helper, "   ");
            }
        });
    }
    
    private AExtVisitor<String, Class<?>,String,AAST> helper = new AExtVisitor<String, Class<?>,String,AAST>(
    		/**
    		 * Default cmd
    		 */
    		new IExtVisitorCmd<String,Class<?>,String,AAST>() {
				@Override
				public String apply(Class<?> index, AAST host, String... prefixes) {
					throw new IllegalArgumentException("AAST_ToString.helper: AST tree does not represent a supported arithmetic expression.");
				}
    		}) {
    	{
            // set up visitor cases here
            setCmd(IntLeaf.class, new IExtVisitorCmd<String,Class<?>,String,AAST>() {
                public String apply(Class<?> index, AAST host, String... prefixes) {
                    return "|_ "+String.valueOf(((IntLeaf)host).getValue());
                }
            });
            setCmd(VarLeaf.class, new IExtVisitorCmd<String,Class<?>,String,AAST>() {
                public String apply(Class<?> index, AAST host, String... prefixes) {
                    return "|_ "+String.valueOf(((VarLeaf)host).getValue());
                }
            });
            setCmd(Add.class, new IExtVisitorCmd<String,Class<?>,String,AAST>() {
                public String apply(Class<?> index, AAST host, String... prefixes) {
                    Add h = (Add)host;
                    return "|_ +\n"+prefixes[0]+h.getLHS().execute(helper, prefixes[0]+"|  ")+"\n"+prefixes[0]+h.getRHS().execute(helper,prefixes[0]+"   ");
                }
            });
            setCmd(Multiply.class, new IExtVisitorCmd<String,Class<?>,String,AAST>() {
                public String apply(Class<?> index, AAST host, String... prefixes) {
                    Multiply h = (Multiply)host;
                    return "|_ *\n"+prefixes[0]+h.getLHS().execute(helper, prefixes[0]+"|  ")+"\n"+prefixes[0]+h.getRHS().execute(helper,prefixes[0]+"   ");
                }
            });
    	}
    };

}
