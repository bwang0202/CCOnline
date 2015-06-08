package sample;

import junit.framework.TestCase;

public class InFixFormatterTest extends TestCase {
    public void testIntLeaf() {
        AAST a = new IntLeaf(2);
        assertEquals("2", a.execute(InFixFormatter.Singleton));
    }

    public void testVarLeaf() {
        AAST a = new VarLeaf("x");
        assertEquals("x", a.execute(InFixFormatter.Singleton));
    }

    public void testAdd() {
        AAST a = new Add(new IntLeaf(2), new IntLeaf(5));
        assertEquals("(2 + 5)", a.execute(InFixFormatter.Singleton));
    }

    public void testMul() {
        AAST a = new Multiply(new IntLeaf(2), new IntLeaf(5));
        assertEquals("(2 * 5)", a.execute(InFixFormatter.Singleton));
    }

    public void testComplex() {
        AAST a = new Add(new IntLeaf(2), new Multiply(new IntLeaf(5), new IntLeaf(7)));
        assertEquals("(2 + (5 * 7))", a.execute(InFixFormatter.Singleton));
    }
}
